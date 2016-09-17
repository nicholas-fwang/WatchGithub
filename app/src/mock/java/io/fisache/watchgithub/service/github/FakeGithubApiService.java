package io.fisache.watchgithub.service.github;

import android.support.annotation.VisibleForTesting;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.fisache.watchgithub.data.GithubApiService;
import io.fisache.watchgithub.data.model.RepositoryResponse;
import io.fisache.watchgithub.data.model.UserResponse;
import rx.Observable;

public class FakeGithubApiService implements GithubApiService{

    private static Map<String, UserResponse> USER_GITHUB_DATA;
    private static Map<String, List<RepositoryResponse>> REPO_GITHUB_DATA;

    static {
        USER_GITHUB_DATA = new LinkedHashMap<>();
        REPO_GITHUB_DATA = new LinkedHashMap<>();
    }

    @Override
    public Observable<UserResponse> getGithubUser(String username) {
        return Observable.just(USER_GITHUB_DATA.get(username));
    }

    @Override
    public Observable<List<RepositoryResponse>> getGithubRepositories(String username, int page) {
        return Observable.from(REPO_GITHUB_DATA.get(username)).toList();
    }

    @VisibleForTesting
    public void createGithubUser(UserResponse userResponse) {
        USER_GITHUB_DATA.put(userResponse.login, userResponse);
    }

    @VisibleForTesting
    public void updateGithubUser(UserResponse userResponse) {
        USER_GITHUB_DATA.put(userResponse.login, userResponse);
    }

    @VisibleForTesting
    public void deleteGithubUser(String login) {
        USER_GITHUB_DATA.remove(login);
    }

    @VisibleForTesting
    public void deleteGithubUserAll() {
        USER_GITHUB_DATA.clear();
    }

    @VisibleForTesting
    public void createGithubRepos(String login, List<RepositoryResponse> repositoryResponse) {
        REPO_GITHUB_DATA.put(login, repositoryResponse);
    }

    @VisibleForTesting
    public void updateGithubRepos(String login, List<RepositoryResponse> repositoryResponse) {
        REPO_GITHUB_DATA.put(login, repositoryResponse);
    }

    @VisibleForTesting
    public void deleteGithubRepos(String login) {
        REPO_GITHUB_DATA.remove(login);
    }

    @VisibleForTesting
    public void deletGithubReposAll() {
        REPO_GITHUB_DATA.clear();
    }
}
