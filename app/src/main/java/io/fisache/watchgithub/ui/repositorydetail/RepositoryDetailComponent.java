package io.fisache.watchgithub.ui.repositorydetail;

import dagger.Subcomponent;
import io.fisache.watchgithub.scope.ActivityScope;

@ActivityScope
@Subcomponent(
        modules = {
                RepositoryDetailActivityModule.class
        }
)
public interface RepositoryDetailComponent {
    RepositoryDetailActivity inject(RepositoryDetailActivity repositoryDetailActivity);
}
