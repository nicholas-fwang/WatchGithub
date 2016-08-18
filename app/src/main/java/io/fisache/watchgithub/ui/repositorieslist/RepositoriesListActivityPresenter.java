package io.fisache.watchgithub.ui.repositorieslist;

import java.util.List;

import io.fisache.watchgithub.base.BasePresenter;
import io.fisache.watchgithub.data.RepositoriesManager;
import io.fisache.watchgithub.data.github.GithubRepositoriesManager;
import io.fisache.watchgithub.data.model.Repository;
import rx.Observer;
import rx.subscriptions.CompositeSubscription;

public class RepositoriesListActivityPresenter implements BasePresenter {
    private RepositoriesListActivity activity;
    private GithubRepositoriesManager githubRepositoriesManager;
    private RepositoriesManager repositoriesManager;
    private CompositeSubscription subscription;

    public RepositoriesListActivityPresenter(RepositoriesListActivity activity,
                                             GithubRepositoriesManager githubRepositoriesManager,
                                             RepositoriesManager repositoriesManager) {
        this.activity = activity;
        this.githubRepositoriesManager = githubRepositoriesManager;
        this.repositoriesManager = repositoriesManager;
        subscription = new CompositeSubscription();
    }

    @Override
    public void subscribe() {
        if(subscription == null) {
            subscription = new CompositeSubscription();
        }
        setRepositories();
    }

    @Override
    public void unsubscribe() {
        subscription = null;
    }

    void setRepositories() {
        activity.showLoading(true);
        githubRepositoriesManager.getUserRepositories().subscribe(new Observer<List<Repository>>() {
            @Override
            public void onCompleted() {
                activity.showLoading(false);
            }

            @Override
            public void onError(Throwable e) {
                activity.showLoading(false);
            }

            @Override
            public void onNext(List<Repository> repositories) {
                processRepositories(repositories);
            }
        });
    }

    private void processRepositories(List<Repository> repositories) {
        if(repositories.isEmpty()) {
            activity.showNotExistRepositories();
        } else {
            activity.showExistRepositories();
            activity.setRepositories(repositories);
        }
    }
}
