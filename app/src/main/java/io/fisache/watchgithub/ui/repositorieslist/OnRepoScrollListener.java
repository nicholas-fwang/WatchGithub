package io.fisache.watchgithub.ui.repositorieslist;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class OnRepoScrollListener extends RecyclerView.OnScrollListener {

    private LinearLayoutManager linearLayoutManager;
    private RepositoriesListActivityPresenter presenter;

    private int pastVisibleItemsCount;
    private int visibleItemsCount;
    private int totalItemsCount;

    protected static boolean repoScrollLoading = true;

    public OnRepoScrollListener(LinearLayoutManager linearLayoutManager, RepositoriesListActivityPresenter presenter) {
        this.linearLayoutManager = linearLayoutManager;
        this.presenter = presenter;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        if(dy > 0) {
            if(presenter.isCached()) {
                return ;
            }
            totalItemsCount = linearLayoutManager.getItemCount();
            pastVisibleItemsCount = linearLayoutManager.findFirstVisibleItemPosition();
            visibleItemsCount = linearLayoutManager.getChildCount();
            if(repoScrollLoading) {
                if(visibleItemsCount + pastVisibleItemsCount >= totalItemsCount) {
                    repoScrollLoading = false;
                    presenter.setNextRepositories();
                }
            }
        }
    }
}
