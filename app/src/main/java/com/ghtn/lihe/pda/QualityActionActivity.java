package com.ghtn.lihe.pda;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class QualityActionActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quality_action);

        Button qualityScoreButton = (Button) findViewById(R.id.quality_score_button);
        Button qualityCheckButton = (Button) findViewById(R.id.quality_check_button);

        qualityScoreButton.setOnClickListener(this);
        qualityCheckButton.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.quality_action, menu);
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
            case R.id.quality_score_button:
                intent.setClass(this, QualityScoreActivity.class);
                startActivity(intent);
                break;
            case R.id.quality_check_button:
                intent.setClass(this, QualityCheckActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
