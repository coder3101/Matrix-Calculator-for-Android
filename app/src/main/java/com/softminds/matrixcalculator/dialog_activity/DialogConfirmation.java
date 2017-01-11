package com.softminds.matrixcalculator.dialog_activity;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.softminds.matrixcalculator.R;

public class DialogConfirmation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isDark=preferences.getBoolean("DARK_THEME_KEY",false);
        if(isDark)
            setTheme(R.style.AppThemeDarkDialog);
        else
            setTheme(R.style.AppThemeDialog);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_confirmation);

        Button confirm = (Button) findViewById(R.id.ActionOkay);
        Button cancel = (Button) findViewById(R.id.ActionCancel);
        TextView message = (TextView) findViewById(R.id.MessageDisplay);
        message.setText(getIntent().getExtras().getInt("MESSAGE"));
        confirm.setText(getIntent().getExtras().getInt("CONFIRM_TEXT"));
        cancel.setText(getIntent().getExtras().getInt("CANCEL_TEXT"));
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(getIntent().getExtras().getInt("RESULT_CODE"));
                finish();
            }
        });
    }
}
