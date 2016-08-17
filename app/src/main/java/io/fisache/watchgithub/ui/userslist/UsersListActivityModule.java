package io.fisache.watchgithub.ui.userslist;

import android.app.Dialog;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;

import dagger.Module;
import dagger.Provides;
import io.fisache.watchgithub.data.UsersManager;
import io.fisache.watchgithub.scope.ActivityScope;

@Module
public class UsersListActivityModule {
    private UsersListActivity usersListActivity;

    public UsersListActivityModule(UsersListActivity activity) {
        usersListActivity = activity;
    }

    @Provides
    @ActivityScope
    UsersListActivity provideUsersListActivity() {
        return usersListActivity;
    }

    @Provides
    @ActivityScope
    UsersListActivityPresenter provideUsersListActivityPresenter(UsersManager usersManager) {
        return new UsersListActivityPresenter(usersListActivity, usersManager);
    }

    @Provides
    @ActivityScope
    UsersListAdapter provideUsersListAdapter() {
        return new UsersListAdapter(usersListActivity);
    }

    @Provides
    @ActivityScope
    LinearLayoutManager provideLinearLayoutManager() {
        return new LinearLayoutManager(usersListActivity);
    }

    @Provides
    @ActivityScope
    AlertDialog.Builder provideAlerDialogBuilder() {
        return new AlertDialog.Builder(usersListActivity);
    }
}
