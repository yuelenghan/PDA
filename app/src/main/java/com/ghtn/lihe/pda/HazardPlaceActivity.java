package com.ghtn.lihe.pda;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class HazardPlaceActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hazard_place);

        Button nfcButton = (Button) findViewById(R.id.nfc_button);
        Button placeSelectButton = (Button) findViewById(R.id.place_select_button);

        nfcButton.setOnClickListener(this);
        placeSelectButton.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.hazard_place, menu);
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
        switch (view.getId()) {
            case R.id.nfc_button:
                Toast.makeText(this, "读取nfc信息", Toast.LENGTH_LONG).show();
                break;
            case R.id.place_select_button:
                Intent intent = new Intent();
                intent.setClass(this, HazardMainActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
