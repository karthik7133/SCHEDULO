package com.carcar.myapplication;

public class Marksmodel {
    public String subject;
    public  String Grade;
    public String marks;
    public int reqcode;
    public Marksmodel(String subject,String Grade,String marks,int reqcode){
        this.subject=subject;
        this.Grade=Grade;
        this.marks=marks;
        this.reqcode=reqcode;
    }
    public String getSubject(){
        return subject;
    }
    public String getGrade(){
        return Grade;
    }
    public String getMarks(){
        return marks;
    }
    public int getReqcode(){
        return reqcode;
    }

}
