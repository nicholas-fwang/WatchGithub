package io.fisache.watchgithub.service.sqlbrite;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import io.fisache.watchgithub.data.BaseService;
import io.fisache.watchgithub.data.model.User;
import rx.Observable;

public class FakeSqlbriteService implements BaseService {

    private static Map<Long, User> USER_LOCAL_DATA;

    static {
        USER_LOCAL_DATA = new LinkedHashMap<>();
    }

    @Override
    public Observable<List<User>> getUsers() {
        return Observable.from(USER_LOCAL_DATA.values()).toList();
    }

    @Override
    public Observable<User> getUser(long userId) {
        return Observable.just(USER_LOCAL_DATA.get(userId));
    }

    @Override
    public void saveUser(User user) {
        USER_LOCAL_DATA.put(user.id, user);
    }

    @Override
    public void updateUser(User user) {
        USER_LOCAL_DATA.put(user.id, user);
    }

    @Override
    public void updateDesc(User user) {
        USER_LOCAL_DATA.put(user.id, user);
    }

    @Override
    public void deleteAllUser() {
        USER_LOCAL_DATA.clear();
    }

    @Override
    public void deleteUser(long userId) {
        USER_LOCAL_DATA.remove(userId);
    }

    @Override
    public Observable<List<User>> searchUsersWithPattern(String pattern) {
        List<User> userList = new ArrayList<>();
        for(Long userId : USER_LOCAL_DATA.keySet()) {
            boolean flag = Pattern.matches("^[a-zA-Z0-9]*$"+pattern+"^[a-zA-Z0-9]*$", USER_LOCAL_DATA.get(userId).login);
            if(flag) {
                userList.add(USER_LOCAL_DATA.get(userId));
            }
        }
        return Observable.from(userList).toList();
    }
}
