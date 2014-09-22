package com.ghtn.lihe.pda;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.ghtn.lihe.pda.dao.DepartmentDao;
import com.ghtn.lihe.pda.dao.PlaceAreaDao;
import com.ghtn.lihe.pda.model.Department;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class MainActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    private ProgressBar departmentProgress;
    private ProgressBar placeAreaProgress;

    private DepartmentDao departmentDao;
    private PlaceAreaDao placeAreaDao;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button hazardCheckButton = (Button) findViewById(R.id.hazard_check_button);
        Button swCheckButton = (Button) findViewById(R.id.sw_check_button);
        Button yhCheckButtpn = (Button) findViewById(R.id.yh_check_button);
        Button qualityCheckButton = (Button) findViewById(R.id.quality_check_button);

        hazardCheckButton.setOnClickListener(this);
        swCheckButton.setOnClickListener(this);
        yhCheckButtpn.setOnClickListener(this);
        qualityCheckButton.setOnClickListener(this);

        departmentProgress = (ProgressBar) findViewById(R.id.departmentProgress);
        placeAreaProgress = (ProgressBar) findViewById(R.id.placeAreaProgress);

        departmentDao = new DepartmentDao(this);
        placeAreaDao = new PlaceAreaDao(this);


        ConnectivityManager mConnectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();

        // 如果没有初始化数据库, 并且网络可用, 那么进行数据库的初始化
        if (mNetworkInfo.isAvailable()) {
            Log.i(TAG, "网络可用, 没有初始化的数据库表将会进行初始化...");
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(10000);   // 10秒超时

            sharedPreferences = this.getSharedPreferences("share", MODE_PRIVATE);
            boolean departmentSetup = sharedPreferences.getBoolean("departmentSetup", false);
            boolean placeAreaSetup = sharedPreferences.getBoolean("placeAreaSetup", false);

            if (!departmentSetup) {
                Log.i(TAG, "对department表进行初始化...");
                departmentProgress.setVisibility(View.VISIBLE);

                // 初始化department表
                String url = "http://192.168.1.123:8081/PDAService/department/mainDept";
                client.get(url, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        if (statusCode == 200 && response != null && response.length() > 0) {
                            departmentDao.deleteAll();

                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    String deptNumber = jsonObject.getString("deptNumber");
                                    String deptName = jsonObject.getString("deptName");

//                                    Log.i(TAG, "deptNumber = " + deptNumber + ", deptName = " + deptName);

                                    departmentDao.add(deptNumber, deptName);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Log.e(TAG, "JSON转换错误!");
                                }
                            }

                            // 标示数据库已经初始化完成
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean("departmentSetup", true);
                            editor.apply();

                            // 初始化spinner
                            List<Department> deptList = departmentDao.getAll();
                            if (deptList != null && deptList.size() > 0) {
                                String[] spinnerData = new String[deptList.size()];
                                for (int i = 0; i < deptList.size(); i++) {
                                    spinnerData[i] = deptList.get(i).getDeptName();
                                }
                                Spinner spinner = (Spinner) findViewById(R.id.spinner);
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, spinnerData);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinner.setAdapter(adapter);

                            }
                        } else {
                            Log.e(TAG, "获取部门数据失败!");
                            Log.e(TAG, "statusCode = " + statusCode);
                        }

                        departmentProgress.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Throwable e, JSONObject errorResponse) {
                        Log.e(TAG, "获取部门数据失败!");
                        Log.e(TAG, e.toString());

                        departmentProgress.setVisibility(View.GONE);
                    }
                });
            }

            if (!placeAreaSetup) {
                // 初始化placeArea表
                Log.i(TAG, "对placeArea表进行初始化...");
                placeAreaProgress.setVisibility(View.VISIBLE);

                String url = "http://192.168.1.123:8081/PDAService/place/allAreas";
                client.get(url, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        if (statusCode == 200 && response != null && response.length() > 0) {
                            placeAreaDao.deleteAll();

                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    int id = jsonObject.getInt("id");
                                    String name = jsonObject.getString("name");
                                    String mainDeptId = jsonObject.getString("mainDeptId");

//                                Log.i(TAG, "deptNumber = " + deptNumber + ", deptName = " + deptName);

                                    placeAreaDao.add(id, name, mainDeptId);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Log.e(TAG, "JSON转换错误!");
                                }
                            }

                            // 标示数据库已经初始化完成
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean("placeAreaSetup", true);
                            editor.apply();

                        } else {
                            Log.e(TAG, "获取区域数据失败!");
                            Log.e(TAG, "statusCode = " + statusCode);
                        }

                        placeAreaProgress.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Throwable e, JSONObject errorResponse) {
                        Log.e(TAG, "获取区域数据失败!");
                        Log.e(TAG, e.toString());

                        placeAreaProgress.setVisibility(View.GONE);
                    }
                });
            }

        }


        // 初始化spinner
        List<Department> deptList = departmentDao.getAll();
        if (deptList != null && deptList.size() > 0) {
            String[] spinnerData = new String[deptList.size()];
            for (int i = 0; i < deptList.size(); i++) {
                spinnerData[i] = deptList.get(i).getDeptName();
            }
            Spinner spinner = (Spinner) findViewById(R.id.spinner);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, spinnerData);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 集中处理按钮的点击事件
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.hazard_check_button:
                intent.setClass(this, HazardPlaceActivity.class);
                startActivity(intent);
                break;
            case R.id.sw_check_button:
                intent.setClass(this, SwMainActivity.class);
                startActivity(intent);
                break;
            case R.id.yh_check_button:
                intent.setClass(this, YhPlaceActivity.class);
                startActivity(intent);
                break;
            case R.id.quality_check_button:
                intent.setClass(this, QualityPlaceActivity.class);
                startActivity(intent);
                break;
            default:
                System.out.println("default");
                break;
        }
    }

}
