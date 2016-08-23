package io.fisache.watchgithub.data.local;

import dagger.Subcomponent;
import io.fisache.watchgithub.scope.GroupScope;
import io.fisache.watchgithub.ui.userdetail.UserDetailActivityModule;
import io.fisache.watchgithub.ui.userdetail.UserDetailComponent;
import io.fisache.watchgithub.ui.userslist.UsersListActivityComponent;
import io.fisache.watchgithub.ui.userslist.UsersListActivityModule;

@GroupScope
@Subcomponent(
        modules = {
                GroupModule.class
        }
)
public interface GroupComponent {
    UsersListActivityComponent plus(UsersListActivityModule usersListActivityModule);

    UserDetailComponent plus(UserDetailActivityModule userDetailActivityModule);
}
