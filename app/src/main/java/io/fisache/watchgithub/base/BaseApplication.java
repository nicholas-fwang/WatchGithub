package io.fisache.watchgithub.base;

import android.app.Application;
import android.content.Context;

public class BaseApplication extends Application {

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
    }
}
