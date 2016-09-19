package io.fisache.watchgithub.service.github;

import android.support.annotation.VisibleForTesting;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.fisache.watchgithub.data.GithubApiService;
import io.fisache.watchgithub.data.model.Repository;
import io.fisache.watchgithub.data.model.RepositoryResponse;
import io.fisache.watchgithub.data.model.User;
import io.fisache.watchgithub.data.model.UserResponse;
import io.fisache.watchgithub.service.FakeUtils;
import rx.Observable;

public class FakeGithubApiService implements GithubApiService{

    private static Map<String, User> USER_GITHUB_DATA;
    private static Map<String, List<Repository>> REPO_GITHUB_DATA;

    static {
        USER_GITHUB_DATA = new LinkedHashMap<>();
        REPO_GITHUB_DATA = new LinkedHashMap<>();
    }

    @Override
    public Observable<UserResponse> getGithubUser(String username) {
        if(USER_GITHUB_DATA.get(username) == null) {
            return Observable.error(new RuntimeException("Not Signed User"));
        } else {
            return Observable.just(FakeUtils.convertUserToResponse(USER_GITHUB_DATA.get(username)));
        }
    }

    @Override
    public Observable<List<RepositoryResponse>> getGithubRepositories(String username, int page) {
        if(REPO_GITHUB_DATA.get(username) == null) {
            return Observable.error(new RuntimeException("Not signed User"));
        } else {
            return Observable.from(FakeUtils.convertReposToResponses(REPO_GITHUB_DATA.get(username))).toList();
        }
    }

    @VisibleForTesting
    public void createGithubUser(User user) {
        USER_GITHUB_DATA.put(user.login, user);
    }

    @VisibleForTesting
    public void updateGithubUser(User user) {
        USER_GITHUB_DATA.put(user.login, user);
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
    public void createGithubRepos(String login, List<Repository> repositories) {
        REPO_GITHUB_DATA.put(login, repositories);
    }

    @VisibleForTesting
    public void updateGithubRepos(String login, List<Repository> repositories) {
        REPO_GITHUB_DATA.put(login, repositories);
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
