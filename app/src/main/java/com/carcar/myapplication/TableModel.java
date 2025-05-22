package com.carcar.myapplication;

public class TableModel {
    public String Day;
    public static int requestCode;
    public String Subject;
    public String starttime;
    public String place;
    public String endtime;
    public TableModel(String day,String subject,String starttime,String endtime,int requestCode,String place){
        this.Day=day;
        this.Subject=subject;
        this.starttime=starttime;
        this.endtime=endtime;
        this.requestCode=requestCode;
        this.place=place;
    }

    public String getSubject() {
        return Subject;
    }


    public String getStartTime() {
        return starttime;
    }

    public String getEndTime() {
        return endtime;
    }
    public String getPlace(){
        return place;
    }


    public String getDay() {
        return Day;
    }
    public  static  int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public void setDay(String newday) {
        this.Day=newday;
    }
}
