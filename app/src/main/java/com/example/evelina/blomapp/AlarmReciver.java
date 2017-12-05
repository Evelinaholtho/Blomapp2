package com.example.evelina.blomapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Evelina on 2017-12-04.
 */

public class AlarmReciver extends BroadcastReceiver {
    String LOG_TAG = "gkgkgk";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(LOG_TAG, "inne i reciver");

    }
}
