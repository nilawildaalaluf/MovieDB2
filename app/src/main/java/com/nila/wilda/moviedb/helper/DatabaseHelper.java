package com.nila.wilda.moviedb.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wilda on 7/15/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "dbmovie.db";
    private static final String TABLE_BOOKMARK = "tbbookmark";

    private static final String COLUMN_ID = "id";
    private  static  final String COLUMN_DATA = "data";

    public  DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_BOOKMARK+" ("+COLUMN_ID+" text primary key, "+COLUMN_DATA+" text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+TABLE_BOOKMARK);
    }

    public void insertData(String id, String data){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ID,id);
        cv.put(COLUMN_DATA,data);
        db.insert(TABLE_BOOKMARK,null,cv);
    }


    public ArrayList<HashMap<String,String>> getData(@Nullable String id){
        String param = id==null ? "" : id;
        ArrayList<HashMap<String,String>> dataResult = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+ TABLE_BOOKMARK+" where id like '%"+param+"%'",null);
        cursor.moveToFirst();
        if(!cursor.isAfterLast()){
            HashMap<String ,String> tmp = new HashMap<>();
            tmp.put("id",cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
            tmp.put("data",cursor.getString(cursor.getColumnIndex(COLUMN_DATA)));
            dataResult.add(tmp);
            cursor.moveToNext();
        }
        return  dataResult;
    }
}
