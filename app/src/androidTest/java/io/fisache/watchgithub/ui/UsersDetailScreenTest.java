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
import io.fisache.watchgithub.data.manager.UsersManager;
import io.fisache.watchgithub.data.model.User;
import io.fisache.watchgithub.mock.ApplicationTest;
import io.fisache.watchgithub.mock.TestUtils;
import io.fisache.watchgithub.mock.UserDummy;
import io.fisache.watchgithub.service.Injection;
import io.fisache.watchgithub.ui.userdetail.UserDetailActivity;
import rx.Observable;


import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class UsersDetailScreenTest {
    ApplicationTest app;

    private static final String ARG_USER = "arg_user";

    UsersManager usersManager;

    User user;

    @Rule
    public ActivityTestRule<UserDetailActivity> userDetailActivityTestRule =
            new ActivityTestRule<>(UserDetailActivity.class, true, false);

    @Before
    public void setup() {
        app = (ApplicationTest) InstrumentationRegistry.getTargetContext().getApplicationContext();
        app.setupAppComponent();
        app.createGroupComponent();

        usersManager = Injection.getInstance_UsersManager();

        usersManager.deleteAllUser();

        user = UserDummy.newInstance();
        user.desc = "dummy";

        Intent startIntent = new Intent();
        startIntent.putExtra(ARG_USER, user);
        userDetailActivityTestRule.launchActivity(startIntent);
    }

    @After
    public void tearDown() {
        app.releaseGroupComponent();
    }

    @Test
    public void writeDesc_checkDescDisplayed() {
        String desc = "Test";
        updateUserDescription(desc);

        onView(withId(R.id.tvDesc)).check(matches(withText(desc)));
    }

    @Test
    public void clickBackBtn_finishActivity() {
        onView(withContentDescription(TestUtils.getToolbarNavigationContentDescription(
                userDetailActivityTestRule.getActivity(), R.id.toolbar
        ))).perform(click());
        assertTrue(userDetailActivityTestRule.getActivity().isFinishing());
    }

    @Test
    public void clickDeleteBtn_finishActivity() {
        onView(withId(R.id.itUserDelete)).perform(click());
        assertTrue(userDetailActivityTestRule.getActivity().isFinishing());
    }

    private void updateUserDescription(String description) {
        checkUserData();
        onView(withId(R.id.tvDesc)).perform(click());
        onView(withId(R.id.etDesc)).perform(typeText(description), closeSoftKeyboard());
        onView(withText("Save")).perform(click());
    }

    private void checkUserData() {
        onView(withId(R.id.tvLogin)).check(matches(withText(user.login)));
        onView(withId(R.id.tvName)).check(matches(withText(user.name)));
        onView(withId(R.id.tvEmail)).check(matches(withText(user.email)));
        onView(withId(R.id.tvType)).check(matches(withText(user.type)));
        onView(withId(R.id.tvDesc)).check(matches(withText(user.desc)));
    }
}
