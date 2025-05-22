package com.carcar.myapplication;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Daywiseschedule")
public class Daywiseschedule {

    @PrimaryKey(autoGenerate = true) // You can add a primary key with auto-generation if you want
    private int id; // Primary key

    private String day;
    private String corsecode;

    // Constructor with all fields
    public Daywiseschedule(String day, String corsecode) {
        this.day = day;
        this.corsecode = corsecode;
    }

    // Getter and setter for ID (if using auto-generate)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter and setter for Day
    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    // Getter and setter for Course code
    public String getCorsecode() {
        return corsecode;
    }

    public void setCorsecode(String corsecode) {
        this.corsecode = corsecode;
    }
}
