package io.fisache.watchgithub.mock;

import io.fisache.watchgithub.base.AppComponent;
import io.fisache.watchgithub.base.AppModule;
import io.fisache.watchgithub.base.BaseApplication;
import io.fisache.watchgithub.base.DaggerAppComponent;
import io.fisache.watchgithub.data.component.GithubUserComponent;
import io.fisache.watchgithub.data.component.GroupComponent;
import io.fisache.watchgithub.data.model.User;
import io.fisache.watchgithub.data.module.GithubUserModule;
import io.fisache.watchgithub.service.github.GithubApiModule;
import io.fisache.watchgithub.data.module.GroupModule;

public class ApplicationTest extends BaseApplication {
    private AppComponent appComponent;
    private GroupComponent groupComponent;
    private GithubUserComponent githubUserComponent;

    public void setupAppComponent() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .githubApiModule(new GithubApiModule())
                .build();
    }

    @Override
    public AppComponent getAppComponent() {
        return appComponent == null ? super.getAppComponent() : appComponent;
    }

    @Override
    public GroupComponent createGroupComponent() {
        groupComponent = appComponent.plus(new GroupModule());
        return groupComponent;
    }

    @Override
    public GroupComponent getGroupComponent() {
        return groupComponent == null ? super.getGroupComponent() : groupComponent;
    }

    @Override
    public void releaseGroupComponent() {
        groupComponent = null;
    }

    @Override
    public GithubUserComponent createGithubUserComponent(User user) {
        return githubUserComponent = appComponent.plus(new GithubUserModule(user));
    }

    @Override
    public void releaseGithubUserComponent() {
        githubUserComponent = null;
    }

    @Override
    public GithubUserComponent getGithubUserComponent() {
        return githubUserComponent;
    }
}
