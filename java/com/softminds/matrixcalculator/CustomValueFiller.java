package com.softminds.matrixcalculator;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CustomValueFiller extends AppCompatActivity {

    final String Key="com.softminds.matrixCalculator.CUSTOM_VALUE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isDark=preferences.getBoolean("DARK_THEME_KEY",false);
        if(isDark)
            setTheme(R.style.AppThemeDarkDialog);
        else
            setTheme(R.style.AppThemeDialog);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_filler_value);
        final EditText editText= (EditText) findViewById(R.id.CustomValue);
        Button button = (Button) findViewById(R.id.ConfirmCustomFill);
        Button exit = (Button) findViewById(R.id.CancelCustomFill);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText.getText().toString().isEmpty())
                    Toast.makeText(getApplication(),R.string.NoValue,Toast.LENGTH_SHORT).show();
                else
                {
                    Intent intent =  new Intent();
                    intent.putExtra(Key,Float.parseFloat(editText.getText().toString()));
                    setResult(2,intent);
                    finish();
                }

            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
