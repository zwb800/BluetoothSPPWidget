package com.mobilejohnny.bluetoothsppwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * Created by zwb08_000 on 2014/11/4.
 */
public class WidgetProvider extends AppWidgetProvider {

    public static final String ACTION_SWITCH = "com.mobilejohnny.bluetoothsppwidget.action.SWITCH";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        for (int i = 0; i < appWidgetIds.length; i++) {
            int id = appWidgetIds[i];

            RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.widget);
            Intent intent = new Intent(ACTION_SWITCH);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,id);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.button,pendingIntent);
            appWidgetManager.updateAppWidget(id,views);
        }
    }
}
