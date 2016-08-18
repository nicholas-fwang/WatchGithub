package io.fisache.watchgithub.data.github;

import java.util.ArrayList;
import java.util.List;

import io.fisache.watchgithub.data.model.Repository;
import io.fisache.watchgithub.data.model.User;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class GithubRepositoriesManager {
    private User user;
    private GithubApiService githubApiService;

    public GithubRepositoriesManager(User user, GithubApiService githubApiService) {
        this.user = user;
        this.githubApiService = githubApiService;
    }

    public Observable<List<Repository>> getUserRepositories() {
        return githubApiService.getGithubRepositories(user.login)
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
                            repository.url = repositoryResponse.url;
                            repository.fork_count = repositoryResponse.forks_count;
                            repository.star_count = repositoryResponse.stargazers_count;
                            list.add(repository);
                        }
                        return list;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
