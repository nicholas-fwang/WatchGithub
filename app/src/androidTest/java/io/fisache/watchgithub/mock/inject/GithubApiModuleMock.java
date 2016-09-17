package io.fisache.watchgithub.mock.inject;

import io.fisache.watchgithub.service.github.GithubApiModule;
import io.fisache.watchgithub.service.github.GithubApiService;
import io.fisache.watchgithub.data.manager.GithubUserManager;

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
