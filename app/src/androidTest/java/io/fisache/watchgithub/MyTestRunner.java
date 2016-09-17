package io.fisache.watchgithub;

import android.app.Application;
import android.content.Context;
import android.support.test.runner.AndroidJUnitRunner;

import io.fisache.watchgithub.base.BaseApplication;
import io.fisache.watchgithub.mock.inject.ApplicationMock;

public class MyTestRunner extends AndroidJUnitRunner {

    @Override
    public Application newApplication(ClassLoader cl, String className, Context context)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return super.newApplication(cl, ApplicationMock.class.getName(), context);
    }
}
