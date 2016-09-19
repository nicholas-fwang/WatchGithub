package io.fisache.watchgithub.service;

import io.fisache.watchgithub.data.model.User;
import io.fisache.watchgithub.data.model.UserResponse;

public class FakeUtils {

    public static UserResponse convertUserToResponse(User user) {
        return new UserResponse(user.id, user.login, user.name, user.avatar_url,
                user.email, user.followers, user.type);
    }

    public static User converResponseToUser(UserResponse userResponse) {
        return new User(userResponse.id, userResponse.login, userResponse.name, userResponse.avatar_url,
                userResponse.email, userResponse.followers, userResponse.type, "");
    }
}
