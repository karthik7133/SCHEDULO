package com.carcar.myapplication;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_NOTIFICATION_PERMISSION = 100;

    private Spinner spinner;
    private EditText subname, starttime, endtime;
    private Button addsubject,nextpage;
    private ArrayList<TableModel> arraytable = new ArrayList<>();
    private RecyclerTableAdapter adapter;
    private ArrayList<String> scheduleList;
    private TextView textView,Classdet,schedulo;
    private CardView cardclassdet;
    public String username;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.neon_violate));


        SharedPreferences preferences = getSharedPreferences("MyAppPrefs2", MODE_PRIVATE);
        username = preferences.getString("username", "User");


        textView = findViewById(R.id.selectday);
        Shader textShader = new LinearGradient(
                0, 0, 0, textView.getTextSize(),
                new int[]{Color.rgb(255, 255, 255), Color.rgb(128, 0, 128)},
                null, Shader.TileMode.CLAMP);
        textView.getPaint().setShader(textShader);

        schedulo = findViewById(R.id.schedulo);


        Shader gradient = new LinearGradient(
                0, 0, 0, schedulo.getTextSize(),
                new int[]{Color.parseColor("#C0C0C0"), Color.parseColor("#4B0082")},
                null, Shader.TileMode.CLAMP
        );

        schedulo.getPaint().setShader(gradient);
        schedulo.invalidate();  // Refresh the view to apply the gradient


        Classdet=findViewById(R.id.classdet);

        Classdet.getPaint().setShader(textShader);

        // Get Drawable resource
        Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.smallic1, null);
        if (d == null) {
            Log.e("Notification", "Drawable resource not found");
            return;
        }
        BitmapDrawable bd = (BitmapDrawable) d;
        Bitmap LargeIcon = bd.getBitmap();



        // Initialize UI elements
        cardclassdet =findViewById(R.id.cardclassdet);

        EditText place =findViewById(R.id.place);
        subname = findViewById(R.id.subname);
        starttime = findViewById(R.id.starttime);
        endtime = findViewById(R.id.Endtime);
        addsubject = findViewById(R.id.addsubject);
        addsubject.setElevation(0);

        cardclassdet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation r = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha);
                Classdet.startAnimation(r);  // Apply animation to text
            }
        });

        starttime.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                openTimePickerDialog(starttime);

                return true;
            }


        });
        endtime.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                openTimePickerDialog(endtime);
                return true;
            }
        });



        String[] days = {"Sunday","Monday","Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        spinner = findViewById(R.id.spinner);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, days);
        spinner.setAdapter(spinnerAdapter);

        loadData();

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerTableAdapter(this, arraytable);
        recyclerView.setAdapter(adapter);



        // Add subject and schedule notification
        addsubject.setOnClickListener(view -> {
            String Place =place.getText().toString().trim().toUpperCase();
            String day = spinner.getSelectedItem().toString();
            String subject = subname.getText().toString().trim().toUpperCase();
            String startTimest = starttime.getText().toString().trim();
            String endTime = endtime.getText().toString().trim();

            if (subject.isEmpty() || startTimest.isEmpty() || endTime.isEmpty() || Place.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please fill in the required details", Toast.LENGTH_SHORT).show();


                return;

            }
            if(!isValidTimeFormat(startTimest) || !isValidTimeFormat(endTime)){
                //Toast.makeText(this, "Babu intiki velli ac veskoni panuko!", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Enter time in HH:MM format.", Toast.LENGTH_SHORT).show();
                return;
            }

            String date[]=startTimest.split(":");
            int hour=Integer.parseInt(date[0]);
            int min=Integer.parseInt(date[1]);
            Calendar calendar=Calendar.getInstance();
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            int requestCode = generateUniqueId(dayOfWeek, hour, min);



            arraytable.add(new TableModel(day, subject, startTimest, endTime,requestCode,Place));

            adapter.notifyDataSetChanged();
            recyclerView.scrollToPosition(arraytable.size() - 1);

            saveData();
            place.setText("");
            subname.setText("");
            starttime.setText("");
            endtime.setText("");




            Toast.makeText(MainActivity.this, "Subject Added", Toast.LENGTH_SHORT).show();
            try{
                if(!startTimest.matches("\\d{2}:\\d{2}")){
                    Toast.makeText(this, "Enter the time in HH:MM 24HR format ", Toast.LENGTH_SHORT).show();
                }

                String n=day.toUpperCase();

                setAlarm(hour,min,day,subject,requestCode,Place);

            }catch (Exception e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
        Intent tofullview=new Intent(MainActivity.this, fullview.class);
        nextpage=findViewById(R.id.nextpage);

        nextpage.setOnClickListener(view->{

            saveData();
            startActivity(tofullview);
        });




    }

    protected void onStart() {
        super.onStart();
        requestNotificationPermission();
    }

    private void requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // API 33+
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{android.Manifest.permission.POST_NOTIFICATIONS},
                        REQUEST_NOTIFICATION_PERMISSION
                );
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_NOTIFICATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                // You can notify the user or proceed to show notifications
            } else {
                // Permission denied
                // Handle accordingly, maybe show an explanation
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }




    // Save timetable data to shared preferences
    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("MySchedulePrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(arraytable);
        editor.putString("scheduleList", json);
        editor.apply();
    }

    // Load timetable data from shared preferences
    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("MySchedulePrefs", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("scheduleList", null);
        Type type = new TypeToken<ArrayList<TableModel>>() {}.getType();
        arraytable = gson.fromJson(json, type);

        if (arraytable == null) {
            arraytable = new ArrayList<>();
        }
    }
    public void setAlarm(int hour, int min, String day, String subname, int requestCode, String place) {
        int dayofweek = daycount(day);

        // Create a Calendar instance for the original alarm time
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, min);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.DAY_OF_WEEK, dayofweek);

        // If the original time has already passed this week, schedule it for the next week
        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.WEEK_OF_YEAR, 1);
        }

        // Create a new Calendar instance for the 10-minute-before alarm
        Calendar calendarBefore5Min = (Calendar) calendar.clone();
        calendarBefore5Min.add(Calendar.MINUTE, -10); // Subtract 10 minutes

        // Set up the AlarmManager
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager == null) {
            Toast.makeText(this, "Alarm service unavailable", Toast.LENGTH_SHORT).show();
            return;
        }

        // Intent for the 10-minute-before alarm
        Intent i1 = new Intent(MainActivity.this, AlarmReceiver.class);
        i1.putExtra("title", "Class Reminder");
        i1.putExtra("message", username+" your " + subname + " class at " + place.toUpperCase() + " starts in 10 minutes");

        // Use a unique requestCode for the 10-minute-before alarm
        int before5MinRequestCode = requestCode + 1000; // Ensure this is unique
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                MainActivity.this, before5MinRequestCode, i1, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

        // Set the 10-minute-before alarm
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendarBefore5Min.getTimeInMillis(), pendingIntent);
    }

    public static int daycount(String day){

        switch (day.trim().toLowerCase()){
            case "sun": case "sunday": return Calendar.SUNDAY;
            case "mon": case "monday": return Calendar.MONDAY;
            case "tue": case "tuesday": return Calendar.TUESDAY;
            case "wed": case "wednesday": return Calendar.WEDNESDAY;
            case "thu": case "thursday": return Calendar.THURSDAY;
            case "fri": case "friday": return Calendar.FRIDAY;
            case "sat": case "saturday": return Calendar.SATURDAY;

            default:return -1;

        }




    }

    private static int generateUniqueId(int dayOfWeek, int hour, int minute) {
        return dayOfWeek * 10000 + hour * 100 + minute;
    }
    private void openTimePickerDialog(EditText time) {
        // Inflate the custom layout for the TimePicker
        View customView = getLayoutInflater().inflate(R.layout.activity_main2, null);
        TimePicker timePicker = customView.findViewById(R.id.timepicker);

        // Initialize the picker with the current time
        Calendar calendar = Calendar.getInstance();
        timePicker.setHour(calendar.get(Calendar.HOUR_OF_DAY));
        timePicker.setMinute(calendar.get(Calendar.MINUTE));

        // Build and display the custom AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(customView)
                .setPositiveButton("Set", (dialog, which) -> {
                    int hour = timePicker.getHour();
                    int minute = timePicker.getMinute();
                    String formattedTime = String.format("%02d:%02d", hour, minute);
                    time.setText(formattedTime);  // Set the time in EditText

                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();

    }

    private boolean isValidTimeFormat(String time) {
        return time.matches("([01]\\d|2[0-3]):[0-5]\\d"); // HH:MM (24-hour format)
    }



}