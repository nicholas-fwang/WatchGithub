package io.fisache.watchgithub.data.local;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.fisache.watchgithub.data.cache.CacheService;
import io.fisache.watchgithub.data.sqlbrite.SqlbriteService;

@Module
public class DataServiceModule {
    @Provides
    @Singleton
    CacheService provideCacheService(Application application) {
        return new CacheService(application);
    }

    @Provides
    @Singleton
    SqlbriteService provideSqlbriteService() {
        return new SqlbriteService();
    }
}
