package com.example.huitong.schoolbusinfoupload.util;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQuery;

/**
 * Created by yinxu on 2018/4/3.
 */

public class DatabaseUtil extends SQLiteOpenHelper {
    public DatabaseUtil(Context context, String name ,SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql="create table if not exists  student (id integer primary key autoincrement ,name varchar(64), phone varchar(64),address varchar(64),distance varchar(64))";
        sqLiteDatabase.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
