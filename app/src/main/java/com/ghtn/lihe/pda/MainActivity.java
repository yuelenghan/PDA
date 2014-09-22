package com.ghtn.lihe.pda;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.ghtn.lihe.pda.dao.DepartmentDao;
import com.ghtn.lihe.pda.model.Department;

import java.util.List;


public class MainActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

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

        // 初始化spinner
        DepartmentDao departmentDao = new DepartmentDao(this);
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
