package io.fisache.watchgithub.data.cache;

import android.app.Application;
import android.util.Log;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.fisache.watchgithub.data.BaseService;
import io.fisache.watchgithub.data.github.GithubRepositoriesManager;
import io.fisache.watchgithub.data.model.Repository;
import io.fisache.watchgithub.data.model.User;
import rx.Observable;
import rx.functions.Action1;

public class CacheService {

    private Application application;

    protected static Map<String, UserRepoCache> USER_CACHE_DATA;

    static {
        USER_CACHE_DATA = new LruCacheImpl(5, 0.75f);
    }

    public CacheService(Application application) {
        this.application = application;
    }

    public Observable<List<Repository>> getRepositories(final String login, int repoPage) {
        // hit
        UserRepoCache userRepoCache = USER_CACHE_DATA.get(login);
        if(userRepoCache != null && repoPage <= userRepoCache.getRepoPage()) {
            Log.d("fisache", "hit");
            return Observable.from(USER_CACHE_DATA.get(login).getRepositories()).toList();
        }
        // miss
        return Observable.error(new RuntimeException("cache missing"));
    }

    public void replaceCache(UserRepoCache userRepoCache) {
        USER_CACHE_DATA.put(userRepoCache.login, userRepoCache);
    }

    public void addRepositoriesAndRepoPage(String login, List<Repository> repositories, int repoPage) {
        USER_CACHE_DATA.get(login).getRepositories().addAll(repositories);
        setUserRepoPage(login, repoPage);

    }

    public int getCachedRepoPage(String login) {
        if(USER_CACHE_DATA.get(login) == null) {
            return 1;
        } else {
            return USER_CACHE_DATA.get(login).getRepoPage();
        }
    }

    public void setUserRepoPage(String login, int page) {
        USER_CACHE_DATA.get(login).setRepoPage(page);
    }
}
