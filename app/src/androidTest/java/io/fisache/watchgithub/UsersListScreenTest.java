package io.fisache.watchgithub;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Component;
import dagger.Module;
import dagger.Subcomponent;
import io.fisache.watchgithub.base.AnalyticsManager;
import io.fisache.watchgithub.base.AppComponent;
import io.fisache.watchgithub.base.AppModule;
import io.fisache.watchgithub.base.BaseApplication;
import io.fisache.watchgithub.base.DaggerAppComponent;
import io.fisache.watchgithub.base.Validator;
import io.fisache.watchgithub.data.manager.GithubUserManager;
import io.fisache.watchgithub.data.manager.UsersManager;
import io.fisache.watchgithub.data.model.User;
import io.fisache.watchgithub.data.model.UserResponse;
import io.fisache.watchgithub.mock.inject.ApplicationMock;
import io.fisache.watchgithub.scope.ActivityScope;
import io.fisache.watchgithub.service.github.FakeGithubApiService;
import io.fisache.watchgithub.service.github.GithubApiModule;
import io.fisache.watchgithub.service.sqlbrite.FakeSqlbriteService;
import io.fisache.watchgithub.service.sqlbrite.SqlbriteModule;
import io.fisache.watchgithub.ui.userslist.UsersListActivity;
import io.fisache.watchgithub.ui.userslist.UsersListActivityComponent;
import io.fisache.watchgithub.ui.userslist.UsersListActivityModule;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.containsString;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static org.hamcrest.Matchers.not;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class UsersListScreenTest {

    GithubUserManager githubUserManager;

    UsersManager usersManager;

    User user1;
    User user2;

    @Rule
    public ActivityTestRule<UsersListActivity> usersListActivityActivityTestRule =
            new ActivityTestRule<>(UsersListActivity.class, true, false);

    @Before
    public void setup() {
        ApplicationMock app = (ApplicationMock) InstrumentationRegistry.getTargetContext().getApplicationContext();
        app.setupAppComponent();
        app.createGroupComponent();
        githubUserManager = new GithubUserManager(new FakeGithubApiService());
        usersManager = new UsersManager(new FakeSqlbriteService());
        githubUserManager.deleteUserAll();
        usersManager.deleteAllUser();

        user1 = UserDummy.newInstance();
        user2 = UserDummy.newInstance();
        githubUserManager.createUser(user1);
        githubUserManager.createUser(user2);

        Intent startIntent = new Intent();
        usersListActivityActivityTestRule.launchActivity(startIntent);
    }

    @Test
    public void enterDummyUser_checkUserDisplayed() {
        enterUser(user1.login);
        enterUser(user2.login);

        onView(withText(user1.login)).check(matches(isDisplayed()));
        onView(withText(user2.login)).check(matches(isDisplayed()));
    }


    private void enterUser(String username) {
        onView(withId(R.id.btnAdd)).perform(click());
        onView(withId(R.id.etGithubId)).perform(typeText(username), closeSoftKeyboard());
        onView(withText("OK")).perform(click());
    }

}
