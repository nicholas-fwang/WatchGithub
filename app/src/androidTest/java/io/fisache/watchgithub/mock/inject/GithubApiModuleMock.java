package io.fisache.watchgithub.mock.inject;

import io.fisache.watchgithub.data.github.GithubApiModule;
import io.fisache.watchgithub.data.github.GithubApiService;
import io.fisache.watchgithub.data.github.GithubUserManager;

public class GithubApiModuleMock extends GithubApiModule {

    private GithubUserManager githubUserManagerMock;

    public GithubApiModuleMock(GithubUserManager githubUserManagerMock) {
        this.githubUserManagerMock = githubUserManagerMock;
    }

    @Override
    public GithubUserManager provideGithubApiManager(GithubApiService githubApiService) {
        return githubUserManagerMock;
    }
}
