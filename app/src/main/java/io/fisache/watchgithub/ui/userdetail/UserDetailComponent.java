package io.fisache.watchgithub.ui.userdetail;

import dagger.Subcomponent;
import io.fisache.watchgithub.scope.ActivityScope;

@ActivityScope
@Subcomponent(
        modules = {
                UserDetailActivityModule.class
        }
)
public interface UserDetailComponent {
    UserDetailActivity inject(UserDetailActivity userDetailActivity);
}
