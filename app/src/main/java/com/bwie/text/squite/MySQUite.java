package com.bwie.text.squite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mabiao on 2017/9/5.
 */

public class MySQUite extends SQLiteOpenHelper {
    public MySQUite(Context context) {
        super(context,"20170905shuju",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    sqLiteDatabase.execSQL("create table sj(type varchar,content text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
