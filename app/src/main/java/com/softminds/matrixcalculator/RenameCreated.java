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

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RenameCreated extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isDark=preferences.getBoolean("DARK_THEME_KEY",false);
        if(isDark)
            setTheme(R.style.AppThemeDarkDialog);
        else
            setTheme(R.style.AppThemeDialog);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rename_created);

        final EditText  e = (EditText) findViewById(R.id.RenameValue);
        e.setText(getIntent().getStringExtra("TITLE_OF_THIS_FORMATION"));
        Button yes = (Button) findViewById(R.id.ConfirmRename);
        Button no = (Button) findViewById(R.id.CancelRename);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(e.getText().toString().isEmpty())
                    Toast.makeText(getApplicationContext(),R.string.NoValue,Toast.LENGTH_SHORT).show();
                else {
                    if(((GlobalValues)getApplication()).hasProfainity(e.getText().toString()))
                    {
                        Toast.makeText(getApplicationContext(),R.string.Warning7,Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Intent intent = new Intent();
                        intent.putExtra("NEW_NAME_FOR_THIS_MATRIX", e.getText().toString());
                        setResult(100, intent);
                        finish();
                    }
                }

            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
