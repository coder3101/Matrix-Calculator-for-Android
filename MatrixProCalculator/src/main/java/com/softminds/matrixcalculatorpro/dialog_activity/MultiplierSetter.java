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

package com.softminds.matrixcalculatorpro.dialog_activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.softminds.matrixcalculatorpro.R;

public class MultiplierSetter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isDark = preferences.getBoolean("DARK_THEME_KEY", false);
        if (isDark)
            setTheme(R.style.AppThemeDarkDialog);
        else
            setTheme(R.style.AppThemeDialog);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplier_setter);

        final EditText editText = (EditText) findViewById(R.id.MultiplierSetterInput);
        Button button1 = (Button) findViewById(R.id.ConfirmSetFillScalar);
        Button button2 =  (Button) findViewById(R.id.CancelSetFillScalar);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!editText.getText().toString().isEmpty()) {
                    if((editText.getText().toString().contains(".") && //edittext contains dot means decimal
                            !PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("DECIMAL_USE",true))) {
                        Toast.makeText(getApplicationContext(),R.string.AllowDecimals,Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Intent intent = new Intent();
                        intent.putExtra("MULTIPLIER_VAL", Float.parseFloat(editText.getText().toString()));
                        setResult(1054, intent);
                        finish();
                    }
                }
                else
                    Toast.makeText(getApplicationContext(),R.string.NoValue,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
