package io.fisache.watchgithub.data;

import dagger.Subcomponent;
import io.fisache.watchgithub.scope.GroupScope;
import io.fisache.watchgithub.ui.userslist.UsersListActivityComponent;
import io.fisache.watchgithub.ui.userslist.UsersListActivityModule;

@GroupScope
@Subcomponent(
        modules = {
                GroupModule.class
        }
)
public interface GroupComponent {
    // TODO : plus, UserDetailActivity
    UsersListActivityComponent plus(UsersListActivityModule usersListActivityModule);
}
