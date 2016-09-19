package io.fisache.watchgithub.manager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import io.fisache.watchgithub.data.GithubApiService;
import io.fisache.watchgithub.data.manager.GithubUserManager;
import io.fisache.watchgithub.data.model.Repository;
import io.fisache.watchgithub.data.model.RepositoryResponse;
import io.fisache.watchgithub.data.model.User;
import io.fisache.watchgithub.data.model.UserResponse;
import io.fisache.watchgithub.mock.UserMock;
import io.fisache.watchgithub.service.FakeUtils;
import rx.Observable;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GithubUserManagerTest {
    @Mock
    GithubApiService githubApiService;

    GithubUserManager githubUserManager;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        githubUserManager = new GithubUserManager(githubApiService);
    }

    @After
    public void tearDown() {
        githubUserManager = null;
    }

    @Test
    public void getGithubUser() {
        User user = UserMock.newInstance();

        when(githubApiService.getGithubUser(anyString())).thenReturn(Observable.just((UserResponse) any()));

        githubUserManager.getGithubUser(user.login);

        verify(githubApiService).getGithubUser(user.login);
    }
}
