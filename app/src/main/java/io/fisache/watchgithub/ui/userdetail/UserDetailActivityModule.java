package io.fisache.watchgithub.ui.userdetail;

import android.support.v7.app.AlertDialog;

import dagger.Module;
import dagger.Provides;
import io.fisache.watchgithub.data.UsersManager;
import io.fisache.watchgithub.data.model.User;
import io.fisache.watchgithub.scope.ActivityScope;

@Module
public class UserDetailActivityModule {
    private UserDetailActivity userDetailActivity;
    private User user;

    public UserDetailActivityModule(UserDetailActivity activity, User user) {
        userDetailActivity = activity;
        this.user = user;
    }

    @Provides
    @ActivityScope
    User provideUser() {
        return user;
    }

    @Provides
    @ActivityScope
    UserDetailActivity provideUserDetailActivity() {
        return userDetailActivity;
    }

    @Provides
    @ActivityScope
    UserDetailActivityPresenter provideUserDetailActivityPresenter(UsersManager usersManager) {
        return new UserDetailActivityPresenter(userDetailActivity, usersManager, user);
    }

    @Provides
    @ActivityScope
    AlertDialog.Builder provideAlertDialogBuilder() {
        return new AlertDialog.Builder(userDetailActivity);
    }
}
