package io.fisache.watchgithub.data.cache;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class CacheServiceModule {
    @Provides
    @Singleton
    CacheService provideCacheService(Application application) {
        return new CacheService(application);
    }
}
