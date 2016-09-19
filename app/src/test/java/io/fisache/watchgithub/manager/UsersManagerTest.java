package io.fisache.watchgithub.manager;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Random;

import io.fisache.watchgithub.data.BaseService;
import io.fisache.watchgithub.data.manager.UsersManager;
import io.fisache.watchgithub.data.model.User;
import io.fisache.watchgithub.mock.UserMock;
import rx.Observable;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class UsersManagerTest {
    @Mock
    BaseService sqlbriteService;

    UsersManager usersManager;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        usersManager = new UsersManager(sqlbriteService);
    }

    @After
    public void tearDown() {
        usersManager = null;
    }

    @Test
    public void getUsers() {
        when(sqlbriteService.getUsers()).thenReturn(Observable.from(new ArrayList<User>()).toList());

        usersManager.getUsers();

        verify(sqlbriteService).getUsers();
    }

    @Test
    public void saveUser() {
        User user = UserMock.newInstance();

        usersManager.saveUser(user);

        verify(sqlbriteService).saveUser(user);
    }

    @Test
    public void deleteUser() {
        long userId = new Random().nextLong();

        usersManager.deleteUser(userId);

        verify(sqlbriteService).deleteUser(userId);
    }

    @Test
    public void updateUser() {
        User user = UserMock.newInstance();

        usersManager.updateUser(user);

        verify(sqlbriteService).updateUser(user);
    }

    @Test
    public void updateDesc() {
        User user = UserMock.newInstance();

        usersManager.updateDesc(user);

        verify(sqlbriteService).updateDesc(user);
    }

    @Test
    public void searchUsersWithPattern() {
        String pattern = "pattern";

        usersManager.searchUsersWithPattern(pattern);

        verify(sqlbriteService).searchUsersWithPattern(pattern);
    }
}
