package com.carcar.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver {
    private static final String TAG = "AlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) {
            Log.e(TAG, "Received null intent! Alarm may not be working.");
            return;
        }

        Log.d(TAG, "onReceive called: Alarm Triggered!");

        String title = intent.getStringExtra("title");
        String message = intent.getStringExtra("message");
        int requestCode = intent.getIntExtra("requestCode", 0);

        Log.d(TAG, "Received Alarm Details - Title: " + title + ", Message: " + message + ", Request Code: " + requestCode);

        // ✅ Show Notification
        NotificationHelper.showNotification(context,
                title != null ? title : "Class Reminder",
                message != null ? message : "Your class has started!"
        );

        // ✅ Schedule Next Alarm (Manually)
        scheduleNextAlarm(context, title, message, requestCode);
    }

    private void scheduleNextAlarm(Context context, String title, String message, int requestCode) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.WEEK_OF_YEAR, 1); // Schedule for next week

        Log.d(TAG, "Scheduling next alarm for: " + calendar.getTime());

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager == null) {
            Log.e(TAG, "Alarm service unavailable");
            Toast.makeText(context, "Alarm service unavailable", Toast.LENGTH_SHORT).show();
            return;
        }

        // ✅ Preserve original class details
        Intent newIntent = new Intent(context, AlarmReceiver.class);
        newIntent.putExtra("title", title);
        newIntent.putExtra("message", message);
        newIntent.putExtra("requestCode", requestCode);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, requestCode, newIntent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT
        );

        // ✅ Use setExactAndAllowWhileIdle() for next alarm
        alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent
        );

        Log.d(TAG, "Next Alarm Set Successfully!");
    }
}
