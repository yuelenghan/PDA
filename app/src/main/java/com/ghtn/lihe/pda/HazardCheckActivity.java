package com.ghtn.lihe.pda;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class HazardCheckActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hazard_check);

        Button nfcButton = (Button) findViewById(R.id.security_check_button);
        Button choosePlaceButton = (Button) findViewById(R.id.choose_place_button);

        nfcButton.setOnClickListener(this);
        choosePlaceButton.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.hazard_check, menu);
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

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        intent.setClass(this, HazardInfoCheckActivity.class);
        switch (view.getId()) {
            case R.id.security_check_button:
                // 通过nfc读取信息


                // 跳转到信息确认列表
                startActivity(intent);
                break;
            case R.id.choose_place_button:
                // 显示地点列表


                // 选择地点之后跳转到信息确认列表
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
