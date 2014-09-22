package com.ghtn.lihe.pda.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lihe on 14/9/16.
 */
public class MySqliteOpenHelper extends SQLiteOpenHelper {

    public MySqliteOpenHelper(Context context) {
        super(context, "fxyk.db", null, 1);
    }

    /**
     * 第一次创建数据库时调用此方法
     *
     * @param sqLiteDatabase 创建的数据库
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table department (deptNumber, deptName)");
        sqLiteDatabase.execSQL("create table placeArea (id, name, mainDeptId)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }
}
