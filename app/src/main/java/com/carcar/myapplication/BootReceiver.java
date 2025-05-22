package com.carcar.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import java.util.Map;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            // Restore alarms after reboot
            SharedPreferences prefs = context.getSharedPreferences("ClassSchedule", Context.MODE_PRIVATE);
            Map<String, ?> allEntries = prefs.getAll();

            for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
                // Extract stored alarm time and day
                String[] values = entry.getValue().toString().split(",");
                String[] timeParts = values[0].split(":");
                int hour = Integer.parseInt(timeParts[0]);
                int minute = Integer.parseInt(timeParts[1]);
                String day = values[1];

                // Call MainActivity to reset alarms
                MainActivity mainActivity = new MainActivity();
                mainActivity.setAlarm(hour, minute, day, "SubjectName", Integer.parseInt(entry.getKey().replace("alarm_", "")), "Classroom");
            }
        }
    }
}
