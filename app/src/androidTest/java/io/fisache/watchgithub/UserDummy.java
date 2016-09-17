package io.fisache.watchgithub;

import java.util.Random;

import io.fisache.watchgithub.data.model.User;

public class UserDummy {
    public static User newInstance() {
        int random = new Random().nextInt(100);
        return new User(random, "User"+random, "Name"+random, "URL"+random, "Email"+random,
                random, "Type"+random, "");
    }
}
