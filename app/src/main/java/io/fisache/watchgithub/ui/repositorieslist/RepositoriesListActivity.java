package io.fisache.watchgithub.ui.repositorieslist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import io.fisache.watchgithub.ui.repositorydetail.RepositoryDetailActivity;

public class RepositoriesListActivity extends BaseActivity {

    private static final String ARG_USER = "arg_user";

    @Bind(R.id.llRepoNotExist)
    View llRepoNotExist;
    @Bind(R.id.llRepoExist)
    View llRepoExist;
    @Bind(R.id.rvRepoList)
    RecyclerView rvRepoList;
    @Bind(R.id.pbLoading)
    ProgressBar pbLoading;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    RepositoriesListActivityPresenter presenter;
    @Inject
    RepositoriesListAdapter repositoriesListAdapter;
    @Inject
    LinearLayoutManager linearLayoutManager;
    @Inject
    AnalyticsManager analyticsManager;
    @Inject
    OnRepoScrollListener onRepoScrollListener;

    private User user;

    public static void startWithUser(User user, Activity startingActivity) {
        Intent intent = new Intent(startingActivity, RepositoriesListActivity.class);
        intent.putExtra(ARG_USER, user);
        startingActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        user = getIntent().getParcelableExtra(ARG_USER);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_list);
        ButterKnife.bind(this);

        //set up toolbar
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);
        ab.setTitle(user.login);

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
                .getGithubUserComponent().plus(new RepositoriesListActivityModule(this, user))
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
        rvRepoList.addOnScrollListener(onRepoScrollListener);
    }

    public void setRepositories(List<Repository> repositories, boolean firstData, boolean filter) {
        repositoriesListAdapter.updateRepositoriesList(repositories, firstData, filter);
    }

    public void showLoading(boolean loading) {
        pbLoading.setVisibility(loading ? View.VISIBLE : View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_repo_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itRepoFilter :
                popUpUserMenuFilter();
                break;
            case android.R.id.home :
                onBackPressed();
        }
        return true;
    }

    private void popUpUserMenuFilter() {
        PopupMenu popup = new PopupMenu(this, this.findViewById(R.id.itRepoFilter));
        popup.getMenuInflater().inflate(R.menu.filter_repo, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.filterRepoAll:
                        presenter.setFilter(RepoFilterType.ALL);
                        break;
                    case R.id.filterRepoPop:
                        presenter.setFilter(RepoFilterType.POPULAR);
                        break;
                    case R.id.filterRepoOri:
                        presenter.setFilter(RepoFilterType.ORIGIN);
                        break;
                    case R.id.filterRepoFork:
                        presenter.setFilter(RepoFilterType.FORKED);
                        break;
                    case R.id.filterRepoRec:
                        presenter.setFilter(RepoFilterType.RECENTLY);
                }
                presenter.setCacheRepositories();
                return true;
            }
        });
        popup.show();
    }

    public void showNotMoreData() {
        Snackbar.make(findViewById(android.R.id.content), "User doesn't have more repositories", Snackbar.LENGTH_LONG).show();
    }

    public void onRepoItemClicked(Repository repository) {
        RepositoryDetailActivity.startWithRepo(repository, this);
    }

    @Override
    public void finish() {
        super.finish();
        BaseApplication.get(this).releaseGithubUserComponent();
    }
}
