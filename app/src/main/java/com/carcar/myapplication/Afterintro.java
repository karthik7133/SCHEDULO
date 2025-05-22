package com.carcar.myapplication;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.GradientDrawable;
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
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Afterintro extends AppCompatActivity {

    TextView schedulo;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_afterintro);
        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.neon_violate));

        Intent fromintro=getIntent();
        Intent Tomain =new Intent(this,MainActivity.class);

        try {
            CardView cardView=findViewById(R.id.Scheduler);
            GradientDrawable gradientDrawable = new GradientDrawable(
                    GradientDrawable.Orientation.BL_TR,
                    new int[]{ Color.parseColor("#00008B"), Color.parseColor("#000000") }
                    // At least two colors
            );

            gradientDrawable.setCornerRadius(24f);
            cardView.setBackground(gradientDrawable);



        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        TextView textView=findViewById(R.id.Actions);
        TextView tv1=findViewById(R.id.tv1);
        TextView tv2=findViewById(R.id.tv2);
        TextView tv3=findViewById(R.id.tv3);
        TextView tv4=findViewById(R.id.tv4);
        TextView tv5=findViewById(R.id.tv5);
        TextView tv6=findViewById(R.id.tv6);
        TextView tv7=findViewById(R.id.tv7);
        Shader textShader = new LinearGradient(
                0, 0, 0, textView.getTextSize(),
                new int[]{Color.rgb(255, 255, 255),Color.rgb(192, 192, 192),Color.rgb(250, 250, 240)},
                null, Shader.TileMode.CLAMP);

        Shader textShader2 = new LinearGradient(
                0, 0, 0, textView.getTextSize(),
                new int[]{Color.rgb(255, 192, 203),Color.rgb(192, 192, 192) },
                null, Shader.TileMode.CLAMP);
        textView.getPaint().setShader(textShader);
        tv1.getPaint().setShader(textShader2);
        tv2.getPaint().setShader(textShader2);
        tv3.getPaint().setShader(textShader2);
        tv4.getPaint().setShader(textShader2);
        tv5.getPaint().setShader(textShader2);
        tv6.getPaint().setShader(textShader2);
        tv7.getPaint().setShader(textShader2);

        schedulo = findViewById(R.id.schedulo);


        Shader gradient = new LinearGradient(
                0, 0, 0, schedulo.getTextSize(),
                new int[]{Color.parseColor("#C0C0C0"), Color.parseColor("#4B0082")},
                null, Shader.TileMode.CLAMP
        );

        schedulo.getPaint().setShader(gradient);
        schedulo.invalidate();  // Refresh the view to apply the gradient






        CardView schedulercard=findViewById(R.id.Schulercard);



        CardView Markscard=findViewById(R.id.markscard);

        schedulercard.setOnClickListener(view ->{

            startActivity(Tomain);
        });

        CardView marksview=findViewById(R.id.markscard);
        Intent tomarks =new Intent(this, marksactivity.class);
        marksview.setOnClickListener(View->{
            startActivity(tomarks);

        });
        Intent tocgpa=new Intent(this,gpacalc.class);
        CardView gpa=findViewById(R.id.cgpacard);
        gpa.setOnClickListener(View->{
            startActivity(tocgpa);
        });
        Intent togpa =new Intent(this,semgpa.class);
        CardView gpacalc=findViewById(R.id.gpacard);
        gpacalc.setOnClickListener(View->{
            startActivity(togpa);
        });




        Intent touserdetails = new Intent(Afterintro.this, userdetails.class);

// Check if views exist


        CardView userdatacard = findViewById(R.id.userdatacard);
        userdatacard.setOnClickListener(v -> startActivity(touserdetails));


        CardView PYQDATACARD=findViewById(R.id.PYQDATACARD);
        PYQDATACARD.setOnClickListener(View->{
            Intent topapers=new Intent(this, pyqactivity.class);
            startActivity(topapers);
        });

        CardView timetable =findViewById(R.id.timetabledatacard);
        timetable.setOnClickListener(View->{
            Intent totimetable =new Intent(this,daysubinput.class);
            startActivity(totimetable);
        });



    }
}