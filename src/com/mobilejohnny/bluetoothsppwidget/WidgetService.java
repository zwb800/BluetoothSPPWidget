package com.mobilejohnny.bluetoothsppwidget;

import android.app.Notification;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * Created by office on 2014/11/7.
 */
public class WidgetService extends Service {
    private static final int BT_NOTIFICATION_ID = 1;
    private String deviceName;
    private Context context;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        Log.i("Service","onDestroy");
        super.onDestroy();
        stopForeground(true);
    }

    @Override
    public void onLowMemory() {
        Log.i("Service","onLowMemory");
        super.onLowMemory();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Notification notification = new NotificationCompat.Builder(getApplicationContext()).
                setContentTitle(getResources().getString(R.string.app_name)).
                setContentText(getResources().getString(R.string.sending)).
                setSmallIcon(R.drawable.bluetooth_icon).build();
        startForeground(BT_NOTIFICATION_ID, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        context = getApplicationContext();


        final int id = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        final RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.widget);
        final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        deviceName = Pref.getDeviceName(context,id);
        String data = Pref.getData(context, id);
        if(deviceName!=null)
        {
            showProcessing(id, views, appWidgetManager);

            Bluetooth bluetooth = new Bluetooth(deviceName);
            bluetooth.setListener(new BluetoothListener() {
                @Override
                public void result(int result) {
                    hideProcessing(views, appWidgetManager, id);
                    showToast(context,result);
                    WidgetService.this.stopSelf();
                }
            });

            bluetooth.connect(data);
        }

        return START_REDELIVER_INTENT;
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

    private void showProcessing(int id, RemoteViews views, AppWidgetManager appWidgetManager) {
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
