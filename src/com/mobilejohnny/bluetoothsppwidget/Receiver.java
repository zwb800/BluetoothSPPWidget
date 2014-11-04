package com.mobilejohnny.bluetoothsppwidget;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * Created by zwb08_000 on 2014/11/4.
 */
public class Receiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {
        Log.i("intent",intent.getAction());
        final int id = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,AppWidgetManager.INVALID_APPWIDGET_ID);
        final RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.widget);
        final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        views.setViewVisibility(R.id.button, View.GONE);
        views.setViewVisibility(R.id.progressBar,View.VISIBLE);
        appWidgetManager.updateAppWidget(id,views);

        Bluetooth bluetooth = new Bluetooth();
        bluetooth.connect(new BluetoothHandler() {
            @Override
            public void result(boolean success) {
                views.setViewVisibility(R.id.button,View.VISIBLE);
                views.setViewVisibility(R.id.progressBar,View.GONE);
                appWidgetManager.updateAppWidget(id,views);
                Toast.makeText(context,success?"成功":"失败",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
