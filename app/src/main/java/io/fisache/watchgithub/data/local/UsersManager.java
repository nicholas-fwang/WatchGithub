package io.fisache.watchgithub.data.local;

import java.util.List;


import io.fisache.watchgithub.data.BaseService;
import io.fisache.watchgithub.data.model.User;
import io.fisache.watchgithub.data.sqlbrite.SqlbriteService;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class UsersManager implements BaseService {
    private SqlbriteService sqlbriteService;

    public UsersManager(SqlbriteService sqlbriteService) {
        this.sqlbriteService = sqlbriteService;
    }

    @Override
    public Observable<List<User>> getUsers() {
        final Observable<List<User>> localUsers = sqlbriteService.getUsers();

        return localUsers;
    }

    // not used
    @Override
    public Observable<User> getUser(final long id) {
        Observable<User> localUser = sqlbriteService.getUser(id)
            .doOnNext(new Action1<User>() {
                @Override
                public void call(User user) {
                  }
                })
                .doOnCompleted(new Action0() {
                    @Override
                    public void call() {
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        return localUser;
    }

    @Override
    public void saveUser(User user) {
        sqlbriteService.saveUser(user);
    }

    @Override
    public void deleteUser(long userId) {
        sqlbriteService.deleteUser(userId);
    }

    @Override
    public void deleteAllUser() {
        sqlbriteService.deleteAllUser();
    }

    @Override
    public void updateUser(User user) {
        sqlbriteService.updateUser(user);
    }

    @Override
    public void updateDesc(User user) {
        sqlbriteService.updateDesc(user);
    }
}
