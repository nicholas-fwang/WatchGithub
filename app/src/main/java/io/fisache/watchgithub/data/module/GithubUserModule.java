package io.fisache.watchgithub.data.module;

import dagger.Module;
import dagger.Provides;
import io.fisache.watchgithub.data.manager.CacheRepositoriesManager;
import io.fisache.watchgithub.data.cache.CacheService;
import io.fisache.watchgithub.service.github.GithubApiService;
import io.fisache.watchgithub.data.manager.GithubRepositoriesManager;
import io.fisache.watchgithub.data.model.User;
import io.fisache.watchgithub.scope.UserScope;

@Module
public class GithubUserModule {
    private User user;

    public GithubUserModule(User user) {
        this.user = user;
    }

    @Provides
    @UserScope
    User provideUser() {
        return user;
    }

    @Provides
    @UserScope
    GithubRepositoriesManager provideGithubRepositoriesManager(User user, GithubApiService githubApiService) {
        return new GithubRepositoriesManager(user, githubApiService);
    }

    @Provides
    @UserScope
    CacheRepositoriesManager provideCacheRepositoriesManager(CacheService cacheService) {
        return new CacheRepositoriesManager(user, cacheService);
    }
}
