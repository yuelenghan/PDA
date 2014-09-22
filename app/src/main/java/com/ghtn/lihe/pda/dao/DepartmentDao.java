package com.ghtn.lihe.pda.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ghtn.lihe.pda.model.Department;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lihe on 14/9/16.
 */
public class DepartmentDao {

    private MySqliteOpenHelper helper;

    public DepartmentDao(Context context) {
        this.helper = new MySqliteOpenHelper(context);
    }

    public void add(String deptNumber, String deptName) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("deptNumber", deptNumber);
        values.put("deptName", deptName);
        db.insert("department", null, values);

        db.close();
    }

    public void deleteAll() {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete("department", null, null);

        db.close();
    }

    public List<Department> getAll() {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("department", null, null, null, null, null, null);
        List<Department> list = new ArrayList<Department>();
        while (cursor.moveToNext()) {
            String deptNumber = cursor.getString(cursor.getColumnIndex("deptNumber"));
            String deptName = cursor.getString(cursor.getColumnIndex("deptName"));

            Department department = new Department();
            department.setDeptNumber(deptNumber);
            department.setDeptName(deptName);

            list.add(department);
        }

        cursor.close();
        db.close();

        return list;
    }
}
