package io.fisache.watchgithub.data.github;

import dagger.Subcomponent;
import io.fisache.watchgithub.data.github.GithubUserModule;
import io.fisache.watchgithub.scope.UserScope;
import io.fisache.watchgithub.ui.repositorieslist.RepositoriesListActivityComponent;
import io.fisache.watchgithub.ui.repositorieslist.RepositoriesListActivityModule;
import io.fisache.watchgithub.ui.repositorydetail.RepositoryDetailActivityModule;
import io.fisache.watchgithub.ui.repositorydetail.RepositoryDetailComponent;

@UserScope
@Subcomponent(
        modules = {
                GithubUserModule.class
        }
)
public interface GithubUserComponent {
    RepositoriesListActivityComponent plus(RepositoriesListActivityModule repositoriesListActivityModule);

    RepositoryDetailComponent plus(RepositoryDetailActivityModule repositoryDetailActivityModule);
}
