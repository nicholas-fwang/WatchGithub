package io.fisache.watchgithub.manager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import io.fisache.watchgithub.data.GithubApiService;
import io.fisache.watchgithub.data.manager.GithubRepositoriesManager;
import io.fisache.watchgithub.data.model.Repository;
import io.fisache.watchgithub.data.model.RepositoryResponse;
import io.fisache.watchgithub.data.model.User;
import io.fisache.watchgithub.mock.UserMock;
import rx.Observable;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GithubReposManagerTest {
    @Mock
    GithubApiService githubApiService;

    User user;

    GithubRepositoriesManager githubRepositoriesManager;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        user = UserMock.newInstance();

        githubRepositoriesManager = new GithubRepositoriesManager(user, githubApiService);
    }

    @After
    public void tearDown() {
        githubRepositoriesManager = null;
    }

    @Test
    public void getUserRepositories() {
        when(githubApiService.getGithubRepositories(anyString(), anyInt()))
                .thenReturn(Observable.from(new ArrayList<RepositoryResponse>()).toList());

        githubRepositoriesManager.getUserRepositories(1);

        verify(githubApiService).getGithubRepositories(user.login, 1);
    }
}
