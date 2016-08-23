package io.fisache.watchgithub.data;

import java.util.List;

import io.fisache.watchgithub.data.model.User;
import rx.Observable;

public interface BaseService {
    Observable<List<User>> getUsers();

    Observable<User> getUser(long userId);

    void saveUser(User user);

    void updateUser(User user);

    void updateDesc(User user);

    void deleteAllUser();

    void deleteUser(long userId);

}
