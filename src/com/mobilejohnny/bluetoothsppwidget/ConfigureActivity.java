package com.mobilejohnny.bluetoothsppwidget;

import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.preference.Preference;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

public class ConfigureActivity extends Activity {

    private int appWidgetid;
    private TextView txtLabel;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configure);

        Intent intent = getIntent();
        appWidgetid = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,AppWidgetManager.INVALID_APPWIDGET_ID);

        txtLabel = (TextView)findViewById(R.id.txtLabel);
        Button btnDone = (Button) findViewById(R.id.btnDone);
        setResult(RESULT_CANCELED);

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String label = txtLabel.getText().toString();
                Pref.set(ConfigureActivity.this,appWidgetid,label, null);

                WidgetProvider.updateWidget(ConfigureActivity.this,
                        appWidgetid, label);
                done(appWidgetid);
            }
        });

    }

    private void done(int appWidgetid) {
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetid);
        setResult(RESULT_OK, resultValue);
        finish();
    }
}
