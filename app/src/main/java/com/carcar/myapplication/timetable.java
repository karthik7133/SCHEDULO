package com.carcar.myapplication;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ScrollView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import java.util.Calendar;

public class timetable extends AppCompatActivity {

    private RecyclerView recyclerMonday,Tuesday,Wednesday,Thursday,Friday,saturday;
    private SubjectAdapter adapter;
    private List<Subject> mondaySubjects,tuesdaysubjects,wednesdaysubjects,thurdaysubjects,fridaysubjects,saturdaysubjects;
    ScheduleDao s;
    ScrollView sc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_timetable);
        Window window=getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.neon_violate));



        GradientDrawable gradientDrawable = new GradientDrawable(
                GradientDrawable.Orientation.TL_BR, // Top Left to Bottom Right
                new int[]{Color.parseColor("#8A2BE2"), Color.parseColor("#000000")} // Violet and Black

        );
        gradientDrawable.setCornerRadius(16f); // Rounded corners





        CardView mondaycard=findViewById(R.id.mondaycard);
        CardView tuesday=findViewById(R.id.tuesdaycard);
        CardView wednesday=findViewById(R.id.wenesdaycard);
        CardView Thursday1 =findViewById(R.id.thurdaycard);
        CardView Friday1 =findViewById(R.id.fridaycard);
        CardView Saturday1 =findViewById(R.id.saturdaycard);
        sc=findViewById(R.id.scrollView);
        CardView todayCard = getTodayCard();

        if (todayCard != null) {
            sc.post(() -> sc.smoothScrollTo(0, todayCard.getTop()));
        }

        mondaycard.setBackground(gradientDrawable);
        tuesday.setBackground(gradientDrawable);
        wednesday.setBackground(gradientDrawable);
        Thursday1.setBackground(gradientDrawable);
        Friday1.setBackground(gradientDrawable);
        Saturday1.setBackground(gradientDrawable);






        mondaySubjects = new ArrayList<>();
        tuesdaysubjects = new ArrayList<>();
        wednesdaysubjects = new ArrayList<>();
        thurdaysubjects = new ArrayList<>();
        fridaysubjects = new ArrayList<>();
        saturdaysubjects = new ArrayList<>();


        UserDatabase db=UserDatabase.getInstance(this);
        s=db.scheduleDao();


        recyclerMonday = findViewById(R.id.recyclerMonday);
        Tuesday=findViewById(R.id.recyclerTuesday);
        Wednesday=findViewById(R.id.recyclerWednesday);
        Thursday=findViewById(R.id.recyclerThursday);
        Friday=findViewById(R.id.recyclerFriday);
        saturday=findViewById(R.id.recyclerSaturday);



        recyclerMonday.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerMonday.addItemDecoration(new SpaceItemDecoration(16));

        Tuesday.setLayoutManager(new GridLayoutManager(this, 3));
        Tuesday.addItemDecoration(new SpaceItemDecoration(16));

        Wednesday.setLayoutManager(new GridLayoutManager(this, 3));
        Wednesday.addItemDecoration(new SpaceItemDecoration(16));

        Thursday.setLayoutManager(new GridLayoutManager(this, 3));
        Thursday.addItemDecoration(new SpaceItemDecoration(16));

        Friday.setLayoutManager(new GridLayoutManager(this, 3));
        Friday.addItemDecoration(new SpaceItemDecoration(16));

        saturday.setLayoutManager(new GridLayoutManager(this, 3));
        saturday.addItemDecoration(new SpaceItemDecoration(16));





        // Fetch Data and Populate Lists
        daycollecter(s.getClassesForDay("MONDAY"), mondaySubjects);
        daycollecter(s.getClassesForDay("TUESDAY"), tuesdaysubjects);
        daycollecter(s.getClassesForDay("WEDNESDAY"), wednesdaysubjects);
        daycollecter(s.getClassesForDay("THURSDAY"), thurdaysubjects);
        daycollecter(s.getClassesForDay("FRIDAY"), fridaysubjects);
        daycollecter(s.getClassesForDay("SATURDAY"), saturdaysubjects);




        // Set Adapters (Use separate instances)
        recyclerMonday.setAdapter(new SubjectAdapter(mondaySubjects));
        Tuesday.setAdapter(new SubjectAdapter(tuesdaysubjects));
        Wednesday.setAdapter(new SubjectAdapter(wednesdaysubjects));
        Thursday.setAdapter(new SubjectAdapter(thurdaysubjects));
        Friday.setAdapter(new SubjectAdapter(fridaysubjects));
        saturday.setAdapter(new SubjectAdapter(saturdaysubjects));
    }
    public void daycollecter(List<Daywiseschedule> D,List<Subject> L){
        for (Daywiseschedule schedule : D) {
            L.add(new Subject(schedule.getCorsecode()));
        }
    }

    private CardView getTodayCard() {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        switch (dayOfWeek) {
            case Calendar.MONDAY:
                return findViewById(R.id.mondaycard);
            case Calendar.TUESDAY:
                return findViewById(R.id.tuesdaycard);
            case Calendar.WEDNESDAY:
                return findViewById(R.id.wenesdaycard);
            case Calendar.THURSDAY:
                return findViewById(R.id.thurdaycard);
            case Calendar.FRIDAY:
                return findViewById(R.id.fridaycard);
            case Calendar.SATURDAY:
                return findViewById(R.id.saturdaycard);
            default:
                return findViewById(R.id.mondaycard); // Default to Monday if it's Sunday
        }
    }




}