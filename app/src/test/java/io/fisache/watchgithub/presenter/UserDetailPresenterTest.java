package io.fisache.watchgithub.presenter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.fisache.watchgithub.data.manager.UsersManager;
import io.fisache.watchgithub.data.model.User;
import io.fisache.watchgithub.mock.UserMock;
import io.fisache.watchgithub.ui.userdetail.UserDetailActivity;
import io.fisache.watchgithub.ui.userdetail.UserDetailActivityPresenter;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserDetailPresenterTest {
    @Mock
    UserDetailActivity userDetailActivity;

    @Mock
    UsersManager usersManager;

    User user;

    private UserDetailActivityPresenter presenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        user = UserMock.newInstance();
        presenter = new UserDetailActivityPresenter(userDetailActivity, usersManager, user);
    }

    @After
    public void tearDown() {
        presenter = null;
    }

    @Test
    public void subscribe_showUserData() {
        presenter.subscribe();

        verify(userDetailActivity).showUser();
    }

    @Test
    public void updateDesc() {
        presenter.updateUser();

        verify(usersManager).updateDesc(user);
        verify(userDetailActivity).finish();
    }

    @Test
    public void deleteUser() {
        presenter.deleteUser();

        verify(usersManager).deleteUser(user.id);
        verify(userDetailActivity).finish();
    }
}
