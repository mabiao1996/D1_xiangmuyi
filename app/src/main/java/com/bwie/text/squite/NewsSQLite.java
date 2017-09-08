package com.bwie.text.squite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mabiao on 2017/9/6.
 */

public class NewsSQLite extends SQLiteOpenHelper {
    public NewsSQLite(Context context) {
        super(context,"1507",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table lb(type text, context text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
