package com.carcar.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
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
import java.util.List;

public class fullview extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Adapter2 adapter2;
    FloatingActionButton fbt2;
    private ArrayList<TableModel> arraytable = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_fullview);
        Window window=getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.neon_violate));




        loadData();
        recyclerView=findViewById(R.id.Fullview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        SharedPreferences sharedPreferences = getSharedPreferences("MySchedulePrefs", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("scheduleList", null);
        Type type = new TypeToken<ArrayList<TableModel>>() {}.getType();
        arraytable = gson.fromJson(json, type);

        if (arraytable == null) {
            arraytable = new ArrayList<>();
        }
        Log.d("SecondActivity", "Retrieved Data: " + sharedPreferences.getString("scheduleList", "null"));
        adapter2 = new Adapter2(arraytable);
        recyclerView.setAdapter(adapter2);


    }
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
}