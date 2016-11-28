package com.example.myapplication_gps_dohee;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

/**
 * Created by 박도희 on 2016-10-31.
 */

public class GPSLocationListener implements android.location.LocationListener{
    SQLiteDatabase db;
    GoogleMap ggmap;
    ArrayList<LatLng> dbList;
    PolylineOptions lineOptions;
    @Override
    public void onLocationChanged(Location location) {
        String msg = "INSERT INTO Location (X,Y) VALUES(?,?);";
        double x = location.getLatitude(); //위도
        double y = location.getLongitude(); //경도
        db.execSQL(msg, new Object[]{x,y}); //?의 위치에 x와 y를 넣어서 db데이터를 추가
        dbList.add(new LatLng(x,y)); //db리스트에 x,y의 위치를 가지는 한 점을 저장
        makeLine(); //선긋기
        ggmap.addCircle(new CircleOptions().center(new LatLng(x, y)).radius(3).strokeColor(Color.DKGRAY).fillColor(Color.GRAY)); //내 위치를 동그라미로 표시하기.

    }
    GPSLocationListener(SQLiteDatabase db, GoogleMap ggmap, ArrayList<LatLng> dbList)
    {
        this.db = db;
        this.ggmap = ggmap;
        this.dbList = dbList;
    }

    public void makeLine(){
        lineOptions = new PolylineOptions();
        lineOptions.color(Color.MAGENTA);
        lineOptions.width(3);
        lineOptions.addAll(dbList); //list안의 점을 받아옴.
        ggmap.addPolyline(lineOptions);
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        //쓰지않습니다.
    }

    @Override
    public void onProviderEnabled(String provider) {
        //쓰지않습니다.
    }

    @Override
    public void onProviderDisabled(String provider) {

    }


}
