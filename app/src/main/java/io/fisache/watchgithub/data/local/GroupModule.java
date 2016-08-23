package io.fisache.watchgithub.data.local;

import dagger.Module;
import dagger.Provides;
import io.fisache.watchgithub.data.sqlbrite.SqlbriteService;
import io.fisache.watchgithub.scope.GroupScope;

@Module
public class GroupModule {

    public GroupModule() {

    }

    @Provides
    @GroupScope
    UsersManager provideUsersManager(SqlbriteService sqlbriteService) {
        return new UsersManager(sqlbriteService);
    }
}
