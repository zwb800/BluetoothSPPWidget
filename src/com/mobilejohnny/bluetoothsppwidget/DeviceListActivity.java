package com.mobilejohnny.bluetoothsppwidget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.*;

/**
 * Created by admin2 on 2014/11/5.
 */
public class DeviceListActivity extends Activity {
    private int appWidgetid;
    private String deviceName;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.devicelist);
        ListView lvDevice = (ListView) findViewById(R.id.listView);
        Set<BluetoothDevice> devices = Bluetooth.getBondedDevices();

        Iterator<BluetoothDevice> it = devices.iterator();

        Intent intent = getIntent();
        appWidgetid = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,AppWidgetManager.INVALID_APPWIDGET_ID);
        setResult(RESULT_CANCELED);

        ArrayList<Map<String,String>> data = new ArrayList<Map<String, String>>();

        String[] from = new String[]{
                "Name",
                "Address",
        };

        int[] to = new int[]{
                R.id.txtName,
                R.id.txtAddress,
        };

        while (it.hasNext())
        {
            BluetoothDevice d = it.next();
            Map<String,String> map =  new HashMap<String, String>();
            map.put(from[0],d.getName());
            map.put(from[1],d.getAddress());
            data.add(map);
        }

        lvDevice.setAdapter(new SimpleAdapter(this,data,R.layout.list_item,from,to));

        lvDevice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                deviceName = (String) (( Map<String,Object>) adapterView.getItemAtPosition(i)).get("Name");

                Intent intent = new Intent(DeviceListActivity.this,ConfigureActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    private void done() {
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetid);
        setResult(RESULT_OK, resultValue);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK) {
            String label = data.getStringExtra("label");
            String dat = data.getStringExtra("data");

            Pref.set(this, appWidgetid, label, deviceName,dat);
            WidgetProvider.updateWidget(this,
                    appWidgetid, label);
            done();
        }
    }
}