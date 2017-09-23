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

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.softminds.matrixcalculator.BuildConfig;
import com.softminds.matrixcalculator.GlobalValues;
import com.softminds.matrixcalculator.R;

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

        TextView textView = (TextView) findViewById(R.id.TextViewCard3);
        String s = "Version " + BuildConfig.VERSION_NAME;
        textView.setText(s);

        if(((GlobalValues)getApplication()).DonationKeyFound()) {
            TextView textView1 = (TextView) findViewById(R.id.Mymessage);
            textView1.setText(getString(R.string.ThanksForDonation));
        }

            }

    public void showSources(View view) {

        Intent sources = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/coder3101/Matrix-Calculator-for-Android"));
        startActivity(Intent.createChooser(sources,"View Sources using"));

    }
}
