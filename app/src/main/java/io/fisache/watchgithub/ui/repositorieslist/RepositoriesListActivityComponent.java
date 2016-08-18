package io.fisache.watchgithub.ui.repositorieslist;

import dagger.Subcomponent;
import io.fisache.watchgithub.scope.ActivityScope;
import io.fisache.watchgithub.scope.UserScope;

@ActivityScope
@Subcomponent(
        modules = {
                RepositoriesListActivityModule.class
        }
)
public interface RepositoriesListActivityComponent {
    RepositoriesListActivity inject(RepositoriesListActivity repositoriesListActivity);
}