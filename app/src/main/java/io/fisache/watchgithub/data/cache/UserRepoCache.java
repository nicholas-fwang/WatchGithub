package io.fisache.watchgithub.data.cache;

import java.util.ArrayList;
import java.util.List;

import io.fisache.watchgithub.data.model.Repository;

public class UserRepoCache {
    String login;
    int repoPage;
    List<Repository> repositories;

    public UserRepoCache(String login, int repoPage, List<Repository> repositories) {
        this.login = login;
        this.repoPage = repoPage;
        this.repositories = new ArrayList<>();
        this.repositories.addAll(repositories);
    }

    public List<Repository> getRepositories() {
        return repositories;
    }

    public int getRepoPage() {
        return repoPage;
    }

    public void setRepoPage(int repoPage) {
        this.repoPage = repoPage;
    }
}
