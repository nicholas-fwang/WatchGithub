package io.fisache.watchgithub.ui;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import io.fisache.watchgithub.R;
import io.fisache.watchgithub.data.manager.CacheRepositoriesManager;
import io.fisache.watchgithub.data.manager.GithubRepositoriesManager;
import io.fisache.watchgithub.data.model.Repository;
import io.fisache.watchgithub.data.model.User;
import io.fisache.watchgithub.mock.ApplicationTest;
import io.fisache.watchgithub.mock.RepositoryDummy;
import io.fisache.watchgithub.mock.UserDummy;
import io.fisache.watchgithub.service.Injection;
import io.fisache.watchgithub.ui.repositorieslist.RepositoriesListActivity;
import io.fisache.watchgithub.ui.repositorieslist.RepositoriesListHolder;
import io.fisache.watchgithub.util.DateUtils;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.action.ViewActions.click;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ReposListScreenTest {
    private String POPULAR = "Popular Repo";
    private String ORIGIN = "Original Repo";
    private String FORKED = "Forked Repo";
    private String RECENTLY = "Recently Repo";

    private int TEST_CASE = 30;

    private int POPULAR_POS = 10;
    private int ORIGIN_POS = 15;
    private int FORKED_POS = 17;
    private int RECENTLY_POS = 5;


    ApplicationTest app;

    User user;

    private static final String ARG_USER = "arg_user";

    GithubRepositoriesManager githubRepositoriesManager;
    CacheRepositoriesManager cacheRepositoriesManager;

    @Rule
    public ActivityTestRule<RepositoriesListActivity> repositoriesListActivityTestRule =
            new ActivityTestRule<>(RepositoriesListActivity.class, true, false);

    @Before
    public void setup() {
        user = UserDummy.newInstance();

        app = (ApplicationTest) InstrumentationRegistry.getTargetContext().getApplicationContext();
        app.setupAppComponent();
        app.createGithubUserComponent(user);

        githubRepositoriesManager = Injection.newInstance_GithubRepositoriesManager(user);
        cacheRepositoriesManager = Injection.newInstance_CacheRepositoriesManager(user);

        githubRepositoriesManager.deleteReposAll();
        cacheRepositoriesManager.deleteCacheAll();

    }

    @After
    public void tearDown() {
        app.releaseGithubUserComponent();
    }


    @Test
    public void createDummyRepositories_checkDisplayed() {
        List<Repository> repositories = createDummyRepositories();
        launchWithUser();

        for(int i=0; i<30; i++) {
            onView(withId(R.id.rvRepoList)).perform(RecyclerViewActions.scrollToPosition(i));
            onView(withText(repositories.get(i).name)).check(matches(isDisplayed()));
        }
    }

    @Test
    public void createSpecificRepositories_checkFiltering() {
        List<Repository> repositories = createSpecificRepositories();
        launchWithUser();

        onView(withId(R.id.itRepoFilter)).perform(click());
        onView(withText("POPULAR")).perform(click());
        onView(withId(R.id.rvRepoList)).perform(RecyclerViewActions.scrollToHolder(customHolderMacher(POPULAR)));
        onView(withText(POPULAR)).check(matches(isDisplayed()));

        onView(withId(R.id.itRepoFilter)).perform(click());
        onView(withText("ORIGIN")).perform(click());
        onView(withId(R.id.rvRepoList)).perform(RecyclerViewActions.scrollToHolder(customHolderMacher(ORIGIN)));
        onView(withText(ORIGIN)).check(matches(isDisplayed()));

        onView(withId(R.id.itRepoFilter)).perform(click());
        onView(withText("FORKED")).perform(click());
        onView(withId(R.id.rvRepoList)).perform(RecyclerViewActions.scrollToHolder(customHolderMacher(FORKED)));
        onView(withText(FORKED)).check(matches(isDisplayed()));

        onView(withId(R.id.itRepoFilter)).perform(click());
        onView(withText("RECENTLY PUSHED")).perform(click());
        onView(withId(R.id.rvRepoList)).perform(RecyclerViewActions.scrollToHolder(customHolderMacher(RECENTLY)));
        onView(withText(RECENTLY)).check(matches(isDisplayed()));

        onView(withId(R.id.itRepoFilter)).perform(click());
        onView(withText("ALL")).perform(click());
        for(int i=0; i<TEST_CASE; i++) {
            onView(withId(R.id.rvRepoList)).perform(RecyclerViewActions.scrollToPosition(i));
            onView(withText(repositories.get(i).name)).check(matches(isDisplayed()));
        }
    }

    private List<Repository> createDummyRepositories() {

        List<Repository> repositories = new ArrayList<>();
        for(int i=0; i<TEST_CASE; i++) {
            repositories.add(RepositoryDummy.newInstance());
        }
        githubRepositoriesManager.createRepos(user.login, repositories);
        cacheRepositoriesManager.replaceCache(repositories, 1);
        return repositories;
    }

    private List<Repository> createSpecificRepositories() {
        List<Repository> repositories = createDummyRepositories();

        repositories.add(RepositoryDummy.newInstance());
        repositories.get(POPULAR_POS).name = POPULAR;
        repositories.get(POPULAR_POS).star_count = 550;

        repositories.add(RepositoryDummy.newInstance());
        repositories.get(ORIGIN_POS).name = ORIGIN;
        repositories.get(ORIGIN_POS).fork = false;

        repositories.add(RepositoryDummy.newInstance());
        repositories.get(FORKED_POS).name = FORKED;
        repositories.get(FORKED_POS).fork = true;

        repositories.add(RepositoryDummy.newInstance());
        repositories.get(RECENTLY_POS).name = RECENTLY;
        repositories.get(RECENTLY_POS).pushed_at = DateUtils.convertDateToString(DateUtils.getCurrentDate());

        githubRepositoriesManager.createRepos(user.login, repositories);
        cacheRepositoriesManager.replaceCache(repositories, 1);
        return repositories;
    }

    private void launchWithUser() {
        Intent startIntent = new Intent();
        startIntent.putExtra(ARG_USER, user);
        repositoriesListActivityTestRule.launchActivity(startIntent);
    }

    private static Matcher<RepositoriesListHolder> customHolderMacher(final String name) {
        return new TypeSafeMatcher<RepositoriesListHolder>() {
            @Override
            protected boolean matchesSafely(RepositoriesListHolder customHolder) {
                return customHolder.getHolderRepoName().equals(name);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("item in the middle");
            }
        };
    }

}
