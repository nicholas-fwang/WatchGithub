package io.fisache.watchgithub.data.manager;


import android.support.annotation.VisibleForTesting;

import java.util.List;

import io.fisache.watchgithub.data.GithubApiService;
import io.fisache.watchgithub.data.model.UserResponse;
import io.fisache.watchgithub.data.model.User;
import io.fisache.watchgithub.service.SchedulerProvider;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class GithubUserManager {
    private GithubApiService githubApiService;

    private SchedulerProvider schedulerProvider = SchedulerProvider.getInstance();

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
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui());
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

    @VisibleForTesting
    public void createUser(User user) {
        githubApiService.createGithubUser(user);
    }

    @VisibleForTesting
    public void updateUser(User user) {
        githubApiService.updateGithubUser(user);
    }

    @VisibleForTesting
    public void deleteUser(String login) {
        githubApiService.deleteGithubUser(login);
    }

    @VisibleForTesting
    public void deleteUserAll() {
        githubApiService.deleteGithubUserAll();
    }
}
