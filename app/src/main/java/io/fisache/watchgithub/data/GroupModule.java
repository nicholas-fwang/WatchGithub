package io.fisache.watchgithub.data;

import dagger.Module;
import dagger.Provides;
import io.fisache.watchgithub.data.cache.CacheService;
import io.fisache.watchgithub.data.github.GithubUserManager;
import io.fisache.watchgithub.data.sqlbrite.SqlbriteService;
import io.fisache.watchgithub.scope.GroupScope;

@Module
public class GroupModule {

    public GroupModule() {

    }

    @Provides
    @GroupScope
    UsersManager provideUsersManager(CacheService cacheService, SqlbriteService sqlbriteService, GithubUserManager githubUserManager) {
        // TODO : sqlbrite, realm, retrofit, firebase service
        return new UsersManager(cacheService, sqlbriteService, githubUserManager);
    }
}
