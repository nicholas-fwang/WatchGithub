package io.fisache.watchgithub.ui.userslist;

import dagger.Subcomponent;
import io.fisache.watchgithub.scope.ActivityScope;

@ActivityScope
@Subcomponent(
        modules = {
                UsersListActivityModule.class
        }
)
public interface UsersListActivityComponent {
    UsersListActivity inject(UsersListActivity activity);
}
