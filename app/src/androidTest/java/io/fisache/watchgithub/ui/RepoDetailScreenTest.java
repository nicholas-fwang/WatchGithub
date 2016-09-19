package io.fisache.watchgithub.ui;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.fisache.watchgithub.R;
import io.fisache.watchgithub.data.model.Repository;
import io.fisache.watchgithub.data.model.User;
import io.fisache.watchgithub.mock.ApplicationTest;
import io.fisache.watchgithub.mock.RepositoryDummy;
import io.fisache.watchgithub.mock.TestUtils;
import io.fisache.watchgithub.mock.UserDummy;
import io.fisache.watchgithub.ui.repositorydetail.RepositoryDetailActivity;
import io.fisache.watchgithub.util.TextUtils;


import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.action.ViewActions.click;
import static org.junit.Assert.assertTrue;
import static org.hamcrest.Matchers.containsString;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RepoDetailScreenTest {
    private static final String ARG_REPO = "arg_repo";

    User user;
    Repository repository;

    ApplicationTest app;

    @Rule
    public ActivityTestRule<RepositoryDetailActivity> repositoryDetailActivityTestRule =
            new ActivityTestRule<>(RepositoryDetailActivity.class, true, false);

    @Before
    public void setup() {
        repository = RepositoryDummy.newInstance();
        user = UserDummy.newInstance();

        app = (ApplicationTest) InstrumentationRegistry.getTargetContext().getApplicationContext();
        app.setupAppComponent();
        app.createGithubUserComponent(user);

        Intent startIntent = new Intent();
        startIntent.putExtra(ARG_REPO, repository);
        repositoryDetailActivityTestRule.launchActivity(startIntent);
    }

    @After
    public void tearDwon() {

    }

    @Test
    public void checkRepoDetail() {
        onView(withId(R.id.tvRepoName)).check(matches(withText(repository.name)));
        String forkstar = TextUtils.getForkStarString(repository.fork_count, repository.star_count);
        onView(withId(R.id.tvForkStar)).check(matches(withText(forkstar)));
        onView(withId(R.id.tvRepoUrl)).check(matches(withText(repository.html_url)));
        onView(withId(R.id.tvPushed)).check(matches(withText(repository.pushed_at)));
    }

    @Test
    public void clickBackBtn_finishActivity() {
        onView(withContentDescription(TestUtils.getToolbarNavigationContentDescription(
                repositoryDetailActivityTestRule.getActivity(), R.id.toolbar
        ))).perform(click());
        assertTrue(repositoryDetailActivityTestRule.getActivity().isFinishing());
    }
}
