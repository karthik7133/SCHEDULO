package com.carcar.myapplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.media.AudioAttributes;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity3 extends AppCompatActivity {

    private ArrayList<TableModel> arraytable = new ArrayList<>();
    private ActivityResultLauncher<Intent> ringtonePickerLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main3);





        Button b=findViewById(R.id.Notification3);
        TextView schedulo=findViewById(R.id.schedulo3);
        Shader gradient = new LinearGradient(
                0, 0, 0, schedulo.getTextSize(),
                new int[]{Color.parseColor("#C0C0C0"), Color.parseColor("#4B0082")},
                null, Shader.TileMode.CLAMP
        );




        schedulo.getPaint().setShader(gradient);
        schedulo.invalidate();  // Refresh the view to apply the gradient








    }




}
