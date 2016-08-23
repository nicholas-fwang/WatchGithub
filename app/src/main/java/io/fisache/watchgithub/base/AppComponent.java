package io.fisache.watchgithub.base;

import javax.inject.Singleton;

import dagger.Component;
import io.fisache.watchgithub.data.github.GithubUserComponent;
import io.fisache.watchgithub.data.github.GithubApiModule;
import io.fisache.watchgithub.data.github.GithubUserModule;
import io.fisache.watchgithub.data.local.DataServiceModule;
import io.fisache.watchgithub.data.local.GroupComponent;
import io.fisache.watchgithub.data.local.GroupModule;

@Singleton
@Component(
        modules = {
                AppModule.class,
                GithubApiModule.class,
                DataServiceModule.class
                // TODO : realm, sqlbrite, retrofit, firebase service module
        }
)
public interface AppComponent {

    GroupComponent plus(GroupModule groupModule);

    GithubUserComponent plus(GithubUserModule githubUserModule);
}
