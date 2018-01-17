package com.oppo.cdo.update.domain.dto;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zk on 2018/1/16.
 */

public class wi extends SQLiteOpenHelper {


    public wi(Context context) {
        super(context, "market.db", null, 1);
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("CREATE TABLE pkgMd5 (_id INTEGER PRIMARY KEY AUTOINCREMENT ,package_name TEXT ,local_version_code LONG ,md5 TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}