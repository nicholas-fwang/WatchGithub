package io.fisache.watchgithub.mock;

import android.os.Bundle;

import java.util.Random;

import io.fisache.watchgithub.data.model.Repository;

public class RepositoryDummy {
    public static Repository newInstance() {
        int random = new Random().nextInt(1000);
        return new Repository(random, "Name"+random, "Html_url"+random, "Desc"+random,
                random + new Random().nextInt(100), random + new Random().nextInt(100), random > 500 ? true : false,
                "1990-11-16T04:00:00Z");
    }
}
