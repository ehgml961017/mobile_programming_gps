package com.example.myapplication_gps_dohee;

/**
 * Created by 박도희 on 2016-11-16.
 */

public class MyDiary {

    int today;  //오늘 날짜
    String category;    //분류
    double currentLocation[];   //현재위치
    String note;    //메모

    public MyDiary() {}
    public MyDiary(int today, String category, double currentLocation[], String note)
    {
        today = today;
        category = category;
        currentLocation[0] = currentLocation[0];
        currentLocation[1] = currentLocation[1];
        note = note;
    }

    public void setCurrentLocation(double x, double y)
    {
        currentLocation[0] = x;
        currentLocation[1] = y;
    }
    public void setCurrentLocation(double currentLocation[])
    {
        currentLocation[0] = currentLocation[0];
        currentLocation[1] = currentLocation[1];
    }

    public int getToday()
    {
        return today;
    }
    public String getCategory()
    {
        return category;
    }
    public String getCurrentLocation()
    {
        String cl1 = Double.toString(currentLocation[0]);
        String cl2 = Double.toString(currentLocation[1]);

        return cl1+"/"+cl2;
    }
    public String getNote()
    {
        return note;
    }

    public String getDiary()
    {
        String strToday = Integer.toString(today);
        return strToday+","+category+","+getCurrentLocation()+","+note ;
    }

}
