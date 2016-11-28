package com.example.myapplication_gps_dohee;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by 박도희 on 2016-10-31.
 */

public class DBOpener extends SQLiteOpenHelper {
    Context context;

    public DBOpener(Context context, String name, SQLiteDatabase.CursorFactory csfactory, int version)
    {
        super(context, name, csfactory, version);
        this.context = context;
    }
    public void onCreate(SQLiteDatabase db) {
        String msg = "Create Table Location(X double, Y double);";
        db.execSQL(msg);
        Toast.makeText(context, "DB를 만들었습니다.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String msg = "Create Table Location(X double, Y double);";
        db.execSQL(msg);
        Toast.makeText(context, "DB를 업데이트 하였습니다.", Toast.LENGTH_SHORT).show();

    }
}
