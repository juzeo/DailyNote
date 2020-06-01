package com.example.part3_8;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION=1;
    public DBHelper(Context context){
        super(context, "memodb",null,DATABASE_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db){

        String memoSQL= "create table tb_memo"+
                "(_id integer primary key autoincrement,"+
                "data,"+
                "title)";
        db.execSQL(memoSQL);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVerison, int newVerison){
        if(newVerison==DATABASE_VERSION){
            db.execSQL("drop table tb_memo");
            onCreate(db);
        }
    }
}

