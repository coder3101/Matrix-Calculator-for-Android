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

package com.softminds.matrixcalculator;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.HashMap;

public class ChangeLogActivity extends AppCompatActivity {

    FirebaseRemoteConfig firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
    ProgressBar bar;
    LinearLayout layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isDark=preferences.getBoolean("DARK_THEME_KEY",false);
        if(isDark)
            setTheme(R.style.AppThemeDark);
        else
            setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.changelog_layout);

        layout = (LinearLayout) findViewById(R.id.changelog_container);
        bar = (ProgressBar) findViewById(R.id.news_loading_pr);

        firebaseRemoteConfig.setConfigSettings(new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build());
        HashMap<String,Object> defaultNews = new HashMap<>();
        //10 changes
        defaultNews.put("news1","null");
        defaultNews.put("news2","null");
        defaultNews.put("news3","null");
        defaultNews.put("news4","null");
        defaultNews.put("news5","null");
        defaultNews.put("news6","null");
        defaultNews.put("news7","null");
        defaultNews.put("news8","null");
        defaultNews.put("news9","null");
        defaultNews.put("news10","null");
        //to mark the top as latest red for new changes
        defaultNews.put("mark_red",false);

        firebaseRemoteConfig.setDefaults(defaultNews);

        final Task<Void> fetch = firebaseRemoteConfig.fetch(0);
        fetch.addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                firebaseRemoteConfig.activateFetched();
                Log.d("RemoteConfig","Change-logs Updated");
                for(int i=0;i<10;i++)
                    SetNewContents(i);
                layout.setVisibility(View.VISIBLE);
            }
        });

        fetch.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Failed", "Cannot Load the Changes");
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),R.string.InternetPlzz,Toast.LENGTH_SHORT).show();
                finish();
            }
        });



    }

    private void SetNewContents(int key){
        if(!Changes(key).equals("null")) {
            CardView.LayoutParams param = new CardView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            CardView card = new CardView(this);
            if(PreferenceManager.getDefaultSharedPreferences(this).getBoolean("DARK_THEME_KEY",false))
                card.setCardBackgroundColor(ContextCompat.getColor(this,R.color.DarkcolorPrimary));
            card.setCardElevation(5);
            card.setLayoutParams(param);
            card.setPadding(ConvertTopx(15), ConvertTopx(15), ConvertTopx(15), ConvertTopx(15));
            card.setUseCompatPadding(true);
            TextView changes = new TextView(this);
            changes.setGravity(Gravity.CENTER);
            changes.setPadding(ConvertTopx(5),ConvertTopx(5),ConvertTopx(5),ConvertTopx(5));
            changes.setText(Changes(key));
            changes.setTypeface(Typeface.MONOSPACE);
            if(firebaseRemoteConfig.getBoolean("mark_red")&& key==0)
                changes.setTextColor(Color.RED);
            card.addView(changes);
            layout.addView(card);
        }
        bar.setVisibility(View.GONE);
    }

    private int ConvertTopx(float dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return ((int) (dp * ((float) metrics.densityDpi) / DisplayMetrics.DENSITY_DEFAULT));

    }
    private String Changes(int k){
        return firebaseRemoteConfig.getString("news"+String.valueOf(k+1));

    }

}
