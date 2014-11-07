package com.mobilejohnny.bluetoothsppwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RemoteViews;

/**
 * Created by zwb08_000 on 2014/11/4.
 */
public class WidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        for (int i = 0; i < appWidgetIds.length; i++) {
            int id = appWidgetIds[i];
            String label = Pref.getLabel(context,id);
            updateWidget(context, appWidgetManager, id, label);
        }
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        onUpdate(context,appWidgetManager,new int[]{appWidgetId});

    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        Pref.removeAll(context, appWidgetIds);
    }

    public static void updateWidget(Context context,AppWidgetManager appWidgetManager,int appWidgetid,String label) {
        RemoteViews views = new RemoteViews(context.getPackageName(),
                R.layout.widget);

        Intent intent = new Intent(context,WidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetid);
        int requestcode = appWidgetid;//这里requestcode用于区分各intent 而不是intent对象

        PendingIntent pendingIntent = PendingIntent.getService(context,requestcode,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.button,pendingIntent);
        views.setTextViewText(R.id.button,label);
        appWidgetManager.updateAppWidget(appWidgetid, views);
    }

    public static void updateWidget(Context context,int appWidgetid,String label) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        updateWidget(context,appWidgetManager,appWidgetid,label);
    }
}
