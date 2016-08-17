package io.fisache.watchgithub.data.github;

import io.fisache.watchgithub.data.model.User;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class GithubApiManager {
    private GithubApiService githubApiService;

    public GithubApiManager(GithubApiService githubApiService) {
        this.githubApiService = githubApiService;
    }

    public Observable<User> getGithubUser(String username) {
        return githubApiService.getGithubUser(username)
                .map(new Func1<UserResponse, User>() {
                    @Override
                    public User call(UserResponse userResponse) {
                        User user = new User();
                        user.login = userResponse.login;
                        user.id = userResponse.id;
                        user.name = userResponse.name;
                        user.avatar_url = userResponse.avatar_url;
                        user.email = userResponse.email;
                        user.followers = userResponse.followers;
                        return user;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
