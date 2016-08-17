package io.fisache.watchgithub.base;

import android.app.Application;

import timber.log.Timber;

public class AnalyticsManager {
    private Application application;

    public AnalyticsManager(Application application) {
        this.application = application;
    }

    public void logScreenView(String screenName) {
        Timber.d("Logged Screen name : " + screenName);
    }
}
