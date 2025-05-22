package com.carcar.myapplication;

import android.content.Intent;
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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import javax.security.auth.Subject;

public class marksactivity extends AppCompatActivity {

    private ArrayList<Marksmodel> marksarray = new ArrayList<>();
    private Adapter3 adapter2;

    RecyclerView r;
    TextView tv;
    EditText subject ,marks,Grade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_marksactivity);
        Window window=getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.neon_violate));

        Intent fromafterintro =getIntent();
        tv=findViewById(R.id.marks);
        r=findViewById(R.id.marksrecview);

        Shader gradient = new LinearGradient(
                0, 0, 0, tv.getTextSize(),
                new int[]{Color.parseColor("#C0C0C0"), Color.parseColor("#4B0082")},
                null, Shader.TileMode.CLAMP
        );

        tv.getPaint().setShader(gradient);
        tv.invalidate();  // Refresh the view to apply the gradient





        subject =findViewById(R.id.subname);
        marks =findViewById(R.id.Marks);
        Grade=findViewById(R.id.Grade);
        loadData();


        r.setLayoutManager(new LinearLayoutManager(this));
        adapter2=new Adapter3(this,marksarray);
        r.setAdapter(adapter2);

        Button btn=findViewById(R.id.addbtn);
        btn.setOnClickListener(View -> {
            String subjectdata = subject.getText().toString().trim().toUpperCase();
            String marksdata = marks.getText().toString().trim();
            String Gradedata = Grade.getText().toString().trim().toUpperCase();

            // Check if any field is empty
            if (subjectdata.isEmpty() || marksdata.isEmpty() || Gradedata.isEmpty()) {
                Toast.makeText(this, "Please fill all details", Toast.LENGTH_SHORT).show();
            } else {
                // Check if marksdata is a valid integer
                if (isInteger(marksdata) && isGrade(Gradedata) && Integer.parseInt(marksdata)<=100 ) {
                    int reqcode = Integer.parseInt(marksdata); // Parse marksdata to integer
                    marksarray.add(new Marksmodel(subjectdata, Gradedata, marksdata, reqcode));
                    adapter2.notifyItemInserted(marksarray.size() - 1);
                    adapter2.notifyDataSetChanged();
                    saveData();

                    // Clear the input fields
                    subject.setText("");
                    marks.setText("");
                    Grade.setText("");
                } else {
                    Toast.makeText(this, "Enter correct things not other bullshit you  know", Toast.LENGTH_SHORT).show();
                    Toast.makeText(this, "Please enter marks as an integer and check Grade entry", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Intent tofullmarksview=new Intent(this, fullmarksview.class);
        Button b2=findViewById(R.id.Nextpage);
        b2.setOnClickListener(View->{
            startActivity(tofullmarksview);
        });





    }
    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyMarkspreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(marksarray);
        editor.putString("MarksList", json);
        editor.apply();
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
        adapter2 = new Adapter3(this,marksarray);
        r.setAdapter(adapter2);
    }
    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public boolean isGrade(String c){
        String carray []={"S","A","B","C","D","E","F"};
        boolean found = false;
        String  target=c;
        for (String d : carray) {
            if (d.equalsIgnoreCase(target)) {
                found = true;
                break;
            }
        }

        return found;
    }


}