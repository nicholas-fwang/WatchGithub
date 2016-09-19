package io.fisache.watchgithub.service;

import io.fisache.watchgithub.data.manager.CacheRepositoriesManager;
import io.fisache.watchgithub.data.manager.GithubRepositoriesManager;
import io.fisache.watchgithub.data.manager.GithubUserManager;
import io.fisache.watchgithub.data.manager.UsersManager;
import io.fisache.watchgithub.data.model.User;

/**
 * It is only invoked when testing
 */
public class Injection {

    public static UsersManager getInstance_UsersManager() {
        return null;
    }

    public static GithubUserManager getInstance_GithubUserManager() {
        return null;
    }

    public static GithubRepositoriesManager newInstance_GithubRepositoriesManager(User user) {
        return null;
    }

    public static CacheRepositoriesManager newInstance_CacheRepositoriesManager(User user) {
        return null;
    }
}
