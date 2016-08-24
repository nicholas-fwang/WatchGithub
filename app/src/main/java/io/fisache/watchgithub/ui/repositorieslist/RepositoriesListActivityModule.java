package io.fisache.watchgithub.ui.repositorieslist;

import android.support.v7.widget.LinearLayoutManager;

import dagger.Module;
import dagger.Provides;
import io.fisache.watchgithub.data.cache.CacheRepositoriesManager;
import io.fisache.watchgithub.data.github.GithubRepositoriesManager;
import io.fisache.watchgithub.data.model.User;
import io.fisache.watchgithub.scope.ActivityScope;

@Module
public class RepositoriesListActivityModule {
    RepositoriesListActivity activity;
    User user;

    public RepositoriesListActivityModule(RepositoriesListActivity activity, User user) {
        this.activity = activity;
        this.user = user;
    }

    @Provides
    @ActivityScope
    User provideUser() {
        return user;
    }

    @Provides
    @ActivityScope
    RepositoriesListActivity provideRepositoriesListActivity() {
        return activity;
    }

    @Provides
    @ActivityScope
    RepositoriesListActivityPresenter provideRepositoriesListActivityPresenter(GithubRepositoriesManager githubRepositoriesManager,
                                                                               CacheRepositoriesManager cacheRepositoriesManager) {
        return new RepositoriesListActivityPresenter(activity, user, githubRepositoriesManager, cacheRepositoriesManager);
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
