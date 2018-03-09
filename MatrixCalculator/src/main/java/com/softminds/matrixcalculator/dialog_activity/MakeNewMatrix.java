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

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.softminds.matrixcalculator.Type;
import com.softminds.matrixcalculator.base_activities.FillingMatrix;
import com.softminds.matrixcalculator.GlobalValues;
import com.softminds.matrixcalculator.R;

public class MakeNewMatrix extends AppCompatActivity {
    final int RESCODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isDark = preferences.getBoolean("DARK_THEME_KEY", false);
        if (isDark)
            setTheme(R.style.AppThemeDarkDialog);
        else
            setTheme(R.style.AppThemeDialog);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_matrix);
        Button button = findViewById(R.id.buttonMake);
        final EditText editText = findViewById(R.id.MatrixName);
        editText.setSingleLine();
        final Spinner Typespinner = findViewById(R.id.MatType);
        final NumberPicker RowSpinner = findViewById(R.id.RowOrder);
        RowSpinner.setMinValue(1);
        RowSpinner.setMaxValue(9);
        RowSpinner.setValue(3);
        final NumberPicker ColSpinner = findViewById(R.id.ColOrder);
        ColSpinner.setMinValue(1);
        ColSpinner.setMaxValue(9);
        ColSpinner.setValue(3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (NoError()) {
                    Type t = TypeFromString(Typespinner.getSelectedItem().toString());
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("TYPE", t);
                    bundle.putString("NAME", editText.getText().toString());
                    bundle.putInt("ROW", RowSpinner.getValue());
                    bundle.putInt("COL", ColSpinner.getValue());
                    Intent intent;
                    intent = new Intent(getApplication(), FillingMatrix.class);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, RESCODE);

                } else {
                    if (((GlobalValues) getApplication()).hasProfainity(editText.getText().toString())) {
                        editText.setError(getString(R.string.Warning7));
                        editText.requestFocus();
                    }
                }


            }

            private boolean NoError() {
                if (editText.getText().toString().isEmpty()) {
                    editText.setError(getString(R.string.Warning1));
                    editText.requestFocus();
                    return false;
                }
                if ((Typespinner.getSelectedItem().toString().equals("Identity") ||
                        Typespinner.getSelectedItem().toString().equals("Diagonal")) &&
                        (RowSpinner.getValue() != ColSpinner.getValue())) {
                    Toast.makeText(getApplicationContext(), R.string.NotSquare, Toast.LENGTH_LONG).show();
                    return false;
                }
                if (editText.getText().toString().contains("+") ||
                        editText.getText().toString().contains("x") || editText.getText().toString().contains("-")) {
                    editText.setError(getString(R.string.Warning13));
                    editText.requestFocus();
                    return false;
                }
                return !((GlobalValues) getApplication()).hasProfainity(editText.getText().toString());

            }


        });


    }

    protected void onActivityResult(int requestcode, int resultCode, Intent data) {
        super.onActivityResult(resultCode, resultCode, data);
        if (resultCode == RESCODE) {
            setResult(1, data);
            finish();
        }

    }

    public Type TypeFromString(String s) {
        switch (s) {
            case "Normal":
                return Type.Normal;
            case "Null":
                return Type.Null;
            case "Diagonal":
                return Type.Diagonal;
            case "Identity":
                return Type.Identity;
        }
        return null;
    }
}

