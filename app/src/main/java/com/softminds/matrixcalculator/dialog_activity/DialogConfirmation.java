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

package com.softminds.matrixcalculator.dialog_activity;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

        TextView confirm = (TextView) findViewById(R.id.ActionOkay);
        TextView cancel = (TextView) findViewById(R.id.ActionCancel);
        TextView message = (TextView) findViewById(R.id.MessageDisplay);
        message.setText(getIntent().getExtras().getInt("MESSAGE"));
        confirm.setText(getIntent().getExtras().getInt("CONFIRM_TEXT"));
        cancel.setText(getIntent().getExtras().getInt("CANCEL_TEXT"));
        cancel.setAllCaps(true);
        cancel.setHeight(50);
        confirm.setHeight(50);
        confirm.setAllCaps(true);
        if(isDark)
        {
            confirm.setTextColor(ContextCompat.getColor(this,R.color.DarkcolorAccent));
            cancel.setTextColor(ContextCompat.getColor(this,R.color.DarkcolorAccent));
        }
        else
        {
            confirm.setTextColor(ContextCompat.getColor(this,R.color.colorAccent));
            cancel.setTextColor(ContextCompat.getColor(this,R.color.colorAccent));
        }
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getIntent().getIntExtra("NON_RESULT_CODE",0)==0)
                    finish();
                else
                {
                    setResult(getIntent().getIntExtra("NON_RESULT_CODE",0));
                    finish();
                }
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
