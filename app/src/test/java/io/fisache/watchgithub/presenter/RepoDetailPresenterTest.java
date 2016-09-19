package io.fisache.watchgithub.presenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.fisache.watchgithub.ui.repositorydetail.RepositoryDetailActivity;
import io.fisache.watchgithub.ui.repositorydetail.RepositoryDetailActivityPresenter;

import static org.mockito.Mockito.verify;

public class RepoDetailPresenterTest {

    @Mock
    RepositoryDetailActivity repositoryDetailActivity;

    RepositoryDetailActivityPresenter presenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        presenter = new RepositoryDetailActivityPresenter(repositoryDetailActivity);
    }

    @Test
    public void subscribe() {
        presenter.subscribe();

        verify(repositoryDetailActivity).showRepository();
    }
}
