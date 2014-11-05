package com.mobilejohnny.bluetoothsppwidget;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Iterator;
import java.util.Set;

/**
 * Created by admin2 on 2014/11/5.
 */
public class DeviceList extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.devicelist);
        ListView lvDevice = (ListView) findViewById(R.id.listView);
        Set<BluetoothDevice> devices = Bluetooth.getBondedDevices();

        Iterator<BluetoothDevice> it = devices.iterator();
        String[] devicesArr = new String[devices.size()];
        int i=0;
        while (it.hasNext())
        {
            devicesArr[i++] = it.next().getName();
        }

        lvDevice.setAdapter(new ArrayAdapter<String>(this,R.layout.list_item,R.id.txtItem,devicesArr));
    }
}