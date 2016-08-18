package io.fisache.watchgithub.data.cache;

import android.app.Application;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.fisache.watchgithub.data.BaseService;
import io.fisache.watchgithub.data.model.User;
import rx.Observable;

public class CacheService implements BaseService {
    private Application application;

    private static Map<Long, User> USER_CACHE_DATA;

    static {
        USER_CACHE_DATA = new LinkedHashMap<>();
//        addUser(25673, "fisache", "inki hwang", "https://avatars.githubusercontent.com/u/15536270?v=3", "hwang031451@gmail.com", 1,"Me");
//        addUser(25670, "pivotal", "inki hwang", "https://avatars.githubusercontent.com/u/9148?v=3", "hwang031451@gmail.com", 150,"desire");
//        addUser(25612, "fisache", "inki hwang", "https://avatars.githubusercontent.com/u/1342004?v=3", "hwang031451@gmail.com", 300,"desire");
    }

    public static void addUser(long id, String login, String name, String avatar_url, String email, int followers, String desc) {
        User newUser = new User(id, login, name, avatar_url, email, followers, desc);
        USER_CACHE_DATA.put(id, newUser);
    }

    public CacheService(Application application) {
        this.application = application;
    }

    @Override
    public Observable<List<User>> getUsers() {
        return Observable
                .from(USER_CACHE_DATA.values())
                .toList();
    }

    @Override
    public Observable<User> getUser(long userId) {
        final User user = USER_CACHE_DATA.get(userId);
        if(user != null) {
            return Observable.just(user);
        } else {
            return Observable.empty();
        }
    }

    @Override
    public void saveUser(User user) {
        USER_CACHE_DATA.put(user.id, user);
    }

    @Override
    public void deleteAllUser() {
        USER_CACHE_DATA.clear();
    }

    @Override
    public void deleteUser(long userId) {
        USER_CACHE_DATA.remove(userId);
    }
}
