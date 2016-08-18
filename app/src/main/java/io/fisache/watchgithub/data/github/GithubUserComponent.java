package io.fisache.watchgithub.data.github;

import dagger.Subcomponent;
import io.fisache.watchgithub.data.github.GithubUserModule;
import io.fisache.watchgithub.scope.UserScope;
import io.fisache.watchgithub.ui.repositorieslist.RepositoriesListActivityComponent;
import io.fisache.watchgithub.ui.repositorieslist.RepositoriesListActivityModule;

@UserScope
@Subcomponent(
        modules = {
                GithubUserModule.class
        }
)
public interface GithubUserComponent {
    // TODO : plus RepositoriesDetailActivity
    RepositoriesListActivityComponent plus(RepositoriesListActivityModule repositoriesListActivityModule);
}
