package com.carcar.myapplication;

public class gpamodel {
    private int Semester;
    private float gpa;
    private int credits;
    private int requestCode;

    // Constructor (Remove void return type)
    public gpamodel(int sem, float gpa, int credits, int reqcode) {
        this.Semester = sem;
        this.gpa = gpa;
        this.credits = credits;
        this.requestCode = reqcode;
    }

    // Getters
    public int getSemester() {
        return Semester;
    }

    public float getGpa() {
        return gpa;
    }

    public int getCredits() {
        return credits;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setSemester(int semester) {
        semester=semester;
    }

    public void setGpa(float gpa) {
        gpa=gpa;
    }

    public void setCredits(int credits) {
        credits=credits;
    }
}
