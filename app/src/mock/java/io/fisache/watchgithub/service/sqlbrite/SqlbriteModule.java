package io.fisache.watchgithub.service.sqlbrite;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.fisache.watchgithub.data.BaseService;
import io.fisache.watchgithub.data.cache.CacheService;

@Module
public class SqlbriteModule {
    @Provides
    @Singleton
    CacheService provideCacheService() {
        return new CacheService();
    }

    @Provides
    @Singleton
    BaseService provideFakeSqlbriteService() {
        return new FakeSqlbriteService();
    }
}
