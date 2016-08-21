package io.fisache.watchgithub.mock.tests;

import android.app.Application;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import io.fisache.watchgithub.R;
import io.fisache.watchgithub.data.UsersManager;
import io.fisache.watchgithub.data.github.GithubUserManager;
import io.fisache.watchgithub.data.model.User;
import io.fisache.watchgithub.mock.inject.ApplicationMock;
import io.fisache.watchgithub.mock.inject.GithubApiModuleMock;
import io.fisache.watchgithub.mock.inject.GroupModuleMock;
import io.fisache.watchgithub.ui.userslist.UsersListActivity;
import rx.Observable;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static org.mockito.Matchers.anyString;

@RunWith(AndroidJUnit4.class)
public class UsersListActivitiyUITest {

    @Mock
    GithubUserManager githubUserManagerMock;

    @Mock
    UsersManager usersManagerMock;

    @Rule
    public ActivityTestRule<UsersListActivity> activityRule = new ActivityTestRule<>(
        UsersListActivity.class, true, false
    );

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GithubApiModuleMock githubApiModuleMock = new GithubApiModuleMock(githubUserManagerMock);
        GroupModuleMock groupModuleMock = new GroupModuleMock(usersManagerMock);
        ApplicationMock app = (ApplicationMock) InstrumentationRegistry.getTargetContext().getApplicationContext();
        app.setGithubApiModuleMock(githubApiModuleMock);
        app.setGroupModuleMock(groupModuleMock);
        activityRule.launchActivity(new Intent());
    }

    @Test
    public void checkLoadingError() {
        Mockito.when(githubUserManagerMock.getGithubUser(anyString()))
                .thenReturn(Observable.<User>error(new RuntimeException("test")));
        onView(withId(R.id.btnAdd)).perform(click());
        onView(withId(R.id.etGithubId)).perform(typeText("fisache"));
        onView(withText("OK")).perform(click());
        onView(withText("Write down exactly")).check(matches(isDisplayed()));
    }
}
