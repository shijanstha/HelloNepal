package com.project.hellonepal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Shijan on 8/1/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    static String name = "helloNepal";
    static int version = 1;
    String createUserTableSql = "Create table if not exists 'route' ('id' INTEGER PRIMARY KEY AUTOINCREMENT," +
            "'name' text, 'points' text)";


    public DatabaseHelper(Context context) {
        super(context, name, null, version);

    }

    public void insertRoute(ContentValues contentValues) {
        getWritableDatabase().insert("route", "", contentValues);
    }

    public void deleteRoute(String id){
        getWritableDatabase().delete("route","id="+id,null);
    }

    public ArrayList<RouteInfo> getRoutes() {
        ArrayList<RouteInfo> list = new ArrayList<RouteInfo>();

        String sql = "Select * from route";
        Cursor c = getReadableDatabase().rawQuery(sql, null);

        while (c.moveToNext()) {
            RouteInfo info = new RouteInfo();
            info.id = c.getString(c.getColumnIndex("id"));
            info.name = c.getString(c.getColumnIndex("name"));
            info.points = c.getString(c.getColumnIndex("points"));
            list.add(info);

        }
        c.close();
        return list;
    }

    public RouteInfo getRoutesInfo(String id) {
        ArrayList<RouteInfo> list = new ArrayList<RouteInfo>();

        String sql = "Select * from route where id = " + id;
        Cursor c = getReadableDatabase().rawQuery(sql, null);
        RouteInfo info = new RouteInfo();

        while (c.moveToNext()) {
            info.id = c.getString(c.getColumnIndex("id"));
            info.name = c.getString(c.getColumnIndex("name"));
            info.points = c.getString(c.getColumnIndex("points"));

        }
        c.close();
        ;
        return info;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createUserTableSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
