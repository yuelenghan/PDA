package com.ghtn.lihe.pda;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ghtn.lihe.pda.dao.PlaceAreaDao;
import com.ghtn.lihe.pda.dao.PlaceDao;
import com.ghtn.lihe.pda.model.Place;
import com.ghtn.lihe.pda.model.PlaceArea;

import java.util.List;


public class HazardPlaceActivity extends Activity implements View.OnClickListener {

    public static int placeId;
    private static int areaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hazard_place);

        Button nfcButton = (Button) findViewById(R.id.nfc_button);
        nfcButton.setOnClickListener(this);

        // 初始化spinner
        PlaceAreaDao placeAreaDao = new PlaceAreaDao(this);
        List<PlaceArea> areaList = placeAreaDao.get(MainActivity.mainDeptId);
        if (areaList != null && areaList.size() > 0) {
            final Integer[] areaIds = new Integer[areaList.size()];
            String[] areaNames = new String[areaList.size()];

            for (int i = 0; i < areaList.size(); i++) {
                areaIds[i] = areaList.get(i).getId();
                areaNames[i] = areaList.get(i).getName();
            }

            Spinner areaSpinner = (Spinner) findViewById(R.id.area_spinner);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(HazardPlaceActivity.this, android.R.layout.simple_spinner_item, areaNames);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            areaSpinner.setAdapter(adapter);

            final PlaceDao placeDao = new PlaceDao(this);
            areaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    areaId = areaIds[position];
                    List<Place> placeList = placeDao.get(areaId);
                    if (placeList != null && placeList.size() > 0) {
                        final Integer[] placeIds = new Integer[placeList.size()];
                        final String[] placeNames = new String[placeList.size()];

                        for (int j = 0; j < placeList.size(); j++) {
                            placeIds[j] = placeList.get(j).getId();
                            placeNames[j] = placeList.get(j).getName();
                        }

                        Spinner placeSpinner = (Spinner) findViewById(R.id.place_spinner);
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(HazardPlaceActivity.this, android.R.layout.simple_spinner_item, placeNames);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        placeSpinner.setAdapter(adapter);

                        placeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                placeId = placeIds[position];
                                TextView placeInfo = (TextView) findViewById(R.id.place_info);
                                placeInfo.setText(placeNames[position]);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
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

            default:
                break;
        }
    }
}
