package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    private Context context;
    String TBLNAME = "member";

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context =context;
    }

    @Override
    public void onCreate(SQLiteDatabase db ) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TBLNAME +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, age INTEGER, phone TEXT NOT NULL UNIQUE);";
        db.execSQL(sql);
    }

    public void deleteTable() {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "DROP TABLE IF EXISTS " +  TBLNAME + ";";
        database.execSQL(sql);
        database.close();
    }

    public long insertData(String name, int age, String phone) {
        SQLiteDatabase database =getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("age",age);
        contentValues.put("phone",phone);
        try {
            return database.insertWithOnConflict(TBLNAME,null,contentValues,SQLiteDatabase.CONFLICT_NONE);
        } catch (Exception e) {
            return -1L;
        }
    }

    public Cursor searchData() {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "SELECT * FROM " + TBLNAME + ";";
        Cursor cursor = database.rawQuery(sql,null);
        return cursor;
    }

    public void deleteDATA(int id) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "DELETE FROM " + TBLNAME + " WHERE _id = '" + id + "';";
        database.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db , int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TBLNAME + ";";
        db.execSQL(sql);
        onCreate(db);
    }
}
