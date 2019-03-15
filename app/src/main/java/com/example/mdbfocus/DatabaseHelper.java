package com.example.mdbfocus;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "locationsTable";
    private static final String COL_ID = "id";

    private static final String COL_NAME = "name";
    private static final String COL_LAT = "latitude";
    private static final String COL_LON = "longitude";
    private static final String COL_ADD = "address";

    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME
                + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_NAME + " TEXT NOT NULL UNIQUE, "
                + COL_LON + " DOUBLE, "
                + COL_LAT + " DOUBLE, "
                + COL_ADD + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(Location l) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME, l.getName());
        contentValues.put(COL_ADD, l.getAddress());
        contentValues.put(COL_LAT, l.getLat());
        contentValues.put(COL_LON, l.getLon());

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public ArrayList<Location> getAllData() {

        ArrayList<Location> locations = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Location p = new Location(
                        cursor.getString(cursor.getColumnIndex(COL_NAME)),
                        cursor.getString(cursor.getColumnIndex(COL_ADD)),
                        cursor.getDouble(cursor.getColumnIndex(COL_LAT)),
                        cursor.getDouble(cursor.getColumnIndex(COL_LON))
                );
                locations.add(p);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return locations;

    }

    public boolean deleteitem(String name){

        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "DELETE FROM " + TABLE_NAME +
                " WHERE " + COL_NAME + " = ?";

        String[] strParams = {name};

        db.execSQL(selectQuery, strParams);
        db.close();
        return  true;

    }

}