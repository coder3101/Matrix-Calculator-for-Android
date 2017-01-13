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

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.softminds.matrixcalculator.R;

public class DeterminantResult extends AppCompatActivity {

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
        Button CopyIt= (Button) findViewById(R.id.ActionOkay);
        Button ignore = (Button) findViewById(R.id.ActionCancel);
        TextView result = (TextView) findViewById(R.id.MessageDisplay);
        result.setText(ConvertToNormal(getIntent().getExtras().getDouble("RESULTANT",0)));
        CopyIt.setText(R.string.copy);
        ignore.setText(R.string.Done);


        ignore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        CopyIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("DETERMINANT_RES",
                        ConvertToNormal(getIntent().getExtras().getDouble("RESULTANT",0)));
                clipboardManager.setPrimaryClip(clipData);
                if(clipboardManager.hasPrimaryClip())
                    Toast.makeText(getApplicationContext(),R.string.CopyToClip,Toast.LENGTH_SHORT).show();
                else
                    Log.d("Clipboard","Unable to set PrimaryClip");
                finish();
            }
        });
    }

    private String ConvertToNormal (double res){
       return String.valueOf(res);
    }
}
