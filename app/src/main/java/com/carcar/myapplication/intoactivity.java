package com.carcar.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class intoactivity extends AppCompatActivity {

    private static final String PREFS_NAME = "MyAppPrefs2";
    private static final String KEY_USERNAME = "username";

    TextView tv,tv2;
    ImageView insta,mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introactivity);

        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.neon_violate));

        // Initialize TextView
        tv = findViewById(R.id.one);
        tv2 = findViewById(R.id.two);  // Name TextView
        mail=findViewById(R.id.mail);
        insta=findViewById(R.id.insta);

        mail.setOnClickListener(View->openEmail());
        insta.setOnClickListener(View->openInstagram());

        // Retrieve stored username from SharedPreferences
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String username = preferences.getString(KEY_USERNAME, null);

        // If no name exists, prompt user
        if (username == null) {
            showNameInputDialog();
        } else {
            // Set the retrieved name to the TextView
            tv2.setText(username.toLowerCase());
            Toast.makeText(this, "Welcome back, " + username + "!", Toast.LENGTH_LONG).show();
        }

        tv2.setOnLongClickListener(View -> {
            showNameInputDialog();
            return false;
        });

        Intent TOAfterintro = new Intent(this, Afterintro.class);
        Button next = findViewById(R.id.getStartedButton);
        next.setOnClickListener(view -> {
            startActivity(TOAfterintro);
            finish();
        });
    }


    private void showNameInputDialog() {
        // Inflate the custom layout
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.usernamedialogue, null);
        builder.setView(view);

        // Initialize UI elements from custom layout
        EditText input = view.findViewById(R.id.username);
        Button saveButton = view.findViewById(R.id.verify);

        AlertDialog dialog = builder.create();
        dialog.show();  // Show the dialog

        // Handle Save Button Click
        saveButton.setOnClickListener(v -> {
            String name = convertExceptFirst(input.getText().toString().trim());
            if (!name.isEmpty()) {
                saveUsername(name);  // Save to SharedPreferences
                tv2.setText(name);   // Update the TextView
                Toast.makeText(intoactivity.this, "Welcome, " + name + "!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();  // Close the dialog
            } else {
                Toast.makeText(intoactivity.this, "Name cannot be empty!", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.setCancelable(false); // Prevent dismissing without input
    }



    private void saveUsername(String username) {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_USERNAME, username);
        editor.apply();
    }

    public static String convertExceptFirst(String str) {
        if (str == null || str.isEmpty()) {
            return str; // Return as is if empty or null
        }
        return Character.toUpperCase(str.charAt(0))+ str.substring(1).toLowerCase();
    }

    private void openEmail() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:chkarthik853@gmail.com")); // Replace with your email
        intent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here"); // Optional
        intent.putExtra(Intent.EXTRA_TEXT, "Hello,"); // Optional
        startActivity(Intent.createChooser(intent, "Send Email"));
    }

    private void openInstagram() {
        Uri uri = Uri.parse("https://www.instagram.com/karthi.chh/"); // Replace with your Instagram username
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage("com.instagram.android"); // Opens Instagram app if installed

        try {
            startActivity(intent);
        } catch (Exception e) {
            // If Instagram is not installed, open in browser
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/karthi.chh/")));
        }
    }


}