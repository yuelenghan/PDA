package com.ghtn.lihe.pda.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ghtn.lihe.pda.model.PlaceArea;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lihe on 14/9/22.
 */
public class PlaceAreaDao {

    private MySqliteOpenHelper helper;

    public PlaceAreaDao(Context context) {
        this.helper = new MySqliteOpenHelper(context);
    }

    public void add(int id, String name, String mainDeptId) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("name", name);
        values.put("mainDeptId", mainDeptId);
        db.insert("placeArea", null, values);

        db.close();
    }

    public void deleteAll() {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete("placeArea", null, null);

        db.close();
    }

    public List<PlaceArea> getAll() {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("placeArea", null, null, null, null, null, null);
        List<PlaceArea> list = new ArrayList<PlaceArea>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String mainDeptId = cursor.getString(cursor.getColumnIndex("mainDeptId"));

            PlaceArea placeArea = new PlaceArea();
            placeArea.setId(id);
            placeArea.setName(name);
            placeArea.setMainDeptId(mainDeptId);

            list.add(placeArea);
        }

        cursor.close();
        db.close();

        return list;
    }

}
