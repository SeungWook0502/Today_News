package com.example.todaynews;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper {

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
            db.execSQL(ScarpDB.CreateDB._CREATE0);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL("DROP TABLE IF EXISTS "+ScarpDB.CreateDB._TABLENAME0);
            onCreate(db);
        }
    }

    public DBOpenHelper(Context context){
        this.mCtx = context;
    }

    public DBOpenHelper open() throws SQLException {
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

    public long insertColumn(String keyword, String article_title, String article_content, String article_url){
        ContentValues values = new ContentValues();
        values.put(ScarpDB.CreateDB.KEYWORD, keyword);
        values.put(ScarpDB.CreateDB.ARTICLE_TITLE, article_title);
        values.put(ScarpDB.CreateDB.ARTICLE_CONTENT, article_content);
        values.put(ScarpDB.CreateDB.ARTICLE_URL, article_url);
        return mDB.insert(ScarpDB.CreateDB._TABLENAME0, null, values);
    }

    // Delete All
    public void deleteAllColumns() {
        mDB.delete(ScarpDB.CreateDB._TABLENAME0, null, null);
    }

    // Delete Column
    public boolean deleteColumn(long id){
        return mDB.delete(ScarpDB.CreateDB._TABLENAME0, "_id="+id, null) > 0;
    }

    // Sort
    public Cursor setFirst(){
        Cursor c = mDB.rawQuery( "SELECT * FROM scarp ORDER BY " + 1 + ";", null);
        return c;
    }

    public Cursor selectColumns(){
        return mDB.query(ScarpDB.CreateDB._TABLENAME0, null, null, null, null, null, null);
    }
}
