package com.mobilejohnny.bluetoothsppwidget;

import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class ConfigureActivity extends Activity {

    private TextView txtLabel;
    private TextView txtData;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configure);

        txtLabel = (TextView)findViewById(R.id.txtLabel);
        txtData = (TextView)findViewById(R.id.txtData);


        Button btnDone = (Button) findViewById(R.id.btnDone);
        setResult(RESULT_CANCELED);

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                done();
            }
        });

    }

    private void done() {

        if(txtLabel.getText().length()==0)
        {
            txtLabel.setError("请输入按钮文本");
            return;
        }

        if(txtData.getText().length()==0)
        {
            txtData.setError("请输入数据");
            return;
        }

        Intent resultValue = new Intent();
        resultValue.putExtra("label",txtLabel.getText().toString());
        resultValue.putExtra("data",txtData.getText().toString());
        setResult(RESULT_OK, resultValue);
        finish();
    }

}
