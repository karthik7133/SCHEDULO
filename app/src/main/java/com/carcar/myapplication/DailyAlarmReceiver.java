package com.carcar.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.app.PendingIntent;
import android.util.Log;

public class DailyAlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("DailyAlarmReceiver", "Alarm triggered at: " + System.currentTimeMillis());
        if (intent == null) return;

        String title = "Daily Schedule Reminder";
        String message = "Tap to view today's timetable";

        // Intent to open timetable activity when notification is clicked
        Intent activityIntent = new Intent(context, timetable.class);
        activityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                context, 2001, activityIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        // Show notification
        DailyNotificationHelper.showNotification(context, title, message, pendingIntent);
    }
}
