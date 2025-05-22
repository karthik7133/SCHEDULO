package com.carcar.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class aftersplash extends AppCompatActivity {

    private static final String PREFS_NAME = "MyAppPrefs";
    private static final String PREF_DONT_SHOW_AGAIN = "dontShowAgain";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aftersplash);

        Window window=getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.neon_violate));

        CheckBox dontShowAgain = findViewById(R.id.dont);
        Button nextButton = findViewById(R.id.nextpage);

        nextButton.setOnClickListener(view -> {
            // Save the user's preference
            SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(PREF_DONT_SHOW_AGAIN, dontShowAgain.isChecked());
            editor.apply();

            // Go to the intro activity
            startActivity(new Intent(this, loginpage.class));
            finish();
        });
    }
}