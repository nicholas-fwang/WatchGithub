package io.fisache.watchgithub.data.module;

import dagger.Module;
import dagger.Provides;
import io.fisache.watchgithub.data.BaseService;
import io.fisache.watchgithub.data.manager.UsersManager;
import io.fisache.watchgithub.scope.GroupScope;

@Module
public class GroupModule {

    public GroupModule() {

    }

    @Provides
    @GroupScope
    UsersManager provideUsersManager(BaseService sqlbriteService) {
        return new UsersManager(sqlbriteService);
    }
}
