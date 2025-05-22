package com.carcar.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class userdetails extends AppCompatActivity {
    ArrayList<LoginStruct> arrayList,l2;
    FloatingActionButton fbt;

    LoginDao l;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_userdetails);
        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.neon_violate));



        fbt=findViewById(R.id.cleardatabase);

        RecyclerView r=findViewById(R.id.rv2);
        UserDatabase db=UserDatabase.getInstance(this);
        l=db.loginDao();


        arrayList=new ArrayList<>(l.getAll());

        rv2adapter rv2=new rv2adapter(this,arrayList);
        r.setLayoutManager(new LinearLayoutManager(this));
        r.setAdapter(rv2);


        fbt.setOnClickListener(View->{
            l.deleteall(); // Delete all data
            arrayList = new ArrayList<>(l.getAll()); // Fetch updated list
            rv2adapter newAdapter = new rv2adapter(this, arrayList); // Create new adapter
            r.setAdapter(newAdapter); // Set new adapter to RecyclerView
        });



    }
}