package io.fisache.watchgithub.ui.repositorydetail;

import io.fisache.watchgithub.base.BasePresenter;

public class RepositoryDetailActivityPresenter implements BasePresenter {
    private RepositoryDetailActivity activity;

    public RepositoryDetailActivityPresenter(RepositoryDetailActivity activity) {
        this.activity = activity;
    }

    @Override
    public void subscribe() {
        activity.showRepository();
    }

    @Override
    public void unsubscribe() {

    }
}
