package io.fisache.watchgithub.presenter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import io.fisache.watchgithub.base.Validator;
import io.fisache.watchgithub.data.manager.GithubUserManager;
import io.fisache.watchgithub.data.manager.UsersManager;
import io.fisache.watchgithub.data.model.User;
import io.fisache.watchgithub.mock.UserMock;
import io.fisache.watchgithub.ui.userslist.UsersListActivity;
import io.fisache.watchgithub.ui.userslist.UsersListActivityPresenter;
import rx.Observable;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UsersListPresenterTest {
    @Mock
    private UsersListActivity usersListActivity;

    @Mock
    private UsersManager usersManager;

    @Mock
    private GithubUserManager githubUserManager;

    @Mock
    private Validator validator;

    private UsersListActivityPresenter presenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        presenter = new UsersListActivityPresenter(usersListActivity, usersManager, githubUserManager, validator);

    }

    @After
    public void tearDown() {
        presenter = null;
    }

    @Test
    public void subscribeUsers_showExistUsersList() {
        int size = 4;
        List<User> users = createUsers(size);

        when(usersManager.getUsers()).thenReturn(Observable.from(users).toList());
        when(githubUserManager.getGithubUsers(users)).thenReturn(Observable.from(users).toList());

        presenter.subscribe();

        verify(usersManager).getUsers();
        verify(githubUserManager).getGithubUsers(users);
        for(int i=0; i<size; i++) {
            verify(usersManager).updateUser(users.get(i));
        }
        verify(usersListActivity).setUsers(users);
        verify(usersListActivity).showExistUsers();
    }

    @Test
    public void subscribeUsers_showNotExistUser() {
        List<User> users = new ArrayList<>();
        when(usersManager.getUsers()).thenReturn(Observable.from(users).toList());

        presenter.subscribe();

        verify(usersManager).getUsers();
        verify(usersListActivity).showNotExistUsers();
    }

    @Test
    public void enterInvalidUser_showValidationError() {
        User user = UserMock.newInstance();

        when(validator.vaildUsername(anyString())).thenReturn(false);
        presenter.enterGithubUser(user.login);

        verify(validator).vaildUsername(user.login);
        verify(usersListActivity).showVaildationError();
    }

    @Test
    public void enterValidUser_getUserFromRemoteAndSetUserToLocal() {
        User user = UserMock.newInstance();

        when(validator.vaildUsername(anyString())).thenReturn(true);
        when(githubUserManager.getGithubUser(anyString())).thenReturn(Observable.just(user));

        presenter.enterGithubUser(user.login);

        verify(validator).vaildUsername(user.login);
        verify(githubUserManager).getGithubUser(user.login);
        verify(usersManager).saveUser(user);
    }

    @Test
    public void enterNotExistUser_showValidationError() {
        User user = UserMock.newInstance();

        when(validator.vaildUsername(anyString())).thenReturn(true);
        when(githubUserManager.getGithubUser(anyString())).thenReturn(Observable.<User>error(new RuntimeException("test")));

        presenter.enterGithubUser(user.login);

        verify(validator).vaildUsername(user.login);
        verify(githubUserManager).getGithubUser(user.login);
        verify(usersListActivity).showVaildationError();
    }

    // TODO : filtering, searching test case

    private List<User> createUsers(int size) {
        List<User> users = new ArrayList<>();
        for(int i=0; i<size; i++) {
            users.add(UserMock.newInstance());
        }
        return users;
    }
}
