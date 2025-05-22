package com.carcar.myapplication;

import static android.content.Context.MODE_PRIVATE;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.res.ResourcesCompat;

public class NotificationHelper {
    public static final String CHANNEL_ID = "alarmChannel";

    public static void showNotification(Context context, String title, String message) {
        createNotificationChannel(context);

        SharedPreferences preferences = context.getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String soundUriString = preferences.getString("notification_sound_uri",
                RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION).toString());
        Uri soundUri = Uri.parse(soundUriString);

        Drawable d = ResourcesCompat.getDrawable(context.getResources(), R.drawable.smallic1, null);
        Bitmap largeIcon = null;
        if (d instanceof BitmapDrawable) {
            largeIcon = ((BitmapDrawable) d).getBitmap();
        }

        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setLargeIcon(largeIcon)
                .setSmallIcon(R.drawable.smallic1)
                .setContentTitle(title)
                .setContentText(message)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSound(soundUri)
                .build();

        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        int notificationId = (int) System.currentTimeMillis(); // Generate unique ID
        manager.notify(notificationId, notification);
    }

    static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            SharedPreferences preferences = context.getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
            String soundUriString = preferences.getString("notification_sound_uri",
                    RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION).toString());
            Uri customSoundUri = Uri.parse(soundUriString);

            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Custom Notification Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Channel for app notifications");

            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            channel.setSound(customSoundUri, audioAttributes);

            NotificationManager manager = context.getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }
}
