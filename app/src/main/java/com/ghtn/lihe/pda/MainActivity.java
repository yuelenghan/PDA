package com.ghtn.lihe.pda;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity implements View.OnClickListener {

    private Button hazardCheckButton;
    private Button swCheckButton;
    private Button yhCheckButtpn;
    private Button qualityCheckButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hazardCheckButton = (Button) findViewById(R.id.hazard_check_button);
        swCheckButton = (Button) findViewById(R.id.sw_check_button);
        yhCheckButtpn = (Button) findViewById(R.id.yh_check_button);
        qualityCheckButton = (Button) findViewById(R.id.quality_check_button);

        hazardCheckButton.setOnClickListener(this);
        swCheckButton.setOnClickListener(this);
        yhCheckButtpn.setOnClickListener(this);
        qualityCheckButton.setOnClickListener(this);
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
