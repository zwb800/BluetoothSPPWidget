package com.mobilejohnny.bluetoothsppwidget;

import android.appwidget.AppWidgetManager;
import android.content.*;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * Created by zwb08_000 on 2014/11/4.
 */
public class Receiver extends BroadcastReceiver {
    private String deviceName;
    private String data;

    @Override
    public void onReceive(final Context context, Intent intent) {
        SharedPreferences pref = context.getSharedPreferences("Default", 0);

        final int id = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        final RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.widget);
        final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        deviceName = Pref.getDeviceName(context,id);
        data = Pref.getData(context,id);
        if(deviceName!=null)
        {
            showProssing(id, views, appWidgetManager);

            Bluetooth bluetooth = new Bluetooth(deviceName);
            bluetooth.setListener(new BluetoothListener() {
                @Override
                public void result(int result) {
                    hideProcessing(views, appWidgetManager, id);
                    showToast(context,result );
                }
            });

            bluetooth.connect(data);
        }

    }

    private void showToast( Context context,int result) {
        String msg = "";
        if(result== Bluetooth.RESULT_FAILD)
        {
            msg = context.getString(R.string.toast_failed);
        }
        else if(result== Bluetooth.RESULT_SUCCESS)
        {
            msg = context.getString(R.string.toast_success);
        }
        else if(result== Bluetooth.RESULT_DEVICE_NOTFOUND)
        {
            msg = context.getString(R.string.toast_devicenotfound).replace("{0}",deviceName);
        }
        else if(result== Bluetooth.RESULT_BLUETOOTH_DISABLED)
        {
            msg = context.getString(R.string.toast_enablebluetooth);
        }

        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    private void showProssing(int id, RemoteViews views, AppWidgetManager appWidgetManager) {
        views.setViewVisibility(R.id.button, View.GONE);
        views.setViewVisibility(R.id.progressBar,View.VISIBLE);
        appWidgetManager.updateAppWidget(id,views);
    }

    private void hideProcessing(RemoteViews views, AppWidgetManager appWidgetManager, int id) {
        views.setViewVisibility(R.id.button, View.VISIBLE);
        views.setViewVisibility(R.id.progressBar,View.GONE);
        appWidgetManager.updateAppWidget(id, views);
    }
}
