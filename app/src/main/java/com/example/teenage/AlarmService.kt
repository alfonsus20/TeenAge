package com.example.teenage

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat

class AlarmService : Service() {
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        var notificationIntent = Intent(this, MainActivity::class.java)

        var pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)

        var notification =
            NotificationCompat.Builder(this, "ALARM SERVICE CHANNEL").setContentTitle("Ini title")
                .setContentText("text")
                .setSmallIcon(R.drawable.logo_small)
                .setContentIntent(pendingIntent)
                .build()

        startForeground(1, notification)

        return START_STICKY
    }
}