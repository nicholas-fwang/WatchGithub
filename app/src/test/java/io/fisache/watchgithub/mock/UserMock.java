package io.fisache.watchgithub.mock;

import java.util.Random;

import io.fisache.watchgithub.data.model.User;

public class UserMock {
    public static User newInstance() {
        int random = new Random().nextInt(1000);
        return new User(random, "User"+random, "Name"+random, "URL"+random, "Email"+random,
                random + new Random().nextInt(1000), "User", "");
    }
}
