package com.example.evelina.blomapp;

/**
 * Created by Evelina on 2017-11-14.
 */

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;

public class ActivitySwitcher {

    private static final String LOG_TAG = ActivitySwitcher.class.getSimpleName();

    public static void switchActivity(Activity activity, MenuItem item) {
        Log.d(LOG_TAG, "Switching to activity: " + item);

        switch (item.getItemId()) {

            case R.id.navigation_search:
                Intent intentSearch = new Intent(activity, Search.class);
                activity.startActivity(intentSearch);
                break;

            case R.id.navigation_flowers:
                Intent intentAddplan = new Intent(activity, MainActivity.class);
                activity.startActivity(intentAddplan);
                break;

            case R.id.navigation_profile:
                Intent intentMap = new Intent(activity, Profile.class);
                activity.startActivity(intentMap);
                break;

            case R.id.navigation_settings:
                Intent intentSettings = new Intent(activity, Settings.class);
                activity.startActivity(intentSettings);
                break;

        }
        activity.finish();
    }

    public static void selectActivity(Activity activity, MenuItem item) {
        ActivitySwitcher.switchActivity(activity, item);
    }

}
