package com.ghtn.lihe.pda;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.File;


public class SwMainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sw_main);

        Button takePhotoButton = (Button) findViewById(R.id.take_photo_button);
        Button inputInfoButton = (Button) findViewById(R.id.input_info_button);

        takePhotoButton.setOnClickListener(this);
        inputInfoButton.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sw_main, menu);
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
//        Intent intent = new Intent();
//        switch (view.getId()) {
//            case R.id.take_photo_button:
//                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivity(intent);
//                break;
//            case R.id.input_info_button:
//                intent.setClass(this, SwInputInfoActivity.class);
//                startActivity(intent);
//            default:
//                break;
//        }

        Log.i("tttt", Environment.getExternalStorageDirectory().getPath());

        // 检测sd是否可用
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
            Log.i("TestFile", "SD card is not avaiable/writeable right now.");
            return;
        }


// 设置照片名称
        String ImageName = Environment.getExternalStorageDirectory().getPath() + "/testImage/test2222222222222222.jpg";

        File file = new File(ImageName);
        if (!file.exists()) {
            file.mkdirs();
        }
//系统摄像头调用
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(intent, 1);
    }
}
