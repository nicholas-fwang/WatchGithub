package io.fisache.watchgithub.data;

import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import io.fisache.watchgithub.data.cache.CacheService;
import io.fisache.watchgithub.data.model.User;
import io.fisache.watchgithub.data.sqlbrite.SqlbriteService;
import rx.Observable;
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

    public UsersManager(CacheService cacheService, SqlbriteService sqlbriteService) {
        this.cacheService = cacheService;
        this.sqlbriteService = sqlbriteService;
    }

    @Override
    public Observable<List<User>> getUsers() {
        // TODO : sqlbrite, realm, retrofit, firebase getUsers..
        /**
         * cache
         */
//        Observable<List<User>> cacheUsers = cacheService.getUsers()
//                .flatMap(new Func1<List<User>, Observable<User>>() {
//                @Override
//                public Observable<User> call(List<User> users) {
//                    Log.e("fisache", "user is empty ? "+ users.isEmpty());
//                    if (users.isEmpty()) {
//                        return Observable.empty();
//                    } else {
//                        return Observable.from(users);
//                    }
//                }
//                })
//                .doOnNext(new Action1<User>() {
//                    @Override
//                    public void call(User user) {
//                        Log.e("fisache", "doOnNext");
//                    }
//                })
//                .toList()
//                .doOnCompleted(new Action0() {
//                    @Override
//                    public void call() {
//                        Log.e("fisache", "doOnCompleted");
//                    }
//                });
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());

//        return cacheUsers;
        /**
         * sqlbrite
         * will add realm in concat
         */
        Observable<List<User>> localUsers = sqlbriteService.getUsers();
        return Observable.concat(localUsers, localUsers).first();
    }

    @Override
    public Observable<User> getUser(final long id) {
        /**
         * cache, sqlbrite same logic
         */
//        Observable<User> cacheUser = cacheService.getUser(id)
        Observable<User> cacheUser = sqlbriteService.getUser(id)
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
        return cacheUser;
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
    public void updateDesc(User user) {
        /**
         * cache, sqlbrite same logic
         */
//        cacheService.updateDesc(user);
        sqlbriteService.updateDesc(user);
    }
}
