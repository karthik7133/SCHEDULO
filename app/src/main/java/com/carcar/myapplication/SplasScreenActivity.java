package com.carcar.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Window;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import pl.droidsonroids.gif.GifDrawable;

public class SplasScreenActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "MyAppPrefs";
    private static final String PREF_DONT_SHOW_AGAIN = "dontShowAgain";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splas_screen);

        Window window=getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.neon_violate));

        //SET DEFAULT USERS...
        insertDefaultUsersIfNeeded();

        ImageView imageView = findViewById(R.id.cardimage);

        // Load GIF safely
        try {
            GifDrawable gifDrawable = new GifDrawable(getResources(), R.drawable.icon);
            gifDrawable.setSpeed(0.5f);
            imageView.setImageDrawable(gifDrawable);
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            boolean dontShowAgain = preferences.getBoolean(PREF_DONT_SHOW_AGAIN, false);

            Intent nextIntent = dontShowAgain ?
                    new Intent(SplasScreenActivity.this, loginpage.class) :
                    new Intent(SplasScreenActivity.this, aftersplash.class);

            startActivity(nextIntent);
            finish();
        }, 1500);
    }
    private void insertDefaultUsersIfNeeded() {
        SharedPreferences prefs = getSharedPreferences("MYAPKTEST", Context.MODE_PRIVATE);
        boolean isFirstRun = prefs.getBoolean("isFirstRun", true);

        if (isFirstRun) {
            UserDatabase db = UserDatabase.getInstance(this);
            LoginDao loginDao = db.loginDao();

            // Example default users
            LoginStruct user1 = new LoginStruct("23BCE20022", "BCE2320022");
            LoginStruct user2 = new LoginStruct("23BCE20131", "BCE2320131");

            loginDao.insert(user1);
            loginDao.insert(user2);

            // Mark that the app has inserted the data
            prefs.edit().putBoolean("isFirstRun", false).apply();
        }
    }
}
