package com.example.myapplication_gps_dohee;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    DBOpener dbOpener;
    SQLiteDatabase db;
    ArrayList<LatLng> dbList = new ArrayList<LatLng>();
    GPSLocationListener gps;

    int today;
    String category;
    double currentLocation[];
    String note;

    ArrayList<MyDiary> myDiaries = new ArrayList<MyDiary>();

    public void write()
    {

        MyDiary diary = new MyDiary(today, category, currentLocation, note);
        myDiaries.add(diary);
    }
    public void showDiary()
    {
        for(int i=0; i<myDiaries.size();i++)
        {
            myDiaries.get(i).getDiary();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void makeLine(){
        PolylineOptions lineOptions = new PolylineOptions();
        lineOptions.color(Color.MAGENTA);
        lineOptions.width(3);
        lineOptions.addAll(dbList); //list안의 점을 받아옴.
        mMap.addPolyline(lineOptions);
    }
    public void initialize(){
        Cursor cs = db.rawQuery("select * from Location;", null); //DB안의 모든정보에 대한 커서
        while(cs.moveToNext()){
            mMap.addCircle(new CircleOptions().center(new LatLng(cs.getDouble(0), cs.getDouble(1))).radius(3).strokeColor(Color.MAGENTA).fillColor(Color.MAGENTA)); //원그리기
            dbList.add(new LatLng(cs.getDouble(0), cs.getDouble(1))); //리스트에 커서의 데이터를 저장.
        }
        makeLine();

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if(dbOpener == null) dbOpener = new DBOpener(MapsActivity.this, "LocationDB", null,1); //데이터오프너가 없으면 버전1을 만들어준다.
        db = dbOpener.getWritableDatabase(); //데이터 베이스 만들기
        gps = new GPSLocationListener(db, mMap, dbList);
        initialize();

        if(dbList.size() != 0) mMap.moveCamera(CameraUpdateFactory.newLatLng((dbList.get(0))));
        else mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(37.559297, 126.794560))); //위치가 없으면 김포공항으로 간다!


        Toast.makeText(this, "DB에 저장된 정보의 수 : "+Integer.toString(dbList.size()),Toast.LENGTH_SHORT).show();

        final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try{
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,60000,10,gps);
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 60000,10,gps);
        }catch(SecurityException ex){}


    }
}
