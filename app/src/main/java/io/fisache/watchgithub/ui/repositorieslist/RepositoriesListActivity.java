package io.fisache.watchgithub.ui.repositorieslist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.fisache.watchgithub.R;
import io.fisache.watchgithub.base.AnalyticsManager;
import io.fisache.watchgithub.base.BaseActivity;
import io.fisache.watchgithub.base.BaseApplication;
import io.fisache.watchgithub.data.model.Repository;
import io.fisache.watchgithub.data.model.User;

public class RepositoriesListActivity extends BaseActivity {

    @Bind(R.id.llRepoNotExist)
    View llRepoNotExist;
    @Bind(R.id.llRepoExist)
    View llRepoExist;
    @Bind(R.id.rvRepoList)
    RecyclerView rvRepoList;
    @Bind(R.id.pbLoading)
    ProgressBar pbLoading;

    @Inject
    RepositoriesListActivityPresenter presenter;
    @Inject
    RepositoriesListAdapter repositoriesListAdapter;
    @Inject
    LinearLayoutManager linearLayoutManager;
    @Inject
    AnalyticsManager analyticsManager;

    public static void startWithUser(Activity startingActivity) {
        Intent intent = new Intent(startingActivity, RepositoriesListActivity.class);
        startingActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_list);
        ButterKnife.bind(this);

        analyticsManager.logScreenView(getClass().getName());
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.subscribe();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.unsubscribe();
    }

    @Override
    protected void setupActivityComponent() {
        BaseApplication.get(this)
                .getUserComponent().plus(new RepositoriesListActivityModule(this))
                .inject(this);
    }

    public void showExistRepositories() {
        llRepoExist.setVisibility(View.VISIBLE);
        llRepoNotExist.setVisibility(View.GONE);
        showRepositoriesListView();
    }

    public void showNotExistRepositories() {
        llRepoExist.setVisibility(View.GONE);
        llRepoNotExist.setVisibility(View.VISIBLE);
    }

    public void showRepositoriesListView() {
        rvRepoList.setAdapter(repositoriesListAdapter);
        rvRepoList.setLayoutManager(linearLayoutManager);
    }

    public void setRepositories(List<Repository> repositories) {
        repositoriesListAdapter.updateRepositoriesList(repositories);
    }

    public void showLoading(boolean loading) {
        pbLoading.setVisibility(loading ? View.VISIBLE : View.GONE);
    }

}
