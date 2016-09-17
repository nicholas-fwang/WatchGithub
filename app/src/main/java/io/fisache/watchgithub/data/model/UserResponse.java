package io.fisache.watchgithub.data.model;

import android.support.annotation.VisibleForTesting;

import java.util.Date;

public class UserResponse {
    public String login;
    public long id;
    public String avatar_url;
    public String gravatar_id;
    public String url;
    public String html_url;
    public String followers_url;
    public String following_url;
    public String gists_url;
    public String starred_url;
    public String subscriptions_url;
    public String organizations_url;
    public String repos_url;
    public String events_url;
    public String received_events_url;
    public String type;
    public String name;
    public String blog;
    public String location;
    public String email;
    public int public_repos;
    public int public_gists;
    public int followers;
    public int following;
    public Date created_at;
    public Date updated_at;

    @VisibleForTesting
    public UserResponse(long id, String login, String name, String avatar_url, String email, int followers,
                        String type) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.avatar_url = avatar_url;
        this.email = email;
        this.followers = followers;
        this.type = type;
    }
}