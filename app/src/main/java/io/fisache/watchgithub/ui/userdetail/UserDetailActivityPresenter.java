package io.fisache.watchgithub.ui.userdetail;

import io.fisache.watchgithub.base.BasePresenter;
import io.fisache.watchgithub.data.UsersManager;
import io.fisache.watchgithub.data.model.User;

public class UserDetailActivityPresenter implements BasePresenter {

    private UserDetailActivity activity;

    private UsersManager usersManager;

    public String userDesc;

    public User user;

    public UserDetailActivityPresenter(UserDetailActivity activity, UsersManager usersManager, User user) {
        this.activity = activity;
        this.user = user;
        this.usersManager = usersManager;
    }

    @Override
    public void subscribe() {
        activity.showUser();
    }

    @Override
    public void unsubscribe() {

    }

    public void updateUser() {
        user.desc = userDesc;
        usersManager.saveUser(user);
        activity.finish();
    }

    public void deleteUser() {
        usersManager.deleteUser(user.id);
        activity.finish();
    }
}
