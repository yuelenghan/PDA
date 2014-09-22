package com.ghtn.lihe.pda.util;

import android.util.Log;

import com.ghtn.lihe.pda.dao.DepartmentDao;
import com.ghtn.lihe.pda.dao.PlaceAreaDao;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lihe on 14/9/22.
 */
public class DataUtil {

    private static final String TAG = "DataUtil";

    public static void setupDataBase(final DepartmentDao departmentDao, final PlaceAreaDao placeAreaDao) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(10000);   // 10秒超时

        // 初始化department表
        String url = "http://192.168.1.123:8081/PDAService/department/mainDept";
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                if (statusCode == 200 && response != null && response.length() > 0) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            String deptNumber = jsonObject.getString("deptNumber");
                            String deptName = jsonObject.getString("deptName");

                            departmentDao.add(deptNumber, deptName);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(TAG, "JSON转换错误!");
                        }
                    }
                } else {
                    Log.e(TAG, "获取部门数据失败!");
                    Log.e(TAG, "statusCode = " + statusCode);
                }
            }

            @Override
            public void onFailure(Throwable e, JSONObject errorResponse) {
                Log.e(TAG, "获取部门数据失败!");
                Log.e(TAG, e.toString());
            }
        });
    }

}
