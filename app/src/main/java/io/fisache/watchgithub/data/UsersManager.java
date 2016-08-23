package io.fisache.watchgithub.data;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.fisache.watchgithub.data.cache.CacheService;
import io.fisache.watchgithub.data.github.GithubUserManager;
import io.fisache.watchgithub.data.model.User;
import io.fisache.watchgithub.data.sqlbrite.SqlbriteService;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class UsersManager implements BaseService{
    private CacheService cacheService;
    private SqlbriteService sqlbriteService;
    private GithubUserManager githubUserManager;

    private boolean checkRemote = false;

    public UsersManager(CacheService cacheService, SqlbriteService sqlbriteService, GithubUserManager githubUserManager) {
        this.cacheService = cacheService;
        this.sqlbriteService = sqlbriteService;
        // 앱 최초 시작 시 remote에서 데이터를 가져와 local에 저장한다
        this.githubUserManager = githubUserManager;
    }

    @Override
    public Observable<List<User>> getUsers() {
        /**
         * sqlbrite
         */
        Observable<List<User>> localUsers = sqlbriteService.getUsers();

//        return localUsers;
        return Observable.concat(localUsers, localUsers).first();
    }

    // not used
    @Override
    public Observable<User> getUser(final long id) {
        /**
         * cache, sqlbrite same logic
         */
//        Observable<User> cacheUser = cacheService.getUser(id);
        Observable<User> localUser = sqlbriteService.getUser(id)
            .doOnNext(new Action1<User>() {
                @Override
                public void call(User user) {
                    cacheService.saveUser(user);
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
//        return Observable.concat(cacheUser, localUser).first();
    }

    @Override
    public void saveUser(User user) {
        /**
         * cache, sqlbrite same logic
         */
//        cacheService.saveUser(user);
        sqlbriteService.saveUser(user);
    }

    @Override
    public void deleteUser(long userId) {
        /**
         * cache, sqlbrite same logic
         */
//        cacheService.deleteUser(userId);
        sqlbriteService.deleteUser(userId);
    }

    @Override
    public void deleteAllUser() {
        /**
         * cache, sqlbrite same logic
         */
//        cacheService.deleteAllUser();
        sqlbriteService.deleteAllUser();
    }

    @Override
    public void updateUser(User user) {
        /**
         * cache, sqlbrite same logic
         */
//        cacheService.updateDesc(user);
        sqlbriteService.updateUser(user);
    }

    @Override
    public void updateDesc(User user) {
        sqlbriteService.updateDesc(user);
    }
}
