package io.fisache.watchgithub.data;

import java.util.List;

import io.fisache.watchgithub.data.cache.CacheService;
import io.fisache.watchgithub.data.model.User;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class UsersManager {
    private CacheService cacheService;

    public UsersManager(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    public Observable<List<User>> getUsers() {
        // TODO : sqlbrite, realm, retrofit, firebase getUsers..
        Observable<List<User>> cacheUsers = cacheService.getUsers()
                .flatMap(new Func1<List<User>, Observable<User>>() {
                @Override
                public Observable<User> call(List<User> users) {
                    return Observable.from(users);
                    }
                })
                .doOnNext(new Action1<User>() {
                    @Override
                    public void call(User user) {

                    }
                })
                .toList()
                .doOnCompleted(new Action0() {
                    @Override
                    public void call() {

                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        return cacheUsers;
    }

    public Observable<User> getUser(final long id) {
        Observable<User> cacheUser = cacheService.getUser(id)
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

    public void saveUser(User user) {
        cacheService.saveUser(user);
    }

    public void deleteUser(long userId) {
        cacheService.deleteUser(userId);
    }

    public void deleteAllUsers() {
        cacheService.deleteAllUser();
    }
}
