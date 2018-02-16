/*
 * Copyright (C) 2017 Ashar Khan <ashar786khan@gmail.com>
 *
 * This file is part of Matrix Calculator.
 *
 * Matrix Calculator is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Matrix Calculator is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Matrix Calculator.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.softminds.matrixcalculator.base_activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.softminds.matrixcalculator.BuildConfig;
import com.softminds.matrixcalculator.GlobalValues;
import com.softminds.matrixcalculator.MainActivity;
import com.softminds.matrixcalculator.R;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class SplashScreen extends Activity {

    final FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int SPLASH_TIME_OUT = 2000;

        ((GlobalValues) getApplication()).SetDonationKeyStatus();

        if (!((GlobalValues) getApplication()).DonationKeyFound()) { //if user is non pro then only check for offer key
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("promotion_offer", false);

            remoteConfig.setDefaults(hashMap);

            remoteConfig.setConfigSettings(new FirebaseRemoteConfigSettings.Builder()
                    .setDeveloperModeEnabled(BuildConfig.DEBUG)
                    .build());

            final Task<Void> fetch = remoteConfig.fetch(TimeUnit.HOURS.toSeconds(6));
            //if offer exist then activate pro features
            fetch.addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    remoteConfig.activateFetched();
                    UpdateKey();
                }
            });
        }

        //init the key status here
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isDark = preferences.getBoolean("DARK_THEME_KEY", false);
        if (isDark)
            setTheme(R.style.AppThemeDark);
        else
            setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        TextView textView = findViewById(R.id.textView9);
        String s = "Version " + BuildConfig.VERSION_NAME;
        textView.setText(s);
        RelativeLayout Root = findViewById(R.id.splash_screen);
        try {
            if (isDark) {
                Root.setBackgroundColor(ContextCompat.getColor(this, R.color.DarkcolorPrimaryDark));
            } else
                Root.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
        } catch (NullPointerException e) {
            Toast.makeText(getApplicationContext(), R.string.FatalError, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            finish();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();

            }
        }, SPLASH_TIME_OUT);
    }

    private void UpdateKey() {
        ((GlobalValues) getApplication()).UpdateStatus(remoteConfig.getBoolean("promotion_offer"));

    }
}
