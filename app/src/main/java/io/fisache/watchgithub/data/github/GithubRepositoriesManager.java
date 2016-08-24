package io.fisache.watchgithub.data.github;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.fisache.watchgithub.data.model.Repository;
import io.fisache.watchgithub.data.model.User;
import io.fisache.watchgithub.ui.repositorieslist.RepositoriesListActivityPresenter;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class GithubRepositoriesManager {
    private User user;
    private GithubApiService githubApiService;

    private static Map<Long, Repository> REPO_CACHE_DATA;

    static {
        REPO_CACHE_DATA = new LinkedHashMap<>();
    }

    public GithubRepositoriesManager(User user, GithubApiService githubApiService) {
        this.user = user;
        this.githubApiService = githubApiService;
        REPO_CACHE_DATA.clear();
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
                            REPO_CACHE_DATA.put(repository.id, repository);
                        }
                        return list;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<Repository>> getCacheUserRepositories() {
        return Observable
                .from(REPO_CACHE_DATA.values())
                .toList();
    }
}
