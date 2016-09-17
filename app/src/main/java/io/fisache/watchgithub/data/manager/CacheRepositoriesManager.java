package io.fisache.watchgithub.data.manager;

import java.util.List;

import io.fisache.watchgithub.data.cache.CacheService;
import io.fisache.watchgithub.data.cache.UserRepoCache;
import io.fisache.watchgithub.data.model.Repository;
import io.fisache.watchgithub.data.model.User;
import rx.Observable;
import rx.functions.Action1;

public class CacheRepositoriesManager {
    final private User user;
    private CacheService cacheService;

    public CacheRepositoriesManager(User user, CacheService cacheService) {
        this.user = user;
        this.cacheService = cacheService;
    }

    public Observable<List<Repository>> getUserRepositories(int repoPage) {
        return cacheService.getRepositories(user.login, repoPage)
                .doOnNext(new Action1<List<Repository>>() {
                    @Override
                    public void call(List<Repository> repositories) {
                        // cache hit
                    }
                })
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        // cache miss
                    }
                });

    }

    public void replaceCache(List<Repository> repositories, int repoPage)
    {
        if(repoPage == 1) {
            cacheService.replaceCache(new UserRepoCache(user.login, 1, repositories));
        } else {
            cacheService.addRepositoriesAndRepoPage(user.login, repositories, repoPage);
        }
    }

    public int getCachedRepoPage(String login) {
        return cacheService.getCachedRepoPage(login);
    }

    public void setCachedRepoPage(String login, int page) {
        cacheService.setUserRepoPage(login, page);
    }

}
