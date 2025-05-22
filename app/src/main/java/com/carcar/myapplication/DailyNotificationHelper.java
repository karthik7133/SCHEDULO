package com.carcar.myapplication;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.res.ResourcesCompat;

public class DailyNotificationHelper {
    private static final String CHANNEL_ID = "daily_schedule_channel";

    public static void showNotification(Context context, String title, String message, PendingIntent pendingIntent) {
        createNotificationChannel(context);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return; // Exit if permission is not granted
        }

        // Convert drawable to bitmap for large icon
        Bitmap largeIcon = null;
        BitmapDrawable drawable = (BitmapDrawable) ResourcesCompat.getDrawable(context.getResources(), R.drawable.smallic1, null);
        if (drawable != null) {
            largeIcon = drawable.getBitmap();
        }

        NotificationManagerCompat manager2 = NotificationManagerCompat.from(context);
        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setLargeIcon(largeIcon)
                .setSmallIcon(R.drawable.smallic1)
                .setContentTitle(title)
                .setContentText(message)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        manager2.notify(2001, notification);
    }

    private static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Daily Schedule Notifications",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Reminds you daily about your schedule at 8 AM");

            NotificationManager manager = context.getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }
}
