package io.fisache.watchgithub.base;

import android.text.TextUtils;

public class Validator {
    public Validator() {

    }
    public boolean vaildUsername(String username) {
        return !TextUtils.isEmpty(username);
    }
}
