package io.fisache.watchgithub.service.github;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.fisache.watchgithub.data.GithubApiService;
import io.fisache.watchgithub.data.manager.GithubUserManager;

@Module
public class GithubApiModule {

    @Provides
    @Singleton
    GithubApiService provideFakeGithubApiService() {
        return new FakeGithubApiService();
    }

    @Provides
    @Singleton
    GithubUserManager provideGithubUserManager(GithubApiService fakeGithubApiService) {
        return new GithubUserManager(fakeGithubApiService);
    }
}
