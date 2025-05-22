package com.carcar.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class gpacalc extends AppCompatActivity {
    TextView tv,cgpaTextView,title;
    EditText sem, gpa, credits;
    Button btn,calculateBtn;
    FloatingActionButton fbtn;
    private RecyclerView recyclerView;
    private Adapter4 adapter;
    private List<gpamodel> gpaList;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gpacalc);
        Window window=getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this ,R.color.neon_violate));

        // Initialize EditText fields
        sem = findViewById(R.id.sem);
        gpa = findViewById(R.id.GPA);
        credits = findViewById(R.id.credits);

        // Initialize Buttons and TextView
        btn = findViewById(R.id.addbtn);
        cgpaTextView = findViewById(R.id.CGPA);
        fbtn=findViewById(R.id.fab);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.gparecview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        title=findViewById(R.id.cgpatext);
        gradforheadings(title);


        // Initialize SharedPreferences and Adapter
        gpaList = new ArrayList<>();
        sharedPreferences = getSharedPreferences("GPA_DATA", Context.MODE_PRIVATE);
        adapter = new Adapter4(gpaList, this, sharedPreferences, this);
        recyclerView.setAdapter(adapter);

        // Load Saved Data
        loadData();

        // Add Data Button Click
        btn.setOnClickListener(v -> addData());

        // Calculate CGPA Button Click
        fbtn.setOnClickListener(v -> {
            float cgpa = calculateCGPA();
            cgpaTextView.setText(String.format("CGPA: %.2f", cgpa));
        });
        FloatingActionButton fb2=findViewById(R.id.fab2);
        fb2.setOnClickListener(View->{clearData();cgpaTextView.setText("CGPA");});
    }


    private void addData() {
        String semesterStr = sem.getText().toString().trim();
        String gpaStr = gpa.getText().toString().trim();
        String creditsStr = credits.getText().toString().trim();

        if(!isInteger(semesterStr) || ((!isFloat(gpaStr)) &&(!isInteger(gpaStr))) || !isInteger(creditsStr)){
            Toast.makeText(this, "Enter correct format's of input", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!semesterStr.isEmpty() && !gpaStr.isEmpty() && !creditsStr.isEmpty()) {
            try {
                int semester = Integer.parseInt(semesterStr);
                float gpaValue = Float.parseFloat(gpaStr);
                int creditsValue = Integer.parseInt(creditsStr);

                // Create new GPA object and add it to the list
                gpamodel gpaItem = new gpamodel(semester, gpaValue, creditsValue, semester);
                gpaList.add(gpaItem);

                // Update RecyclerView
                adapter.notifyDataSetChanged();

                // Save data
                saveData();

                // Clear input fields
                sem.setText("");
                gpa.setText("");
                credits.setText("");

            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid input! Please enter valid numbers.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        }
    }

    void saveData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> gpaSet = new HashSet<>();

        for (gpamodel gpaItem : gpaList) {
            gpaSet.add(gpaItem.getSemester() + "," + gpaItem.getGpa() + "," + gpaItem.getCredits());
        }

        editor.putStringSet("gpa_list", gpaSet);
        editor.apply();
    }
    void clearData() {
        // Clear SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("gpa_list"); // Remove the saved GPA list
        editor.apply();

        // Clear the list and update RecyclerView
        gpaList.clear();
        adapter.notifyDataSetChanged();
    }


    private void loadData() {
        Set<String> gpaSet = sharedPreferences.getStringSet("gpa_list", new HashSet<>());

        if (gpaList == null) {
            gpaList = new ArrayList<>();
        } else {
            gpaList.clear(); // Clear the list before loading new data
        }

        for (String entry : gpaSet) {
            String[] parts = entry.split(",");
            if (parts.length == 3) {
                int semester = Integer.parseInt(parts[0]);
                float gpa = Float.parseFloat(parts[1]);
                int credits = Integer.parseInt(parts[2]);

                gpaList.add(new gpamodel(semester, gpa, credits, semester));
            }
        }

        adapter.notifyDataSetChanged();
    }
    private float calculateCGPA() {
        float totalGpaCredits = 0;
        int totalCredits = 0;

        for (gpamodel gpaItem : gpaList) {
            totalGpaCredits += gpaItem.getGpa() * gpaItem.getCredits();
            totalCredits += gpaItem.getCredits();
        }

        if (totalCredits == 0) return 0; // Prevent division by zero
        return totalGpaCredits / totalCredits;
    }
    public void gradforheadings(TextView t){
        Shader gradient = new LinearGradient(
                0, 0, 0, t.getTextSize(),
                new int[]{Color.parseColor("#C0C0C0"), Color.parseColor("#4B0082")},
                null, Shader.TileMode.CLAMP
        );

        t.getPaint().setShader(gradient);
        t.invalidate();  // Refresh the view to apply the gradient
    }
    public static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true; // It's an integer
        } catch (NumberFormatException e) {
            return false; // Not an integer
        }
    }
    public static boolean isFloat(String input) {
        try {
            Float.parseFloat(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


}
