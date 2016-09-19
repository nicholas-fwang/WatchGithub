package io.fisache.watchgithub.mock;

import android.app.Activity;
import android.os.Looper;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitor;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;
import android.support.v7.widget.Toolbar;

import java.util.Collection;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

public class TestUtils {
    public static Activity getCurrentActivity() {
        if(Looper.myLooper() == Looper.getMainLooper()) {
            return getCurrentActivityOnMainThread();
        } else {
            final Activity[] topActivity = new Activity[1];
            getInstrumentation().runOnMainSync(new Runnable() {
                @Override
                public void run() {
                    topActivity[0] = getCurrentActivityOnMainThread();
                }
            });
            return topActivity[0];
        }
    }

    private static Activity getCurrentActivityOnMainThread() {
        ActivityLifecycleMonitor registry = ActivityLifecycleMonitorRegistry.getInstance();
        Collection<Activity> activities = registry.getActivitiesInStage(Stage.RESUMED);
        return activities.iterator().hasNext() ? activities.iterator().next() : null;
    }

    public static String getToolbarNavigationContentDescription(@NonNull Activity activity, @IdRes int toolbar1) {
        Toolbar toolbar = (Toolbar) activity.findViewById(toolbar1);
        if(toolbar != null) {
            return (String) toolbar.getNavigationContentDescription();
        } else {
            throw new RuntimeException("No toolbar found");
        }
    }
}
