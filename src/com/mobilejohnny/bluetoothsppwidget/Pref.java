package com.mobilejohnny.bluetoothsppwidget;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by admin2 on 2014/11/5.
 */
public class Pref {
    public static void set(Context context,int appWidgetid,String label, String deviceName) {
        SharedPreferences pref = getPref(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("dev"+appWidgetid,deviceName);
        editor.putString("label"+appWidgetid,label);
        editor.commit();
    }

    public static void removeAll(Context context,int[] appWidgetIds) {
        SharedPreferences pref = getPref(context);
        SharedPreferences.Editor editor = pref.edit();
        for (int i = 0; i < appWidgetIds.length; i++) {
            int id = appWidgetIds[i];
            editor.remove("label"+id);
            editor.remove("dev"+id);
        }

        editor.commit();
    }

    public static SharedPreferences getPref(Context context) {
        return context.getSharedPreferences("Default", 0);
    }

    public static String getDeviceName(Context context,int appWidgetid)
    {
        return getString(context,appWidgetid,"dev",null);
    }

    public static String getLabel(Context context,int appWidgetid)
    {
        return getString(context,appWidgetid,"label","error");
    }
    public static String getString(Context context,int appWidgetid,String prefix,String notfoundvalue)
    {
        SharedPreferences pref = getPref(context);
        return pref.getString(prefix+appWidgetid,notfoundvalue);
    }
}
