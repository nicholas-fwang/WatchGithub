package io.fisache.watchgithub.data.manager;


import java.util.List;

import io.fisache.watchgithub.service.github.GithubApiService;
import io.fisache.watchgithub.data.model.UserResponse;
import io.fisache.watchgithub.data.model.User;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class GithubUserManager {
    private GithubApiService githubApiService;

    public GithubUserManager(GithubApiService githubApiService) {
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
                        user.type = userResponse.type;
                        return user;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<User>> getGithubUsers(List<User> users) {
        return Observable.from(users)
                .flatMap(new Func1<User, Observable<User>>() {
                    @Override
                    public Observable<User> call(final User localUser) {
                        return githubApiService.getGithubUser(localUser.login)
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
                                        user.type = userResponse.type;
                                        user.desc = localUser.desc;
                                        return user;
                                    }
                                });
                    }
                })
                .toList();
    }
}
