package com.ghtn.lihe.pda.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ghtn.lihe.pda.model.Place;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lihe on 14/9/22.
 */
public class PlaceDao {

    private MySqliteOpenHelper helper;

    public PlaceDao(Context context) {
        this.helper = new MySqliteOpenHelper(context);
    }

    public void add(int id, String name, Integer areaId, String mainDeptId) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("name", name);
        values.put("areaId", areaId);
        values.put("mainDeptId", mainDeptId);

        db.insert("place", null, values);

        db.close();
    }

    public void deleteAll() {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete("place", null, null);

        db.close();
    }

    public List<Place> getAll() {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("place", null, null, null, null, null, null);
        List<Place> list = new ArrayList<Place>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            Integer areaId = cursor.getInt(cursor.getColumnIndex("areaId"));
            String mainDeptId = cursor.getString(cursor.getColumnIndex("mainDeptId"));

            Place place = new Place();
            place.setId(id);
            place.setName(name);
            place.setAreaId(areaId);
            place.setMainDeptId(mainDeptId);

            list.add(place);
        }

        cursor.close();
        db.close();

        return list;
    }

    public List<Place> get(int areaId) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("place", null, "areaId = " + areaId, null, null, null, null);
        List<Place> list = new ArrayList<Place>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String mainDeptId = cursor.getString(cursor.getColumnIndex("mainDeptId"));

            Place place = new Place();
            place.setId(id);
            place.setName(name);
            place.setAreaId(areaId);
            place.setMainDeptId(mainDeptId);

            list.add(place);
        }

        cursor.close();
        db.close();

        return list;
    }

}
