package io.fisache.watchgithub.ui.userdetail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fisache.watchgithub.R;
import io.fisache.watchgithub.base.AnalyticsManager;
import io.fisache.watchgithub.base.BaseActivity;
import io.fisache.watchgithub.base.BaseApplication;
import io.fisache.watchgithub.data.model.User;
import rx.Subscription;
import rx.functions.Action1;

public class UserDetailActivity extends BaseActivity {
    private static final String ARG_USER = "arg_user";

    @Bind(R.id.ivAvatar)
    ImageView ivAvatar;
    @Bind(R.id.tvLogin)
    TextView tvLogin;
    @Bind(R.id.tvName)
    TextView tvName;
    @Bind(R.id.tvEmail)
    TextView tvEmail;
    @Bind(R.id.etDesc)
    EditText etDesc;
    @Bind(R.id.btnEdit)
    FloatingActionButton btnEdit;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    AnalyticsManager analyticsManager;
    @Inject
    UserDetailActivityPresenter presenter;

    private User user;

    private Subscription textChangeSubscription;

    public static void startWithUser(User user, Activity startingActivity) {
        Intent intent = new Intent(startingActivity, UserDetailActivity.class);
        intent.putExtra(ARG_USER, user);
        startingActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        user = getIntent().getParcelableExtra(ARG_USER);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);
        ab.setTitle(user.login);

        textChangeSubscription = RxTextView.textChangeEvents(etDesc).subscribe(new Action1<TextViewTextChangeEvent>() {
            @Override
            public void call(TextViewTextChangeEvent textViewTextChangeEvent) {
                presenter.userDesc = textViewTextChangeEvent.text().toString();
            }
        });

        analyticsManager.logScreenView(getClass().getName());

        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_user_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itUserDelete :
                presenter.deleteUser();
                break;
            case android.R.id.home :
                onBackPressed();
                break;
        }
        return true;
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
        BaseApplication.get(this).getGroupComponent()
                .plus(new UserDetailActivityModule(this, user))
                .inject(this);
    }

    public void showUser() {
        Picasso.with(this).load(user.avatar_url).into(ivAvatar);
        tvLogin.setText(user.login);
        tvName.setText(user.name);
        tvEmail.setText(user.email);
        if(user.desc == null) {
            etDesc.setHint("Write about user..");
        } else {
            etDesc.setText(user.desc);
        }
    }

    @OnClick(R.id.btnEdit)
    public void onEditDescComplete() {
        presenter.updateUser();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        textChangeSubscription.unsubscribe();
    }
}
