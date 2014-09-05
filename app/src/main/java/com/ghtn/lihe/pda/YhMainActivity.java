package com.ghtn.lihe.pda;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class YhMainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yh_main);

        Button yhInputButton = (Button) findViewById(R.id.yh_input_button);
        Button yhCheckButton = (Button) findViewById(R.id.yh_check_button);

        yhInputButton.setOnClickListener(this);
        yhCheckButton.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.yh_main, menu);
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
        switch (view.getId()) {
            case R.id.yh_input_button:
                intent.setClass(this, YhCategoryActivity.class);
                startActivity(intent);
                break;
            case R.id.yh_check_button:
                intent.setClass(this, YhCheckActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
