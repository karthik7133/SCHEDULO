package com.carcar.myapplication;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class fullmarksview extends AppCompatActivity {
    private RecyclerView recyclerView;
    private fullmarksAdapter fullmarksAdapter;
    private ArrayList<Marksmodel> marksarray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_fullmarksview);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.neon_violate));

        TextView schedulo = findViewById(R.id.schedulo);
        Shader gradient = new LinearGradient(
                0, 0, 0, schedulo.getTextSize(),
                new int[]{Color.parseColor("#C0C0C0"), Color.parseColor("#4B0082")},
                null, Shader.TileMode.CLAMP
        );

        schedulo.getPaint().setShader(gradient);
        schedulo.invalidate(); // Apply gradient to text

        recyclerView = findViewById(R.id.Fullmarksview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadData(); // Load the data before setting the adapter

        fullmarksAdapter = new fullmarksAdapter(marksarray);
        recyclerView.setAdapter(fullmarksAdapter);
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyMarkspreferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("MarksList", null);
        Type type = new TypeToken<ArrayList<Marksmodel>>() {}.getType();
        marksarray = gson.fromJson(json, type);

        if (marksarray == null) {
            marksarray = new ArrayList<>();
        }

        Log.d("fullmarksview", "Loaded Data: " + marksarray.size() + " items");
    }
}
