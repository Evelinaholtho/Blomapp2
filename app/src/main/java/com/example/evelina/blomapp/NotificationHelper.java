package com.example.evelina.blomapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;

import java.sql.Time;

/**
 * Created by Evelina on 2017-11-30.
 */

class NotificationHelper extends ContextWrapper {
    private NotificationManager manager;
    public static final String PRIMARY_CHANNEL = "default";
    public static final String SECONDARY_CHANNEL = "second";
    private int hour;

    /**
     * Registers notification channels, which can be used later by individual notifications.
     *
     * @param ctx The application context
     */

    public NotificationHelper(Context ctx) {
        super(ctx);


        NotificationChannel chan2 = new NotificationChannel(SECONDARY_CHANNEL,
                getString(R.string.notitficationText), NotificationManager.IMPORTANCE_HIGH);
        chan2.setLightColor(Color.BLUE);
        chan2.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        getManager().createNotificationChannel(chan2);
    }
    public void setHour(int hour){
        this.hour = hour;
    }


    /**
     * Get a notification of type 1
     *
     * Provide the builder rather than the notification it's self as useful for making notification
     * changes.
     *
     * @param title the title of the notification
     * @param body the body text for the notification
     * @return the builder as it keeps a reference to the notification (since API 24)
     */
    public Notification.Builder getNotification1(String title, String body) {
        return new Notification.Builder(getApplicationContext(), PRIMARY_CHANNEL)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(getSmallIcon())
                .setAutoCancel(true);
    }

    /**
     * Build notification for secondary channel.
     *
     *  title Title for notification.
     *  body Message for notification.
     * @return A Notification.Builder configured with the selected channel and details
     */
    public Notification.Builder getNotification2(String title, String body) {
        return new Notification.Builder(getApplicationContext(), SECONDARY_CHANNEL)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(getSmallIcon())
                .setAutoCancel(true);
    }

    /**
     * Send a notification.
     * The ID of the notification
     *  notification The notification object
     */
    public void notify(int id, Notification.Builder notification) {
        getManager().notify(id, notification.build());
    }

    /**
     * Get the small icon for this app
     * @return The small icon resource id
     */
    private int getSmallIcon() {
        return R.drawable.plant_small;
    }

    /**
     * Get the notification manager.
     * @return The system service NotificationManager
     */
    private NotificationManager getManager() {
        if (manager == null) {
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return manager;
    }


    public void sendNotification(int id, String title) {
        Notification.Builder nb = null;
        getNotification2(title, getString(R.string.notitficationText));
        if (nb != null) {
            notify(id, nb);
        }
        }

    }
