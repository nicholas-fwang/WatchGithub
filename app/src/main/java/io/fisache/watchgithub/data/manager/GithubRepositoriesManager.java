package io.fisache.watchgithub.data.manager;

import android.support.annotation.VisibleForTesting;

import java.util.ArrayList;
import java.util.List;

import io.fisache.watchgithub.data.GithubApiService;
import io.fisache.watchgithub.data.model.RepositoryResponse;
import io.fisache.watchgithub.data.model.Repository;
import io.fisache.watchgithub.data.model.User;
import io.fisache.watchgithub.service.SchedulerProvider;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class GithubRepositoriesManager {
    private User user;
    private GithubApiService githubApiService;

    private SchedulerProvider schedulerProvider = SchedulerProvider.getInstance();

    public GithubRepositoriesManager(User user, GithubApiService githubApiService) {
        this.user = user;
        this.githubApiService = githubApiService;
    }

    public Observable<List<Repository>> getUserRepositories(int repoPage) {
        return githubApiService.getGithubRepositories(user.login, repoPage)
                .map(new Func1<List<RepositoryResponse>, List<Repository>>() {
                    @Override
                    public List<Repository> call(List<RepositoryResponse> repositoryResponses) {
                        final List<Repository> list = new ArrayList<>();
                        for(RepositoryResponse repositoryResponse : repositoryResponses) {
                            Repository repository = new Repository();
                            repository.id = repositoryResponse.id;
                            repository.name = repositoryResponse.name;
                            repository.desc = repositoryResponse.description;
                            repository.fork = repositoryResponse.fork;
                            repository.html_url = repositoryResponse.html_url;
                            repository.fork_count = repositoryResponse.forks_count;
                            repository.star_count = repositoryResponse.stargazers_count;
                            repository.pushed_at = repositoryResponse.pushed_at;
                            list.add(repository);
                        }
                        return list;
                    }
                })
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui());
    }

    @VisibleForTesting
    public void createRepos(String login, List<Repository> repos) {
        githubApiService.createGithubRepos(login, repos);
    }

    @VisibleForTesting
    public void updateRepos(String login, List<Repository> repos) {
        githubApiService.updateGithubRepos(login, repos);
    }

    @VisibleForTesting
    public void deleteRepos(String login) {
        githubApiService.deleteGithubRepos(login);
    }

    @VisibleForTesting
    public void deleteReposAll() {
        githubApiService.deletGithubReposAll();
    }
}
