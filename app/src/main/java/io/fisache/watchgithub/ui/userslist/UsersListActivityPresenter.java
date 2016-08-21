package io.fisache.watchgithub.ui.userslist;

import android.content.res.Resources;

import java.util.List;

import io.fisache.watchgithub.R;
import io.fisache.watchgithub.base.BasePresenter;
import io.fisache.watchgithub.base.Validator;
import io.fisache.watchgithub.data.UsersManager;
import io.fisache.watchgithub.data.github.GithubUserManager;
import io.fisache.watchgithub.data.model.User;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class UsersListActivityPresenter implements BasePresenter {
    private UsersListActivity activity;
    private UsersManager usersManager;
    private GithubUserManager githubUserManager;
    private Validator validator;

    private Resources resources;

    private CompositeSubscription subscription;

    private UserFilterType userFilterType = UserFilterType.ALL;

    public UsersListActivityPresenter(UsersListActivity activity, UsersManager usersManager,
                                      GithubUserManager githubUserManager, Validator validator) {
        this.activity = activity;
        this.usersManager = usersManager;
        this.githubUserManager = githubUserManager;
        this.validator = validator;
        subscription = new CompositeSubscription();
        resources = activity.getResources();
    }

    @Override
    public void subscribe() {
        if(subscription == null) {
            subscription = new CompositeSubscription();
        }
        setUsers();
    }

    @Override
    public void unsubscribe() {
        subscription = null;
    }

    public void setUsers() {
        activity.showLoading(true);
        subscription.clear();

        Subscription mSubscription = usersManager.getUsers()
                .flatMap(new Func1<List<User>, Observable<User>>() {
                    @Override
                    public Observable<User> call(List<User> users) {
                        return Observable.from(users);
                    }
                })
                .filter(new Func1<User, Boolean>() {
                    @Override
                    public Boolean call(User user) {
                        switch (userFilterType) {
                            case POPULAR:
                                return user.followers >= resources.getInteger(R.integer.user_popular_follower) ? true : false;
                            case USER:
                                return user.type.equals("User");
                            case ORG:
                                return user.type.equals("Organization");
                            case ALL:
                            default:
                                return true;
                        }
                    }
                })
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<User>>() {
                    @Override
                    public void onCompleted() {
                        activity.showLoading(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        activity.showLoading(false);
                    }

                    @Override
                    public void onNext(List<User> users) {
                        processUsers(users);
                    }
                });
        subscription.add(mSubscription);
    }

    private void processUsers(List<User> users) {
        if(users.isEmpty()) {
            activity.showNotExistUsers();
        } else {
            activity.setUsers(users);
            activity.showExistUsers();
        }
    }

    public void enterGithubUser(String username) {
        if(validator.vaildUsername(username)) {
            activity.showLoading(true);
            githubUserManager.getGithubUser(username).subscribe(new Observer<User>() {
                @Override
                public void onCompleted() {
                    activity.showLoading(false);
                }

                @Override
                public void onError(Throwable e) {
                    activity.showLoading(false);
                    activity.showVaildationError();
                }

                @Override
                public void onNext(User user) {
                    usersManager.saveUser(user);
                    setUsers();
                }
            });
        } else {
            activity.showVaildationError();
        }
    }

    void setFilter(UserFilterType type) {
        userFilterType = type;
    }
}
