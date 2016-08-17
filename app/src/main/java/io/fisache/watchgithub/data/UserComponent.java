package io.fisache.watchgithub.data;

import dagger.Subcomponent;
import io.fisache.watchgithub.scope.UserScope;

@UserScope
@Subcomponent(
        modules = {
                UserModule.class
        }
)
public interface UserComponent {
    // TODO : plus, RepositoriesListActivity, RepositoryDetailActivity
}
