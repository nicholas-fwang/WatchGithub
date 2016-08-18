package io.fisache.watchgithub.base;

import javax.inject.Singleton;

import dagger.Component;
import io.fisache.watchgithub.data.GroupComponent;
import io.fisache.watchgithub.data.GroupModule;
import io.fisache.watchgithub.data.github.GithubUserComponent;
import io.fisache.watchgithub.data.cache.CacheServiceModule;
import io.fisache.watchgithub.data.github.GithubApiModule;
import io.fisache.watchgithub.data.github.GithubUserModule;

@Singleton
@Component(
        modules = {
                AppModule.class,
                GithubApiModule.class,
                CacheServiceModule.class
                // TODO : realm, sqlbrite, retrofit, firebase service module
        }
)
public interface AppComponent {

    GroupComponent plus(GroupModule groupModule);

    GithubUserComponent plus(GithubUserModule githubUserModule);
}
