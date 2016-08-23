package io.fisache.watchgithub.data.cache;

import java.util.ArrayList;
import java.util.List;

import io.fisache.watchgithub.data.model.Repository;

public class UserRepoCache {
    long userId;
    int repoPage;
    List<Repository> repositories;

    public UserRepoCache(long userId, int repoPage, List<Repository> repositories) {
        this.userId = userId;
        this.repoPage = repoPage;
        this.repositories = new ArrayList<>();
        this.repositories.addAll(repositories);
    }
}
