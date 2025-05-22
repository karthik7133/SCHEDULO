package com.carcar.myapplication;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "question_papers")
public class QuestionPaper {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String subject;
    private String year;
    private String date;  // Example: "2023-06-15"
    private String imageName;  // Example: "maths_2023.png"

    public QuestionPaper(String subject, String year, String date, String imageName) {
        this.subject = subject;
        this.year = year;
        this.date = date;
        this.imageName = imageName;
    }

    public int getId() { return id; }
    public String getSubject() { return subject; }
    public String getYear() { return year; }
    public String getDate() { return date; }
    public String getImageName() { return imageName; }

    public void setId(int id) { this.id = id; }
}