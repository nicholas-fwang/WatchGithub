//package io.fisache.watchgithub.mock.tests;
//
//import android.content.Context;
//import android.content.Intent;
//import android.support.test.InstrumentationRegistry;
//import android.support.test.rule.ActivityTestRule;
//import android.support.test.runner.AndroidJUnit4;
//import android.test.suitebuilder.annotation.LargeTest;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//
//import java.util.List;
//
//import io.fisache.watchgithub.R;
//import io.fisache.watchgithub.data.manager.GithubUserManager;
//import io.fisache.watchgithub.data.manager.UsersManager;
//import io.fisache.watchgithub.data.model.User;
//import io.fisache.watchgithub.mock.inject.ApplicationMock;
//import io.fisache.watchgithub.mock.inject.GithubApiModuleMock;
//import io.fisache.watchgithub.mock.inject.GroupModuleMock;
//import io.fisache.watchgithub.ui.userslist.UsersListActivity;
//import rx.Observable;
//
//import static android.support.test.espresso.Espresso.onView;
//import static android.support.test.espresso.action.ViewActions.click;
//import static android.support.test.espresso.action.ViewActions.typeText;
//import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
//import static android.support.test.espresso.assertion.ViewAssertions.matches;
//import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
//import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
//import static android.support.test.espresso.matcher.ViewMatchers.withHint;
//import static android.support.test.espresso.matcher.ViewMatchers.withId;
//import static android.support.test.espresso.matcher.ViewMatchers.withText;
//import static org.hamcrest.Matchers.not;
//import static org.mockito.Matchers.anyString;
//
//@RunWith(AndroidJUnit4.class)
//@LargeTest
//public class UsersListActivitiyUITest {
//
//    @Mock
//    GithubUserManager githubUserManagerMock;
//
//    @Mock
//    UsersManager usersManagerMock;
//
//    @Rule
//    public ActivityTestRule<UsersListActivity> activityRule = new ActivityTestRule<>(
//        UsersListActivity.class, true, false
//    );
//
//    @Before
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//        GithubApiModuleMock githubApiModuleMock = new GithubApiModuleMock(githubUserManagerMock);
//        GroupModuleMock groupModuleMock = new GroupModuleMock(usersManagerMock);
//        ApplicationMock app = (ApplicationMock) getTargetContext().getApplicationContext();
//        app.setGithubApiModuleMock(githubApiModuleMock);
//        app.setGroupModuleMock(groupModuleMock);
//        activityRule.launchActivity(new Intent());
//
//    }
//
//    @After
//    public void tearDown() {
//
//    }
//
//    private Context getTargetContext() {
//        return InstrumentationRegistry.getTargetContext();
//    }
//
//    @Test
//    public void showErrorSnackbar_wrongGithubUser() {
//        Mockito.when(githubUserManagerMock.getGithubUser(anyString()))
//                .thenReturn(Observable.<User>error(new RuntimeException("test")));
//        Mockito.when(usersManagerMock.getUsers())
//                .thenReturn(Observable.<List<User>>error(new RuntimeException("test")));
//        enterUser("fisache");
//
//        onView(withText("Write down exactly")).check(matches(isDisplayed()));
//    }
//
//    @Test
//    public void showUserInList_correctGithubUser() {
//
//        User user = createNormalUser();
//
//        Mockito.when(githubUserManagerMock.getGithubUser(anyString())).
//                thenReturn(Observable.just(user));
//        Mockito.when(usersManagerMock.getUser(user.id)).
//                thenReturn(Observable.just(user));
//
//        onView(withId(R.id.pbLoading)).check(matches(not(isDisplayed())));
//
//        enterUser(user.login);
//
//        onView(withText("fisache")).check(matches(isDisplayed()));
//        onView(withHint("Go to Setting..")).check(matches(isDisplayed()));
//    }
//
//    @Test
//    public void showNotUser_clickCancelButton() {
//        onView(withId(R.id.btnAdd)).perform(click());
//        onView(withId(R.id.etGithubId)).perform(typeText("fisache"));
//        onView(withText("Cancel")).perform(click());
//        onView(withId(R.id.etGithubId)).check(doesNotExist());
//    }
//
//    @Test
//    public void showUser_filter() {
//
//        createMultiUsers();
//
//        onView(withId(R.id.itUserFilter)).perform(click());
//        onView(withText("ALL")).perform(click());
//
//        onView(withId(R.id.rvUserList)).check(matches(hasDescendant(withText("fisache"))));
//        onView(withId(R.id.rvUserList)).check(matches(hasDescendant(withText("popular"))));
//        onView(withId(R.id.rvUserList)).check(matches(hasDescendant(withText("ilion"))));
//
//        onView(withId(R.id.itUserFilter)).perform(click());
//        onView(withText("POPULAR")).perform(click());
//
//        onView(withId(R.id.rvUserList)).check(matches(hasDescendant(not(withText("fisache")))));
//        onView(withId(R.id.rvUserList)).check(matches(hasDescendant(withText("popular"))));
//        onView(withId(R.id.rvUserList)).check(matches(hasDescendant(not(withText("ilion")))));
//
//        onView(withId(R.id.itUserFilter)).perform(click());
//        onView(withText("USER")).perform(click());
//
//        onView(withId(R.id.rvUserList)).check(matches(hasDescendant(withText("fisache"))));
//        onView(withId(R.id.rvUserList)).check(matches(hasDescendant(withText("popular"))));
//        onView(withId(R.id.rvUserList)).check(matches(hasDescendant(not(withText("ilion")))));
//
//        onView(withId(R.id.itUserFilter)).perform(click());
//        onView(withText("ORG")).perform(click());
//
//        onView(withId(R.id.rvUserList)).check(matches(hasDescendant(not(withText("fisache")))));
//        onView(withId(R.id.rvUserList)).check(matches(hasDescendant(not(withText("popular")))));
//        onView(withId(R.id.rvUserList)).check(matches(hasDescendant(withText("ilion"))));
//
//    }
//
//
//    private void enterUser(String username) {
//        onView(withId(R.id.pbLoading)).check(matches(not(isDisplayed())));
//
//        onView(withId(R.id.btnAdd)).perform(click());
//        onView(withId(R.id.etGithubId)).perform(typeText(username));
//        onView(withText("OK")).perform(click());
//    }
//
//    private void createMultiUsers() {
//        User user1 = createNormalUser();
//        User user2 = createPopularUser();
//        User user3 = createOrganization();
//        Mockito.when(githubUserManagerMock.getGithubUser(user1.login)).
//                thenReturn(Observable.just(user1));
//        Mockito.when(usersManagerMock.getUser(user1.id)).
//                thenReturn(Observable.just(user1));
//        Mockito.when(githubUserManagerMock.getGithubUser(user2.login)).
//                thenReturn(Observable.just(user2));
//        Mockito.when(usersManagerMock.getUser(user2.id)).
//                thenReturn(Observable.just(user2));
//        Mockito.when(githubUserManagerMock.getGithubUser(user3.login)).
//                thenReturn(Observable.just(user3));
//        Mockito.when(usersManagerMock.getUser(user3.id)).
//                thenReturn(Observable.just(user3));
//
//        enterUser(user1.login);
//        enterUser(user2.login);
//        enterUser(user3.login);
//    }
//
//    private User createNormalUser() {
//        User user = new User();
//        user.id = 1;
//        user.login = "fisache";
//        user.avatar_url = "https://avatars.githubusercontent.com/u/15536270?v=3";
//        user.email = "hwang031451@gmail.com";
//        user.followers = 2;
//        user.type = "User";
//        return user;
//    }
//
//    private User createPopularUser() {
//        User user = new User();
//        user.id = 2;
//        user.login = "popular";
//        user.avatar_url = "https://avatars.githubusercontent.com/u/15536270?v=3";
//        user.email = "hwang031451@gmail.com";
//        user.followers = getTargetContext().getResources().getInteger(R.integer.user_popular_follower)+1;
//        user.type = "User";
//        return user;
//    }
//
//    private User createOrganization() {
//        User user = new User();
//        user.id = 3;
//        user.login = "ilion";
//        user.avatar_url = "https://avatars.githubusercontent.com/u/15536270?v=3";
//        user.email = "hwang031451@gmail.com";
//        user.type = "Organization";
//        return user;
//    }
//
//
//}
