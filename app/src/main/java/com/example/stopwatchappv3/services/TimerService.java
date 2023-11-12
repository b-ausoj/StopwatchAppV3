package com.example.stopwatchappv3.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.stopwatchappv3.R;

import java.util.Timer;
import java.util.TimerTask;

public class TimerService extends Service {
    public static final String TIMER_UPDATED = "timerUpdated";
    public static final String TIME_EXTRA = "timeExtra";
    private final Timer timer = new Timer();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Erstellt den Thread at fixed rate, welche alle 10ms die run() von TimeTask ausführt
        timer.scheduleAtFixedRate(new TimeTask(), 0, 10);

        final String CHANNEL_ID = "Foreground Service ID";
        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                CHANNEL_ID,
                NotificationManager.IMPORTANCE_LOW
        );
        getSystemService(NotificationManager.class).createNotificationChannel(channel);
        Notification.Builder notification = new Notification.Builder(this, CHANNEL_ID)
                .setContentText("A stopwatch is running")
                .setContentTitle("StopwatchApp")
                .setSmallIcon(R.drawable.ic_launcher_foreground);

        startForeground(1001, notification.build());
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        timer.cancel();
        super.onDestroy();
    }

    private class TimeTask extends TimerTask {

        @Override
        public void run() {
            // Zählt die zeit hoch und wird via intent zu main activity geschickt
            Intent intent = new Intent(TIMER_UPDATED);
            long time = System.currentTimeMillis(); // schickt aktuelle Zeit
            //Log.e("Service", "Service is running");
            intent.putExtra(TIME_EXTRA, time);
            sendBroadcast(intent);
        }
    }
}