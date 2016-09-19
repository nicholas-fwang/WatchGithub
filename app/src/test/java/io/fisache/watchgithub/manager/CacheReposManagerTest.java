package io.fisache.watchgithub.manager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import io.fisache.watchgithub.data.cache.CacheService;
import io.fisache.watchgithub.data.cache.UserRepoCache;
import io.fisache.watchgithub.data.manager.CacheRepositoriesManager;
import io.fisache.watchgithub.data.model.Repository;
import io.fisache.watchgithub.data.model.User;
import io.fisache.watchgithub.mock.UserMock;
import rx.Observable;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CacheReposManagerTest {

    @Mock
    CacheService cacheService;

    User user;

    CacheRepositoriesManager cacheRepositoriesManager;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        user = UserMock.newInstance();

        cacheRepositoriesManager = new CacheRepositoriesManager(user, cacheService);
    }

    @Test
    public void getUserRepositories() {
        when(cacheService.getRepositories(anyString(), anyInt()))
                .thenReturn(Observable.from(new ArrayList<Repository>()).toList());

        cacheRepositoriesManager.getUserRepositories(1);

        verify(cacheService).getRepositories(user.login, 1);
    }

    @Test
    public void replaceCache_replace() {
        List<Repository> repositories = new ArrayList<>();

        cacheRepositoriesManager.replaceCache(repositories, 1);

        verify(cacheService).replaceCache((UserRepoCache) any());
    }

    @Test
    public void replaceCache_addRepos() {
        List<Repository> repositories = new ArrayList<>();

        cacheRepositoriesManager.replaceCache(repositories, 2);

        verify(cacheService).addRepositoriesAndRepoPage(user.login, repositories, 2);
    }

    @Test
    public void getCachedRepoPage() {
        User user = UserMock.newInstance();

        when(cacheService.getCachedRepoPage(user.login)).thenReturn(1);

        cacheRepositoriesManager.getCachedRepoPage(user.login);

        verify(cacheService).getCachedRepoPage(user.login);
    }
}
