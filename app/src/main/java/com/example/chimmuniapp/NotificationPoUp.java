package com.example.chimmuniapp;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;

public class NotificationPoUp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        notificationChannel();
        startService(new Intent(this, AppService.class));
    }

    private void notificationChannel() {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(Utils.CHANNEL_ID,Utils.CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH);
            NotificationChannel channel1 = new NotificationChannel(Utils.CHANNEL_ID1,Utils.CHANNEL_NAME1,
                    NotificationManager.IMPORTANCE_HIGH);

            channel.setDescription(Utils.CHANNEL_DESC);
            channel.setDescription(Utils.CHANNEL_DESC1);

            NotificationManager managerCompat = getSystemService(NotificationManager.class);
            managerCompat.createNotificationChannel(channel);
            managerCompat.createNotificationChannel(channel1);
    }
    }
}
