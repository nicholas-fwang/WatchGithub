package io.fisache.watchgithub.presenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import io.fisache.watchgithub.data.manager.CacheRepositoriesManager;
import io.fisache.watchgithub.data.manager.GithubRepositoriesManager;
import io.fisache.watchgithub.data.manager.GithubUserManager;
import io.fisache.watchgithub.data.model.Repository;
import io.fisache.watchgithub.data.model.User;
import io.fisache.watchgithub.mock.RepositoryMock;
import io.fisache.watchgithub.mock.UserMock;
import io.fisache.watchgithub.ui.repositorieslist.RepositoriesListActivity;
import io.fisache.watchgithub.ui.repositorieslist.RepositoriesListActivityPresenter;
import rx.Observable;
import rx.functions.Func1;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ReposListPresenterTest {
    @Mock
    RepositoriesListActivity repositoriesListActivity;

    @Mock
    GithubRepositoriesManager githubRepositoriesManager;

    @Mock
    CacheRepositoriesManager cacheRepositoriesManager;

    User user;

    RepositoriesListActivityPresenter presenter;

    @Before
    public void setup() {
        user = UserMock.newInstance();
        MockitoAnnotations.initMocks(this);

        presenter = new RepositoriesListActivityPresenter(repositoriesListActivity, user,
                githubRepositoriesManager, cacheRepositoriesManager);
    }

    @Test
    public void subscribe_showReposFromCache() {
        List<Repository> repositories = createRepositoies(10);

        when(cacheRepositoriesManager.getCachedRepoPage(anyString())).thenReturn(1);
        when(cacheRepositoriesManager.getUserRepositories(anyInt())).thenReturn(Observable.from(repositories).toList());

        presenter.subscribe();

        verify(cacheRepositoriesManager).getCachedRepoPage(user.login);
        verify(cacheRepositoriesManager).getUserRepositories(anyInt());
        verify(repositoriesListActivity).showExistRepositories();
        verify(repositoriesListActivity).setRepositories(repositories, true, false);
    }

    @Test
    public void subscribe_showReposFromRemote() {
        List<Repository> repositories = createRepositoies(10);

        when(cacheRepositoriesManager.getCachedRepoPage(anyString())).thenReturn(1);
        when(cacheRepositoriesManager.getUserRepositories(anyInt())).thenReturn(
                Observable.error(new RuntimeException("cache missing"))
                    .map(new Func1<Object, List<Repository>>() {
                        @Override
                        public List<Repository> call(Object o) {
                            return null;
                        }
                    })
        );
        when(githubRepositoriesManager.getUserRepositories(anyInt())).thenReturn(Observable.from(repositories).toList());

        presenter.subscribe();

        verify(cacheRepositoriesManager).getCachedRepoPage(user.login);
        verify(cacheRepositoriesManager).getUserRepositories(anyInt());
        verify(githubRepositoriesManager).getUserRepositories(anyInt());
        verify(cacheRepositoriesManager).replaceCache(repositories, 1);
        verify(repositoriesListActivity).showExistRepositories();
        verify(repositoriesListActivity).setRepositories(repositories, true, false);
    }

    private List<Repository> createRepositoies(int size) {
        List<Repository> repositories = new ArrayList<>();
        for(int i=0; i<size; i++) {
            repositories.add(RepositoryMock.newInstance());
        }
        return repositories;
    }

    // TODO : filtering
}
