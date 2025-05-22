package com.carcar.myapplication;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class daysubinput extends AppCompatActivity {

    ArrayList<Daywiseschedule> arrayList;
    EditText e1, e2;
    Button b1, b2;
    TextView tv;
    ScheduleDao s;
    FloatingActionButton fbt,fbt2;
    rv3adapter rv3;  // Declare adapter globally
    Dialog dialog3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.daysubinput);

        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.black));

        UserDatabase db = UserDatabase.getInstance(this);
        s = db.scheduleDao();

        tv = findViewById(R.id.marks);
        e1 = findViewById(R.id.day);
        e2 = findViewById(R.id.corse);
        b1 = findViewById(R.id.addbtn);
        fbt = findViewById(R.id.fab2);

        RecyclerView r = findViewById(R.id.rv2);
        arrayList = new ArrayList<>(s.getall()); // Load existing data
        rv3 = new rv3adapter(this, arrayList);
        r.setLayoutManager(new LinearLayoutManager(this));
        r.setAdapter(rv3);

        b1.setOnClickListener(View -> {
            String day = e1.getText().toString().trim().toUpperCase();
            String Corse = e2.getText().toString().trim().toUpperCase();

            if (day.isEmpty() || Corse.isEmpty()) {
                Toast.makeText(this, "Please fill all details", Toast.LENGTH_SHORT).show();
            } else {
                s.insert(new Daywiseschedule(day, Corse)); // Insert into DB
                arrayList.clear();
                arrayList.addAll(s.getall()); // Update the same list
                rv3.notifyDataSetChanged();  // Notify adapter
                e1.setText("");
                e2.setText("");
            }
        });

        fbt.setOnClickListener(View -> {
            Intent totimetable = new Intent(this, timetable.class);
            startActivity(totimetable);
        });

        dialog3 = new Dialog(this);
        dialog3.setContentView(R.layout.timetonotifydialog);

        if (dialog3.getWindow() != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog3.getWindow().setBackgroundDrawable(getDrawable(R.drawable.cardviewbg1));
            dialog3.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        dialog3.setCancelable(false);

        Button btn = dialog3.findViewById(R.id.verify);
        EditText et= dialog3.findViewById(R.id.password);

        btn.setOnClickListener(View->{
            if(et==null){
                Toast.makeText(this, "Not found", Toast.LENGTH_SHORT).show();
            }else {
                String time = et.getText().toString().trim();
                if (time.isEmpty()) {
                    Toast.makeText(this, "Please enter time to remind", Toast.LENGTH_SHORT).show();
                } else if (isValid24HourFormat(time)) {
                    setDailyAlarm(time);
                } else {
                    Toast.makeText(this, "Not valied 24 hr format time", Toast.LENGTH_SHORT).show();

                }
            }dialog3.dismiss();
        });


        fbt2=findViewById(R.id.fab3);
        fbt2.setOnClickListener(View->{
            dialog3.show();
        });

    }

    private void setDailyAlarm(String time) {

        String[] parts = time.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, DailyAlarmReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this, 2001, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, minutes);
        calendar.set(Calendar.SECOND, 0);

        // If the time has already passed today, schedule for tomorrow
        if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        if (alarmManager != null) {
            alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent
            );
            Log.d("DailyAlarm", "Alarm scheduled successfully.");
        }else {
            Log.e("DailyAlarm", "AlarmManager is null, failed to schedule alarm.");
        }
    }

    public static boolean isValid24HourFormat(String time) {
        String regex = "^(?:[01]\\d|2[0-3]):[0-5]\\d$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(time);
        return matcher.matches();
    }
}

