package com.carcar.myapplication;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class semgpa extends AppCompatActivity {
    Spinner c1, g1, g2, c2, c3, g3, c4, g4, c5, g5, c6, g6, c7, g7, c8, g8, c9, g9, c10, g10;
    Button calc;
    TextView gpa ,credit,grad,title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_semgpa);

        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.neon_violate));

        // Initializing Spinners
        c1 = findViewById(R.id.r1c);
        c2 = findViewById(R.id.r2c);
        c3 = findViewById(R.id.r3c);
        c4 = findViewById(R.id.r4c);
        c5 = findViewById(R.id.r5c);
        c6 = findViewById(R.id.r6c);
        c7 = findViewById(R.id.r7c);
        c8 = findViewById(R.id.r8c);
        c9 = findViewById(R.id.r9c);
        c10 = findViewById(R.id.r10c);

        g1 = findViewById(R.id.r1g);
        g2 = findViewById(R.id.r2g);
        g3 = findViewById(R.id.r3g);
        g4 = findViewById(R.id.r4g);
        g5 = findViewById(R.id.r5g);
        g6 = findViewById(R.id.r6g);
        g7 = findViewById(R.id.r7g);
        g8 = findViewById(R.id.r8g);
        g9 = findViewById(R.id.r9g);
        g10 = findViewById(R.id.r10g);

        gpa = findViewById(R.id.result);
        calc = findViewById(R.id.calculate);
        credit=findViewById(R.id.b1a3);
        grad=findViewById(R.id.bad3);
        title=findViewById(R.id.Title);
        textviewshader(credit);
        textviewshader(grad);gradforheadings(title);



        // Setting up Credits Spinner with a default hint
        List<String> creditList = new ArrayList<>();
        creditList.add("Select Credits"); // Default Hint
        creditList.add("1");
        creditList.add("2");
        creditList.add("3");
        creditList.add("4");
        creditList.add("6");
        creditList.add("12");

        ArrayAdapter<String> creditAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, creditList);
        creditAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        c1.setAdapter(creditAdapter);
        c2.setAdapter(creditAdapter);
        c3.setAdapter(creditAdapter);
        c4.setAdapter(creditAdapter);
        c5.setAdapter(creditAdapter);
        c6.setAdapter(creditAdapter);
        c7.setAdapter(creditAdapter);
        c8.setAdapter(creditAdapter);
        c9.setAdapter(creditAdapter);
        c10.setAdapter(creditAdapter);

        // Setting up Grades Spinner with a default hint
        List<String> gradeList = new ArrayList<>();
        gradeList.add("Select Grade"); // Default Hint
        gradeList.add("S");
        gradeList.add("A");
        gradeList.add("B");
        gradeList.add("C");
        gradeList.add("D");
        gradeList.add("E");
        gradeList.add("F");

        ArrayAdapter<String> gradeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, gradeList);
        gradeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        g1.setAdapter(gradeAdapter);
        g2.setAdapter(gradeAdapter);
        g3.setAdapter(gradeAdapter);
        g4.setAdapter(gradeAdapter);
        g5.setAdapter(gradeAdapter);
        g6.setAdapter(gradeAdapter);
        g7.setAdapter(gradeAdapter);
        g8.setAdapter(gradeAdapter);
        g9.setAdapter(gradeAdapter);
        g10.setAdapter(gradeAdapter);

        // GPA Calculation on Button Click
        calc.setOnClickListener(view -> {calculateGPA();});

        //clear data
        Button c=findViewById(R.id.clear);
        c.setOnClickListener(View->clear());
    }

    private void calculateGPA() {
        // Mapping grades to numerical values
        HashMap<String, Integer> gradeToPoints = new HashMap<>();
        gradeToPoints.put("S", 10);
        gradeToPoints.put("A", 9);
        gradeToPoints.put("B", 8);
        gradeToPoints.put("C", 7);
        gradeToPoints.put("D", 6);
        gradeToPoints.put("E", 5);
        gradeToPoints.put("F", 0);

        // Arrays for Spinners
        Spinner[] creditSpinners = {c1, c2, c3, c4, c5, c6, c7, c8, c9, c10};
        Spinner[] gradeSpinners = {g1, g2, g3, g4, g5, g6, g7, g8, g9, g10};

        double totalWeightedPoints = 0;
        int totalCredits = 0;

        for (int i = 0; i < creditSpinners.length; i++) {
            // Skip if "Select Credits" or "Select Grade" is chosen
            if (creditSpinners[i].getSelectedItem() == null || gradeSpinners[i].getSelectedItem() == null) {
                continue;
            }

            String selectedCredit = creditSpinners[i].getSelectedItem().toString().trim();
            String selectedGrade = gradeSpinners[i].getSelectedItem().toString().trim();

            if (selectedCredit.equals("Select Credits") || selectedGrade.equals("Select Grade")) {
                continue; // Ignore default values
            }

            int credits = Integer.parseInt(selectedCredit);
            int gradeValue = gradeToPoints.getOrDefault(selectedGrade, 0);

            totalWeightedPoints += credits * gradeValue;
            totalCredits += credits;
        }

        // Calculate GPA
        double gpaValue = (totalCredits > 0) ? (totalWeightedPoints / totalCredits) : 0.0;

        // Display result
        gpa.setText(String.format("GPA: %.2f", gpaValue));
    }

    public void clear(){
        Spinner[] creditSpinners = {c1, c2, c3, c4, c5, c6, c7, c8, c9, c10};
        Spinner[] gradeSpinners = {g1, g2, g3, g4, g5, g6, g7, g8, g9, g10};
        for (int i = 0; i < creditSpinners.length; i++){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                creditSpinners[i].setSelection(0);
                gradeSpinners[i].setSelection(0);
            }

        }
    }
    public void textviewshader(TextView t){
        Shader textShader = new LinearGradient(
                0, 0, 0, t.getTextSize(),
                new int[]{Color.rgb(255, 255, 255), Color.rgb(128, 0, 128)},
                null, Shader.TileMode.CLAMP);
        t.getPaint().setShader(textShader);
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
}
