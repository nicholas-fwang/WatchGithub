package io.fisache.watchgithub.ui;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.fisache.watchgithub.R;
import io.fisache.watchgithub.data.manager.GithubUserManager;
import io.fisache.watchgithub.data.manager.UsersManager;
import io.fisache.watchgithub.data.model.User;
import io.fisache.watchgithub.mock.ApplicationTest;
import io.fisache.watchgithub.mock.UserDummy;
import io.fisache.watchgithub.service.Injection;
import io.fisache.watchgithub.ui.userslist.UsersListActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static org.hamcrest.Matchers.not;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class UsersListScreenTest {

    ApplicationTest app;

    GithubUserManager githubUserManager;
    UsersManager usersManager;

    @Rule
    public ActivityTestRule<UsersListActivity> usersListActivityActivityTestRule =
            new ActivityTestRule<>(UsersListActivity.class, true, false);

    @Before
    public void setup() {
        app = (ApplicationTest) InstrumentationRegistry.getTargetContext().getApplicationContext();
        app.setupAppComponent();
        app.createGroupComponent();

        githubUserManager = Injection.getInstance_GithubUserManager();
        usersManager = Injection.getInstance_UsersManager();

        githubUserManager.deleteUserAll();
        usersManager.deleteAllUser();

        Intent startIntent = new Intent();
        usersListActivityActivityTestRule.launchActivity(startIntent);
    }

    @After
    public void tearDown() {
        app.releaseGroupComponent();
    }

    @Test
    public void enterNotSignedUser_displayedErrorSnackbar() {
        User user = UserDummy.newInstance();
        user.login = "Fail User";

        onView(ViewMatchers.withId(R.id.btnAdd)).perform(click());
        onView(withId(R.id.etGithubId)).perform(typeText(user.login), closeSoftKeyboard());
        onView(withText("OK")).perform(click());

        onView(withText("Write down exactly")).check(matches(isDisplayed()));
    }

    @Test
    public void enterDummyUser_checkUserDisplayed() {
        User user1 = enterUser(UserDummy.newInstance());
        User user2 = enterUser(UserDummy.newInstance());

        onView(withText(user1.login)).check(matches(isDisplayed()));
        onView(withText(user2.login)).check(matches(isDisplayed()));
    }

    @Test
    public void enterSpecificUsers_checkFiltering() {
        enterEachTypeUsers();

        onView(withId(R.id.itUserFilter)).perform(click());
        onView(withText("ALL")).perform(click());

        onView(withId(R.id.rvUserList)).check(matches(hasDescendant(withText("Plain User"))));
        onView(withId(R.id.rvUserList)).check(matches(hasDescendant(withText("Popular User"))));
        onView(withId(R.id.rvUserList)).check(matches(hasDescendant(withText("Org User"))));

        onView(withId(R.id.itUserFilter)).perform(click());
        onView(withText("POPULAR")).perform(click());

        onView(withId(R.id.rvUserList)).check(matches(hasDescendant(not(withText("Plain User")))));
        onView(withId(R.id.rvUserList)).check(matches(hasDescendant(withText("Popular User"))));
        onView(withId(R.id.rvUserList)).check(matches(hasDescendant(not(withText("Org User")))));

        onView(withId(R.id.itUserFilter)).perform(click());
        onView(withText("USER")).perform(click());

        onView(withId(R.id.rvUserList)).check(matches(hasDescendant(withText("Plain User"))));
        onView(withId(R.id.rvUserList)).check(matches(hasDescendant(withText("Popular User"))));
        onView(withId(R.id.rvUserList)).check(matches(hasDescendant(not(withText("Org User")))));

        onView(withId(R.id.itUserFilter)).perform(click());
        onView(withText("ORG")).perform(click());

        onView(withId(R.id.rvUserList)).check(matches(hasDescendant(not(withText("Plain User")))));
        onView(withId(R.id.rvUserList)).check(matches(hasDescendant(not(withText("Popular User")))));
        onView(withId(R.id.rvUserList)).check(matches(hasDescendant(withText("Org User"))));
    }

    @Test
    public void enterSpecificUsers_checkSearching() {
        enterEachTypeUsers();

        onView(withId(R.id.etSearch)).perform(typeText("P"), closeSoftKeyboard());
        onView(withId(R.id.rvUserList)).check(matches(hasDescendant(withText("Plain User"))));
        onView(withId(R.id.rvUserList)).check(matches(hasDescendant(withText("Popular User"))));
        onView(withId(R.id.rvUserList)).check(matches(hasDescendant(not(withText("Org User")))));

        onView(withId(R.id.etSearch)).perform(typeText("l"), closeSoftKeyboard());
        onView(withId(R.id.rvUserList)).check(matches(hasDescendant(not(withText("Popular User")))));

        onView(withId(R.id.etSearch)).perform(clearText());
        onView(withId(R.id.etSearch)).perform(typeText("User"), closeSoftKeyboard());
        onView(withId(R.id.rvUserList)).check(matches(hasDescendant(withText("Plain User"))));
        onView(withId(R.id.rvUserList)).check(matches(hasDescendant(withText("Popular User"))));
        onView(withId(R.id.rvUserList)).check(matches(hasDescendant(withText("Org User"))));

        onView(withId(R.id.etSearch)).perform(clearText());
        onView(withId(R.id.etSearch)).perform(typeText("g"), closeSoftKeyboard());
        onView(withId(R.id.rvUserList)).check(matches(hasDescendant(not(withText("Plain User")))));
        onView(withId(R.id.rvUserList)).check(matches(hasDescendant(not(withText("Popular User")))));
        onView(withId(R.id.rvUserList)).check(matches(hasDescendant(withText("Org User"))));
    }

    /**
     * TODO : It has tested if only one user, then have to modify to test for one more data
     */
    @Test
    public void updateDescription_checkDisplayed() {
        String updateDesc = "Test";
        enterUser(UserDummy.newInstance());
        onView(withId(R.id.ivSetting)).perform(click());

        onView(withId(R.id.tvDesc)).perform(click());
        onView(withId(R.id.etDesc)).perform(typeText(updateDesc), closeSoftKeyboard());
        onView(withText("Save")).perform(click());
        onView(withId(R.id.btnEdit)).perform(click());

        onView(withId(R.id.rvUserList)).check(matches(hasDescendant(withText(updateDesc))));
    }

    private User enterUser(User user) {
        githubUserManager.createUser(user);
        onView(withId(R.id.btnAdd)).perform(click());
        onView(withId(R.id.etGithubId)).perform(typeText(user.login), closeSoftKeyboard());
        onView(withText("OK")).perform(click());
        return user;
    }

    private void enterEachTypeUsers() {
        User plainUser = UserDummy.newInstance();
        plainUser.login = "Plain User";
        plainUser.followers = 1;
        enterUser(plainUser);

        User popularUser = UserDummy.newInstance();
        popularUser.login = "Popular User";
        popularUser.followers = 151;
        enterUser(popularUser);

        User orgUser = UserDummy.newInstance();
        orgUser.login = "Org User";
        orgUser.followers = 0;
        orgUser.type = "Organization";
        enterUser(orgUser);
    }

}
