package com.carcar.myapplication;

import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.biometric.BiometricManager;


import java.util.List;
import java.util.concurrent.Executor;

public class loginpage extends AppCompatActivity {


    TextView uname,pass;
    Button btn,signup;
    ImageView i;
    LoginDao l;
    ScheduleDao s;
    CheckBox showbutton;
    ImageView biometric;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_loginpage);
        Window window=getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.black));

        if (isLoginValid()) {
            Intent intent = new Intent(loginpage.this, intoactivity.class);
            startActivity(intent);
            finish(); // Skip login if authenticated within 10 hours
            return;
        }




        uname=findViewById(R.id.Uname);
        pass=findViewById(R.id.Passwrod);
        biometric=findViewById(R.id.biometric);

        biometric.setOnClickListener(View->{
            checkBiometricSupport();
            showBiometricPrompt();
        });


        btn=findViewById(R.id.btn);
        signup=findViewById(R.id.btnSignUp);

        showbutton=findViewById(R.id.checkbox);

        showbutton.setOnCheckedChangeListener((compoundButton, b) -> {
            if(b){
                pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }else {
                pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });



        UserDatabase db = UserDatabase.getInstance(this);
        l = db.loginDao();
        if(l.validateuser("23BCE20022","BCE2320022")==0){
            l.insert(new LoginStruct("23BCE20022","BCE2320022"));
        }

        insertuser("23BCE20131","BCE2320131");
        insertuser("23BCE20104","BCE2320104");
        insertuser("23BCE20164","BCE2320164");


        btn.setOnClickListener(View -> {
            String name = uname.getText().toString().trim();
            String password = pass.getText().toString().trim();

            if (name.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }
            if (l.validateuser(name, password) > 0) {
                Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show();

                saveLoginTime(); // Save login time

                Intent tointo = new Intent(this, intoactivity.class);
                startActivity(tointo);
                finishAffinity(); // Close all previous activities

            } else {
                Toast.makeText(this, "Invalid Credentials!", Toast.LENGTH_SHORT).show();
            }
        });






        Intent tosignup=new Intent(this, signupactivity.class);
        signup.setOnClickListener(View->{
            startActivity(tosignup);
        });

    }

    public void insertuser (String rig,String pass){
        if(l.validateuser(rig,pass)==0){
            l.insert(new LoginStruct(rig,pass));
        }

    }


    private void checkBiometricSupport() {
        BiometricManager biometricManager = BiometricManager.from(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            switch (biometricManager.canAuthenticate()) {
                case BiometricManager.BIOMETRIC_SUCCESS:
                    // Device supports biometric authentication
                    break;
                case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                    Toast.makeText(this, "No biometric hardware detected", Toast.LENGTH_SHORT).show();
                    break;
                case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                    Toast.makeText(this, "Biometric hardware is currently unavailable", Toast.LENGTH_SHORT).show();
                    break;
                case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                    Toast.makeText(this, "No biometric data enrolled. Please set up biometrics.", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }

    private void showBiometricPrompt() {
        Executor executor = ContextCompat.getMainExecutor(this);
        BiometricPrompt biometricPrompt = new BiometricPrompt(this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(), "Authentication Error: " + errString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
               // Toast.makeText(getApplicationContext(), "Authentication Successful!", Toast.LENGTH_SHORT).show();
                // Proceed to the home screen or next activity
                saveLoginTime(); // Save login time

                // Redirect to next activity after successful biometric login
                Intent intent = new Intent(loginpage.this, intoactivity.class); // Replace 'intoactivity' with your target activity
                startActivity(intent);
                finishAffinity(); // This will close all previous activities
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "Authentication Failed", Toast.LENGTH_SHORT).show();
            }
        });

        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("SCHEDULO")
                .setSubtitle("login using your biometric credential")
                .setNegativeButtonText("Use account password")
                .build();

        biometricPrompt.authenticate(promptInfo);
    }

    private void saveLoginTime() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("LastLoginTime", System.currentTimeMillis()); // Save current time in milliseconds
        editor.apply();
    }
    private boolean isLoginValid() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        long lastLoginTime = sharedPreferences.getLong("LastLoginTime", 0);

        long currentTime = System.currentTimeMillis();
        long tenHoursInMillis = 10 * 60 * 60 * 1000; // 10 hours in milliseconds

        return (currentTime - lastLoginTime) < tenHoursInMillis; // True if within 10 hours
    }



}
