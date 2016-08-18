package io.fisache.watchgithub.base;

import android.app.Application;
import android.content.Context;

import io.fisache.watchgithub.data.GroupComponent;
import io.fisache.watchgithub.data.GroupModule;
import io.fisache.watchgithub.data.UserComponent;
import io.fisache.watchgithub.data.UserModule;
import io.fisache.watchgithub.data.github.GithubUserManager;
import io.fisache.watchgithub.data.github.GithubUserModule;
import io.fisache.watchgithub.data.model.User;

public class BaseApplication extends Application {

    private AppComponent appComponent;
    private GroupComponent groupComponent;
    private UserComponent userComponent;

    public static BaseApplication get(Context context) {
        return (BaseApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        initAppComponent();
    }

    private void initAppComponent() {
        // TODO : dagger component
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public GroupComponent createGroupComponent() {
        groupComponent = appComponent.plus(new GroupModule());
        return groupComponent;
    }

    public UserComponent createUserComponent(User user) {
        userComponent = appComponent.plus(new UserModule(), new GithubUserModule(user));
        return userComponent;
    }

    public void releaseGroupComponent() {
        groupComponent = null;
    }

    public void releaseUserComponent() {
        userComponent = null;
    }

    public GroupComponent getGroupComponent() {
        return groupComponent;
    }

    // TODO : userComponent
    public UserComponent getUserComponent() {
        return userComponent;
    }
}
