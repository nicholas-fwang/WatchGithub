package io.fisache.watchgithub.service;

import io.fisache.watchgithub.data.manager.GithubUserManager;
import io.fisache.watchgithub.data.manager.UsersManager;
import io.fisache.watchgithub.service.github.FakeGithubApiService;
import io.fisache.watchgithub.service.sqlbrite.FakeSqlbriteService;

public class Injection {
    private static UsersManager usersManager;
    private static GithubUserManager githubUserManager;

    public static UsersManager getInstance_UsersManager() {
        if(usersManager == null) {
            return usersManager = new UsersManager(new FakeSqlbriteService());
        }
        return usersManager;
    }

    public static GithubUserManager getInstance_GithubUserManager() {
        if(githubUserManager == null) {
            return githubUserManager = new GithubUserManager(new FakeGithubApiService());
        }
        return githubUserManager;
    }
}
