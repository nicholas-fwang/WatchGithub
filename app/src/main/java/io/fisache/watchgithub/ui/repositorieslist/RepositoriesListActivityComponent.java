package io.fisache.watchgithub.ui.repositorieslist;

import dagger.Subcomponent;
import io.fisache.watchgithub.scope.ActivityScope;

@ActivityScope
@Subcomponent(
        modules = {
                RepositoriesListActivityModule.class
        }
)
public interface RepositoriesListActivityComponent {
    RepositoriesListActivity inject(RepositoriesListActivity repositoriesListActivity);
}