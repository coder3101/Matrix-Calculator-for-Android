package com.softminds.matrixcalculator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class AboutMe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isDark=preferences.getBoolean("DARK_THEME_KEY",false);
        if(isDark)
            setTheme(R.style.AppThemeDark);
        else
            setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aboutme_layout);
        ImageView imageView = (ImageView) findViewById(R.id.FecbookLogo);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),R.string.ToastMessage,Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse(getString(R.string.FacebookLink)));
                    startActivity(Intent.createChooser(intent,getResources().getString(R.string.Chooser)));
            }
        });
            }
        }
