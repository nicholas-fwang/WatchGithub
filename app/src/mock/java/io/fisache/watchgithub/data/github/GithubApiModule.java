package io.fisache.watchgithub.data.github;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class GithubApiModule {
    @Provides
    @Singleton
    GithubUserManager provideGithubUserManager() {
        return new GithubUserManager();
    }
}
