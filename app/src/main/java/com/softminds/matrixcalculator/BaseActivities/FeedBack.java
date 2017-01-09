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

package com.softminds.matrixcalculator.BaseActivities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.softminds.matrixcalculator.BaseActivities.GlobalValues;
import com.softminds.matrixcalculator.R;

public class FeedBack extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isDark=preferences.getBoolean("DARK_THEME_KEY",false);
        if(isDark)
            setTheme(R.style.AppThemeDark);
        else
            setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed_back);
        final EditText editText=(EditText) findViewById(R.id.FeedT);
        Button Confirm = (Button) findViewById(R.id.Confirm);
        Button Not = (Button) findViewById(R.id.NotNow);
        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(((GlobalValues)getApplication()).hasProfainity(editText.getText().toString()))
                {
                    final Snackbar a;
                    a=Snackbar.make(findViewById(R.id.feed_back),R.string.Warning5,Snackbar.LENGTH_INDEFINITE);
                    a.show();
                    a.setAction(R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            a.dismiss();
                        }
                    });

                }
                else
                {
                    if(editText.getText().toString().isEmpty())
                        Toast.makeText(getApplicationContext(),R.string.Warning6,Toast.LENGTH_LONG).show();
                    else {
                        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "ashar786khan@gmail.com", null));
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback for the Matrix Calculator");
                        intent.putExtra(Intent.EXTRA_TEXT, editText.getText().toString());
                        startActivity(Intent.createChooser(intent, "Send Via"));
                    }
                }
            }
        });
        Not.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Okay Some Other Day",Toast.LENGTH_SHORT).show();
                finish();

            }
        });
    }

}
