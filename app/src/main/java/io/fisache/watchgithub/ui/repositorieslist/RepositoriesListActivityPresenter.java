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

    public static int REPOSPAGE = 1;

    private boolean dataMore = true;

    private boolean cached = false;

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
        REPOSPAGE  = 1;
        setRepositories();
    }

    @Override
    public void unsubscribe() {
        subscription = null;
        REPOSPAGE = 1;
    }

    private void setRepositories() {
        if(!dataMore) {
            return ;
        }

        activity.showLoading(true);
        subscription.clear();
        Subscription mSubscription = githubRepositoriesManager.getUserRepositories()
                .flatMap(new Func1<List<Repository>, Observable<Repository>>() {
                    @Override
                    public Observable<Repository> call(List<Repository> repositories) {
                        return Observable.from(repositories);
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

        // first data empty
        if(repositories.isEmpty() && OnRepoScrollListener.repoScrollLoading) {
            activity.showNotExistRepositories();
            return ;
        }

        // first data exist
        if(!repositories.isEmpty() && OnRepoScrollListener.repoScrollLoading) {
            activity.showExistRepositories();
            activity.setRepositories(repositories, true, false);
            return ;
        }

        // scroll

        // next data empty
        if(repositories.isEmpty()) {
            // next data not exist, so not setRepsitories call
            dataMore = false;
            activity.showNotMoreData();
        } else {
            dataMore = true;
            activity.setRepositories(repositories, false, false);
        }
        OnRepoScrollListener.repoScrollLoading = true;
    }

    public void setFilter(RepoFilterType type) {
        repoFilterType = type;
    }

    public void setNextRepositories() {
        REPOSPAGE++;
        setRepositories();
    }

    void setCacheRepositories() {
        cached = true;
        activity.showLoading(true);
        githubRepositoriesManager.getCacheUserRepositories()
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
                                cached = false;
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
                        activity.setRepositories(repositories, false, cached);
                    }
                });
    }

    public boolean isCached() {
        return cached;
    }
}
