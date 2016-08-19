package io.fisache.watchgithub.ui.repositorieslist;

import android.content.res.Resources;

import java.util.List;

import io.fisache.watchgithub.R;
import io.fisache.watchgithub.base.BasePresenter;
import io.fisache.watchgithub.data.github.GithubRepositoriesManager;
import io.fisache.watchgithub.data.model.Repository;
import io.fisache.watchgithub.util.DateUtils;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;

public class RepositoriesListActivityPresenter implements BasePresenter {
    private RepositoriesListActivity activity;
    private GithubRepositoriesManager githubRepositoriesManager;
    private CompositeSubscription subscription;

    private Resources resources;

    private RepoFilterType repoFilterType = RepoFilterType.ALL;

    public RepositoriesListActivityPresenter(RepositoriesListActivity activity,
                                             GithubRepositoriesManager githubRepositoriesManager) {
        this.activity = activity;
        this.githubRepositoriesManager = githubRepositoriesManager;
        subscription = new CompositeSubscription();
        resources = activity.getResources();
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
        subscription.clear();
        Subscription mSubscription = githubRepositoriesManager.getUserRepositories()
                .flatMap(new Func1<List<Repository>, Observable<Repository>>() {
                    @Override
                    public Observable<Repository> call(List<Repository> repositories) {
                        return Observable.from(repositories);
                    }
                })
                .filter(new Func1<Repository, Boolean>() {
                    @Override
                    public Boolean call(Repository repository) {
                        switch (repoFilterType) {
                            case POPULAR:
                                return repository.star_count > resources.getInteger(R.integer.repo_popular_star) ? true : false;
                            case ORIGIN:
                                return !repository.fork;
                            case FORKED:
                                return repository.fork;
                            case RECENTLY:
                                return DateUtils.getTermsFromLastPushed(repository.pushed_at) <= resources.getInteger(R.integer.repo_recent_push_terms) ? true : false;
                            case ALL:
                            default:
                                return true;
                        }
                    }
                })
                .toList()
                .subscribe(new Observer<List<Repository>>() {
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
        subscription.add(mSubscription);
    }

    private void processRepositories(List<Repository> repositories) {
        if(repositories.isEmpty()) {
            activity.showNotExistRepositories();
        } else {
            activity.showExistRepositories();
            activity.setRepositories(repositories);
        }
    }

    public void setFilter(RepoFilterType type) {
        repoFilterType = type;
    }
}
