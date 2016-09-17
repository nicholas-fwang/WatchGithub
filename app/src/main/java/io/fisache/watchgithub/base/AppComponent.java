package io.fisache.watchgithub.base;

import javax.inject.Singleton;

import dagger.Component;
import io.fisache.watchgithub.data.component.GithubUserComponent;
import io.fisache.watchgithub.service.github.GithubApiModule;
import io.fisache.watchgithub.data.module.GithubUserModule;
import io.fisache.watchgithub.service.sqlbrite.SqlbriteModule;
import io.fisache.watchgithub.data.component.GroupComponent;
import io.fisache.watchgithub.data.module.GroupModule;

@Singleton
@Component(
        modules = {
                AppModule.class,
                GithubApiModule.class,
                SqlbriteModule.class
        }
)
public interface AppComponent {

    GroupComponent plus(GroupModule groupModule);

    GithubUserComponent plus(GithubUserModule githubUserModule);
}
