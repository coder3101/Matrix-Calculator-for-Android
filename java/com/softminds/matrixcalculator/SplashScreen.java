package com.softminds.matrixcalculator;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class SplashScreen extends Activity {
    private static int SPLASH_TIME_OUT=2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isDark=preferences.getBoolean("DARK_THEME_KEY",false);
        if(isDark)
            setTheme(R.style.AppThemeDark);
        else
            setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        RelativeLayout Root = (RelativeLayout) findViewById(R.id.splash_screen);
        if(isDark)
        { Root.setBackgroundColor(ContextCompat.getColor(this,R.color.DarkcolorPrimaryDark));}
        else
         Root.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.colorPrimaryDark));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent= new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();

            }
        },SPLASH_TIME_OUT);
    }
}
