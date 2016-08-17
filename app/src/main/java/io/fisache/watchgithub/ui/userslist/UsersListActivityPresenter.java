package io.fisache.watchgithub.ui.userslist;

import java.util.List;

import io.fisache.watchgithub.base.BasePresenter;
import io.fisache.watchgithub.data.UsersManager;
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

    private CompositeSubscription subscription;

    public UsersListActivityPresenter(UsersListActivity activity, UsersManager usersManager) {
        this.activity = activity;
        this.usersManager = usersManager;
        subscription = new CompositeSubscription();
    }

    @Override
    public void subscribe() {
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
                        return true;
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
        if(users == null) {
            activity.showNotExistUsers();
        } else {
            activity.setUsers(users);
            activity.showExistUsers();
        }
    }
}
