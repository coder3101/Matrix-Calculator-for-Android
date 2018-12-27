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
import android.util.Log;
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
import com.softminds.matrixcalculator.MatrixV2;
import com.softminds.matrixcalculator.R;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
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

        if (PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("PERSIST_ENABLER", false)) {
            try {
                FileInputStream fis = getApplicationContext().openFileInput("persist_data.mat");
                ObjectInputStream os = new ObjectInputStream(fis);
                ArrayList<MatrixV2> vars = (ArrayList<MatrixV2>) os.readObject();
                Log.d("TTT","ABOUT TO RESTORE "+ String.valueOf(vars.size()));
                os.close();
                fis.close();
                Log.d("TTT", "Previously on MainList " + String.valueOf(((GlobalValues) getApplication()).GetCompleteList().size()));
                ((GlobalValues) getApplication()).GetCompleteList().clear();
                ((GlobalValues) getApplication()).GetCompleteList().addAll(vars);
                Log.d("TTT", "Total Value " + String.valueOf(((GlobalValues) getApplication()).GetCompleteList().size()));
                Toast.makeText(getApplicationContext(), "Restored all Variables", Toast.LENGTH_SHORT).show();
            } catch (FileNotFoundException | ClassNotFoundException e) {
                // Nothing to restore simply skip
                e.printStackTrace();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "Cannot Load Persisted Variable. I/O Error", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }

    private void UpdateKey() {
        ((GlobalValues) getApplication()).UpdateStatus(remoteConfig.getBoolean("promotion_offer"));

    }
}
