package io.fisache.watchgithub.ui.repositorydetail;

import dagger.Module;
import dagger.Provides;
import io.fisache.watchgithub.scope.ActivityScope;

@Module
public class RepositoryDetailActivityModule {
    private RepositoryDetailActivity activity;

    public RepositoryDetailActivityModule(RepositoryDetailActivity activity) {
        this.activity = activity;
    }

    @ActivityScope
    @Provides
    RepositoryDetailActivity provideRepositoryDetailActivity() {
        return activity;
    }

    @ActivityScope
    @Provides
    RepositoryDetailActivityPresenter provideRepositoryDetailActivityPresenter() {
        return new RepositoryDetailActivityPresenter(activity);
    }
}
