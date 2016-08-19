package io.fisache.watchgithub.ui.repositorieslist;

import android.support.v7.widget.LinearLayoutManager;

import dagger.Module;
import dagger.Provides;
import io.fisache.watchgithub.data.github.GithubRepositoriesManager;
import io.fisache.watchgithub.scope.ActivityScope;

@Module
public class RepositoriesListActivityModule {
    RepositoriesListActivity activity;

    public RepositoriesListActivityModule(RepositoriesListActivity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    RepositoriesListActivity provideRepositoriesListActivity() {
        return activity;
    }

    @Provides
    @ActivityScope
    RepositoriesListActivityPresenter provideRepositoriesListActivityPresenter(GithubRepositoriesManager githubRepositoriesManager) {
        return new RepositoriesListActivityPresenter(activity, githubRepositoriesManager);
    }

    @Provides
    @ActivityScope
    RepositoriesListAdapter provideRepositoriesListAdapter() {
        return new RepositoriesListAdapter(activity);
    }

    @Provides
    @ActivityScope
    LinearLayoutManager provideLinearLayoutManager() {
        return new LinearLayoutManager(activity);
    }

    @Provides
    @ActivityScope
    OnRepoScrollListener provideOnRepoScrollListener(LinearLayoutManager linearLayoutManager, RepositoriesListActivityPresenter presenter) {
        return new OnRepoScrollListener(linearLayoutManager, presenter);
    }
}
