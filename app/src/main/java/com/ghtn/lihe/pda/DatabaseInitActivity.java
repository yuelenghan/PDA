package com.ghtn.lihe.pda;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ghtn.lihe.pda.dao.DepartmentDao;
import com.ghtn.lihe.pda.dao.PlaceAreaDao;
import com.ghtn.lihe.pda.dao.PlaceDao;
import com.ghtn.lihe.pda.util.ConstantUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class DatabaseInitActivity extends Activity {

    private static final String TAG = "DatabaseInitActivity";

    private ProgressBar progressBar;
    private TextView textView;

    private AsyncHttpClient client;

    private SharedPreferences sharedPreferences;

    private boolean dialogShow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_init);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        textView = (TextView) findViewById(R.id.textView);

        ConnectivityManager mConnectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();

        // 如果没有初始化数据库, 并且网络可用, 那么进行数据库的初始化
        if (mNetworkInfo.isAvailable()) {
            Log.i(TAG, "网络可用, 没有初始化的数据库表将会进行初始化...");

            initDatabase(this);
        } else {
            new AlertDialog.Builder(this).setTitle("错误").setMessage("网络不可用, 无法进行数据库初始化!")
                    .setPositiveButton("确定", null).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.database_init, menu);
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
     * 初始化数据库
     */
    private void initDatabase(Context context) {
        final DepartmentDao departmentDao = new DepartmentDao(context);
        final PlaceAreaDao placeAreaDao = new PlaceAreaDao(context);
        final PlaceDao placeDao = new PlaceDao(context);

        client = new AsyncHttpClient();
        client.setTimeout(ConstantUtil.TIME_OUT);   // 20秒超时

        sharedPreferences = this.getSharedPreferences("share", MODE_PRIVATE);
        boolean departmentInit = sharedPreferences.getBoolean("departmentInit", false);
        boolean placeAreaInit = sharedPreferences.getBoolean("placeAreaInit", false);
        boolean placeInit = sharedPreferences.getBoolean("placeInit", false);

        if (!departmentInit) {
            Log.i(TAG, "对department表进行初始化...");

            // 初始化department表
            String url = ConstantUtil.SERVER_PATH + "/department/mainDept";
            client.get(url, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    if (statusCode == 200 && response != null && response.length() > 0) {
                        departmentDao.deleteAll();

                        // 更新进度条, 增加10个单位
                        updateProgressBar(10);

                        for (int i = 0; i < response.length(); i++) {

                            if (i == response.length() / 2) {
                                // 更新进度条, 增加10个单位
                                updateProgressBar(10);
                            }

                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String deptNumber = jsonObject.getString("deptNumber");
                                String deptName = jsonObject.getString("deptName");

                                departmentDao.add(deptNumber, deptName);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.e(TAG, "JSON转换错误!");

                                showErrorDialog();
                            }
                        }

                        // 更新进度条, 增加10个单位
                        updateProgressBar(10);

                        // 标示数据库已经初始化完成
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("departmentInit", true);
                        editor.apply();
                        Log.i(TAG, "department表初始化完成!");
                    } else {
                        Log.e(TAG, "获取部门数据失败!");
                        Log.e(TAG, "statusCode = " + statusCode);

                        if (!dialogShow) {
                            showErrorDialog();
                        }

                    }

                }

                @Override
                public void onFailure(Throwable e, JSONObject errorResponse) {
                    Log.e(TAG, "获取部门数据失败!");
                    Log.e(TAG, e.toString());


                    if (!dialogShow) {
                        showErrorDialog();
                    }
                }
            });
        } else {
            // 更新进度条, 增加30个单位
            updateProgressBar(30);
        }

        // 初始化placeArea表
        if (!placeAreaInit) {
            Log.i(TAG, "对placeArea表进行初始化...");

            String url = ConstantUtil.SERVER_PATH + "/place/allAreas";
            client.get(url, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    if (statusCode == 200 && response != null && response.length() > 0) {
                        placeAreaDao.deleteAll();

                        // 更新进度条, 增加10个单位
                        updateProgressBar(10);

                        for (int i = 0; i < response.length(); i++) {
                            if (i == response.length() / 2) {
                                // 更新进度条, 增加10个单位
                                updateProgressBar(10);
                            }

                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                int id = jsonObject.getInt("id");
                                String name = jsonObject.getString("name");
                                String mainDeptId = jsonObject.getString("mainDeptId");

                                placeAreaDao.add(id, name, mainDeptId);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.e(TAG, "JSON转换错误!");

                                showErrorDialog();
                            }
                        }

                        // 更新进度条, 增加10个单位
                        updateProgressBar(10);

                        // 标示数据库已经初始化完成
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("placeAreaInit", true);
                        editor.apply();
                        Log.i(TAG, "placeArea表初始化完成!");
                    } else {
                        Log.e(TAG, "获取区域数据失败!");
                        Log.e(TAG, "statusCode = " + statusCode);

                        if (!dialogShow) {
                            showErrorDialog();
                        }
                    }

                }

                @Override
                public void onFailure(Throwable e, JSONObject errorResponse) {
                    Log.e(TAG, "获取区域数据失败!");
                    Log.e(TAG, e.toString());

                    if (!dialogShow) {
                        showErrorDialog();
                    }
                }
            });
        } else {
            // 更新进度条, 增加30个单位
            updateProgressBar(30);
        }


        // 初始化place表
        if (!placeInit) {
            Log.i(TAG, "对place表进行初始化...");

            String url = ConstantUtil.SERVER_PATH + "/place/allPlaces";
            client.get(url, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    if (statusCode == 200 && response != null && response.length() > 0) {
                        placeDao.deleteAll();

                        // 更新进度条, 增加10个单位
                        updateProgressBar(10);

                        for (int i = 0; i < response.length(); i++) {

                            if (i == response.length() / 3) {
                                // 更新进度条, 增加10个单位
                                updateProgressBar(10);
                            }

                            if (i == response.length() * 2 / 3) {
                                // 更新进度条, 增加10个单位
                                updateProgressBar(10);
                            }

                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                int id = jsonObject.getInt("id");
                                String name = jsonObject.getString("name");
                                int areaId = jsonObject.getInt("areaId");
                                String mainDeptId = jsonObject.getString("mainDeptId");

                                placeDao.add(id, name, areaId, mainDeptId);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.e(TAG, "JSON转换错误!");

                                showErrorDialog();
                            }
                        }

                        // 更新进度条, 增加10个单位
                        updateProgressBar(10);

                        // 标示数据库已经初始化完成
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("placeInit", true);
                        editor.apply();
                        Log.i(TAG, "place表初始化完成!");
                    } else {
                        Log.e(TAG, "获取地点数据失败!");
                        Log.e(TAG, "statusCode = " + statusCode);

                        if (!dialogShow) {
                            showErrorDialog();
                        }
                    }

                }

                @Override
                public void onFailure(Throwable e, JSONObject errorResponse) {
                    Log.e(TAG, "获取地点数据失败!");
                    Log.e(TAG, e.toString());

                    if (!dialogShow) {
                        showErrorDialog();
                    }
                }
            });
        } else {
            // 更新进度条, 增加40个单位
            updateProgressBar(40);
        }
    }

    /**
     * 更新进度条
     *
     * @param increment 增长幅度
     */
    private void updateProgressBar(int increment) {
        progressBar.incrementProgressBy(increment);
        textView.setText("正在初始化数据库...(" + progressBar.getProgress() + "%)");

        if (progressBar.getProgress() == 100) {
            Intent intent = new Intent();
            intent.setClass(this, MainActivity.class);
            startActivity(intent);
        }
    }

    private void showErrorDialog() {
        dialogShow = true;
        new AlertDialog.Builder(this).setTitle("错误").setMessage("初始化数据库发生错误!")
                .setPositiveButton("重新初始化", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        client.cancelRequests(DatabaseInitActivity.this, true);
                        initDatabase(DatabaseInitActivity.this);
                        dialogShow = false;
                    }
                }).show();
    }
}
