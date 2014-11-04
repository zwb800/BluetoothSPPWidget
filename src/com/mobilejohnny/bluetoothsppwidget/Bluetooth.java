package com.mobilejohnny.bluetoothsppwidget;

import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

/**
 * Created by zwb08_000 on 2014/11/4.
 */
public class Bluetooth {
    private final BluetoothDevice device;
    private BluetoothAdapter adapter;
    private BluetoothSocket socket;

    final UUID SPP_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    AsyncTask task = new AsyncTask() {
        @Override
        protected Object doInBackground(Object[] objects) {
            connectSocket();
            if(socket.isConnected())
            {
                try {
                    OutputStream out = socket.getOutputStream();
                    out.write('1');
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return objects[0];
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            BluetoothHandler handler = (BluetoothHandler) o;
            handler.result(socket.isConnected());
            closeSocket();
        }
    };

    public Bluetooth()
    {
        adapter = BluetoothAdapter.getDefaultAdapter();

        device =  findDeviceByName("BTCOM");



    }

    public void connect(BluetoothHandler handler) {

        createSocket();

        if(socket!=null)
        {
            task.execute(handler);
        }
    }

    private void createSocket() {
        try {
            Method m = device.getClass().getMethod("createRfcommSocket", new Class[]{int.class});
            socket = (BluetoothSocket) m.invoke(device, 1);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void connectSocket() {
        try {
            socket.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeSocket() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private BluetoothDevice findDeviceByName(String name) {
        BluetoothDevice device = null;
        Set<BluetoothDevice> devices = getBondedDevices();
        Iterator<BluetoothDevice> it = devices.iterator();
        while(it.hasNext())
        {
            BluetoothDevice d = it.next();
            if(d.getName().equals(name))
            {
                device = d;
                break;
            }
        }
        return device;
    }

    private Set<BluetoothDevice> getBondedDevices() {
        return adapter.getBondedDevices();
    }


}
