package io.fisache.watchgithub.data;

import dagger.Subcomponent;
import io.fisache.watchgithub.data.github.GithubUserModule;
import io.fisache.watchgithub.scope.UserScope;
import io.fisache.watchgithub.ui.repositorieslist.RepositoriesListActivityComponent;
import io.fisache.watchgithub.ui.repositorieslist.RepositoriesListActivityModule;

@UserScope
@Subcomponent(
        modules = {
                UserModule.class,
                GithubUserModule.class
        }
)
public interface UserComponent {
    // TODO : plus, RepositoriesListActivity
    RepositoriesListActivityComponent plus(RepositoriesListActivityModule repositoriesListActivityModule);
}
