package com.example.chimmuniapp;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class AppService extends Service {

    private static final int NOTIF_ID = 002;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        startForeground();
        return super.onStartCommand(intent, flags, startId);
    }

    private void startForeground() {

        startForeground(NOTIF_ID, new NotificationCompat.Builder(this,
                Utils.CHANNEL_ID1)
                .setOngoing(true)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(getString(R.string.app_name))
                .setContentText("Service is running background")
                .build());
    }
}
