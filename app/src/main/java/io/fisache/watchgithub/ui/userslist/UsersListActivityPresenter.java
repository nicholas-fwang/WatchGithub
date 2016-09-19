package io.fisache.watchgithub.ui.userslist;

import android.content.res.Resources;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.fisache.watchgithub.R;
import io.fisache.watchgithub.base.BasePresenter;
import io.fisache.watchgithub.base.Validator;
import io.fisache.watchgithub.data.manager.GithubUserManager;
import io.fisache.watchgithub.data.manager.UsersManager;
import io.fisache.watchgithub.data.model.User;
import io.fisache.watchgithub.service.SchedulerProvider;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
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

    private static boolean firstStarted = true;

    private SchedulerProvider schedulerProvider = SchedulerProvider.getInstance();

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
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
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
                        usersFilterObservable(users, false)
                                .subscribe(new Action1<List<User>>() {
                                    @Override
                                    public void call(List<User> users) {
                                        processUsers(users);
                                        activity.showLoading(false);
                                    }
                                });
                    }
                });
        subscription.add(mSubscription);
    }

    private void processUsers(List<User> users) {
        if(users.size() == 0) {
            activity.showNotExistUsers();
        } else {
            if(firstStarted) {
                activity.showLoading(true);
                Subscription remoteSubscription =
                        githubUserManager.getGithubUsers(users)
                                .subscribeOn(schedulerProvider.io())
                                .observeOn(schedulerProvider.ui())
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
                                usersFilterObservable(users, true)
                                        .subscribe(new Action1<List<User>>() {
                                            @Override
                                            public void call(List<User> users) {
                                                firstStarted = false;
                                                processUsers(users);
                                            }
                                        });
                            }
                        });
                subscription.add(remoteSubscription);
            } else {
                activity.setUsers(users);
                activity.showExistUsers();
            }
        }
    }

    private Observable<List<User>> usersFilterObservable(List<User> users, final boolean isRemote) {
        return Observable.from(users)
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
                .doOnNext(new Action1<User>() {
                    @Override
                    public void call(User user) {
                        if(isRemote) {
                            usersManager.updateUser(user);
                        }
                    }
                }).toList();
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

    public void setFilter(UserFilterType type) {
        userFilterType = type;
    }

    Subscription observeSearchText(Observable<CharSequence> observable) {
        return observable
                .map(new Func1<CharSequence, String>() {
                    @Override
                    public String call(CharSequence charSequence) {
                        String pattern = new StringBuilder(charSequence).toString();
                        return pattern;
                    }
                })
                .observeOn(Schedulers.io())
                .debounce(200, TimeUnit.MILLISECONDS)
                .switchMap(new Func1<String, Observable<List<User>>>() {
                    @Override
                    public Observable<List<User>> call(String s) {
                        return usersManager.searchUsersWithPattern(s);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<User>>() {
                    @Override
                    public void call(List<User> users) {
                        activity.setUsers(users);
                    }
                });
    }
}
