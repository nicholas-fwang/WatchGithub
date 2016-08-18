package io.fisache.watchgithub.data;

import dagger.Module;
import dagger.Provides;
import io.fisache.watchgithub.scope.UserScope;

@Module
public class UserModule {

    @Provides
    @UserScope
    RepositoriesManager provideRepositoriesManager() {
        return new RepositoriesManager();
    }
}
