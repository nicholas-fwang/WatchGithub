package io.fisache.watchgithub.ui.repositorydetail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.fisache.watchgithub.R;
import io.fisache.watchgithub.base.BaseActivity;
import io.fisache.watchgithub.base.BaseApplication;
import io.fisache.watchgithub.data.model.Repository;
import io.fisache.watchgithub.ui.repositorieslist.RepositoriesListActivityModule;
import io.fisache.watchgithub.util.TextUtils;

public class RepositoryDetailActivity extends BaseActivity {
    private static final String ARG_REPO = "arg_repo";

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tvRepoName)
    TextView tvRepoName;
    @Bind(R.id.tvForkStar)
    TextView tvForkStar;
    @Bind(R.id.tvForked)
    TextView tvForked;
    @Bind(R.id.tvRepoUrl)
    TextView tvRepoUrl;
    @Bind(R.id.tvPushed)
    TextView tvPushed;
    @Bind(R.id.btnGithub)
    FloatingActionButton btnGithub;

    @Inject
    RepositoryDetailActivityPresenter presenter;

    private Repository repository;

    public static void startWithRepo(Repository repo, Activity startingActivity) {
        Intent intent = new Intent(startingActivity, RepositoryDetailActivity.class);
        intent.putExtra(ARG_REPO, repo);
        startingActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        repository = getIntent().getParcelableExtra(ARG_REPO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);
        ab.setTitle(repository.name);
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
        BaseApplication.get(this).getGithubUserComponent()
                .plus(new RepositoryDetailActivityModule(this))
                .inject(this);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    public void showRepository() {
        tvRepoName.setText(repository.name);
        tvRepoUrl.setText(repository.html_url);
        tvPushed.setText(repository.pushed_at);
        if(repository.fork) {
            tvForked.setText("Forked Repository");
        } else {
            tvForked.setText("Original Repository");
        }
        tvForkStar.setText(TextUtils.getForkStarString(repository.fork_count, repository.star_count));
    }
}
