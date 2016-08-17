package io.fisache.watchgithub.base;

import android.app.Application;
import android.content.Context;

import io.fisache.watchgithub.data.GroupComponent;
import io.fisache.watchgithub.data.GroupModule;
import io.fisache.watchgithub.data.UserComponent;

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
//        createGroupComponent();
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

    public void releaseGroupComponent() {
        groupComponent = null;
    }

    public GroupComponent getGroupComponent() {
        return groupComponent;
    }

    // TODO : userComponent
}
