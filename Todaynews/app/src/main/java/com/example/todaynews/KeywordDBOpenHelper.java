package com.example.todaynews;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class KeywordDBOpenHelper {

    private static final String DATABASE_NAME = "TodayNews.db";
    private static final int DATABASE_VERSION = 1;
    public static SQLiteDatabase mDB;
    private DatabaseHelper mDBHelper;
    private Context mCtx;

    private class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db){
            db.execSQL(KeywordDB.CreateDB._CREATE0);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL("DROP TABLE IF EXISTS "+KeywordDB.CreateDB._TABLENAME0);
            onCreate(db);
        }
    }

    public KeywordDBOpenHelper(Context context){
        this.mCtx = context;
    }

    public KeywordDBOpenHelper open() throws SQLException {
        mDBHelper = new DatabaseHelper(mCtx, DATABASE_NAME, null, DATABASE_VERSION);
        mDB = mDBHelper.getWritableDatabase();
        return this;
    }

    public void create(){
        mDBHelper.onCreate(mDB);
    }

    public void close(){
        mDB.close();
    }

    public long insertColumn(String keyword){
        ContentValues values = new ContentValues();
        values.put(KeywordDB.CreateDB.KEYWORD, keyword);
        return mDB.insert(KeywordDB.CreateDB._TABLENAME0, null, values);
    }

    // Delete All
    public void deleteAllColumns() {
        mDB.delete(KeywordDB.CreateDB._TABLENAME0, null, null);
    }

    // Sort
    public Cursor setFirst(){
        Cursor c = mDB.rawQuery( "SELECT * FROM scarp ORDER BY " + 1 + ";", null);
        return c;
    }
}
