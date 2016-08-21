package io.fisache.watchgithub.mock.inject;

import io.fisache.watchgithub.data.GroupModule;
import io.fisache.watchgithub.data.UsersManager;
import io.fisache.watchgithub.data.cache.CacheService;
import io.fisache.watchgithub.scope.GroupScope;

public class GroupModuleMock extends GroupModule {

    private UsersManager usersManager;

    public GroupModuleMock(UsersManager usersManager) {
        this.usersManager = usersManager;
    }


}
