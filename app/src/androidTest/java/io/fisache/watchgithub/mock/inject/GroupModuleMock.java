package io.fisache.watchgithub.mock.inject;

import io.fisache.watchgithub.data.cache.CacheService;
import io.fisache.watchgithub.data.local.GroupModule;
import io.fisache.watchgithub.data.local.UsersManager;
import io.fisache.watchgithub.scope.GroupScope;

public class GroupModuleMock extends GroupModule {

    private UsersManager usersManager;

    public GroupModuleMock(UsersManager usersManager) {
        this.usersManager = usersManager;
    }


}
