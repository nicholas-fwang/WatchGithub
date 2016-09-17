package io.fisache.watchgithub.mock.inject;

import io.fisache.watchgithub.base.AppComponent;
import io.fisache.watchgithub.base.AppModule;
import io.fisache.watchgithub.base.BaseApplication;
import io.fisache.watchgithub.base.DaggerAppComponent;
import io.fisache.watchgithub.service.github.GithubApiModule;
import io.fisache.watchgithub.data.component.GroupComponent;
import io.fisache.watchgithub.data.module.GroupModule;

public class ApplicationMock extends BaseApplication {
    private AppComponent appComponent;
    private GroupComponent groupComponent;

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
}
