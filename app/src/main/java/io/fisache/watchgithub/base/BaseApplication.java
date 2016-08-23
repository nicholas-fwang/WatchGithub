package io.fisache.watchgithub.base;

import android.app.Application;
import android.content.Context;

import io.fisache.watchgithub.data.github.GithubUserComponent;
import io.fisache.watchgithub.data.github.GithubUserModule;
import io.fisache.watchgithub.data.local.GroupComponent;
import io.fisache.watchgithub.data.local.GroupModule;
import io.fisache.watchgithub.data.model.User;

public class BaseApplication extends Application {

    private AppComponent appComponent;
    private GroupComponent groupComponent;
    private GithubUserComponent githubUserComponent;

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

    public GithubUserComponent createGithubUserComponent(User user) {
        githubUserComponent = appComponent.plus(new GithubUserModule(user));
        return githubUserComponent;
    }

    public void releaseGroupComponent() {
        groupComponent = null;
    }

    public void releaseGithubUserComponent() {
        githubUserComponent = null;
    }

    public GroupComponent getGroupComponent() {
        return groupComponent;
    }

    public GithubUserComponent getGithubUserComponent() {
        return githubUserComponent;
    }
}
