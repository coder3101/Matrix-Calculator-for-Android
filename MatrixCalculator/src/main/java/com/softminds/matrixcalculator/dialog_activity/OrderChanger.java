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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import com.softminds.matrixcalculator.GlobalValues;
import com.softminds.matrixcalculator.MatrixV2;
import com.softminds.matrixcalculator.R;
import com.softminds.matrixcalculator.Type;

public class OrderChanger extends AppCompatActivity {

    final int ERROR = -1;
    final int SUCCESS = 12;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final int MatrixPosition = getIntent().getIntExtra("INDEX_OF_SELECTED_MATRIX", -1);

        final int OriginalRows = ((GlobalValues) getApplication()).GetCompleteList().get(MatrixPosition).getNumberOfRows();
        final int OriginalCols = ((GlobalValues) getApplication()).GetCompleteList().get(MatrixPosition).getNumberOfCols();


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isDark = preferences.getBoolean("DARK_THEME_KEY", false); //Get the Preferences to set the new theme
        if (isDark)
            setTheme(R.style.AppThemeDarkDialog);
        else
            setTheme(R.style.AppThemeDialog);


        super.onCreate(savedInstanceState);


        setContentView(R.layout.order_changer);

        final NumberPicker ChangedRow = findViewById(R.id.RowChangedOrder);
        final NumberPicker ChangedCol = findViewById(R.id.ColChangedOrder);

        ChangedCol.setMaxValue(9);
        ChangedCol.setMinValue(1);
        ChangedRow.setMaxValue(9);
        ChangedRow.setMinValue(1);

        if (MatrixPosition == -1) {
            Log.d("Index Error", "The MatrixV2 index could not be asserted");
            setResult(ERROR);
            finish();
        }

        ChangedCol.setValue(((GlobalValues) getApplication()) //Grab the Value from Global Context
                .GetCompleteList().get(MatrixPosition).getNumberOfCols());
        ChangedRow.setValue(((GlobalValues) getApplication()) //Grab the Value from Global Context
                .GetCompleteList().get(MatrixPosition).getNumberOfRows());

        Button CommitChanges = findViewById(R.id.CommitChanges);
        Button CancelChanges = findViewById(R.id.NoCommitChanges);

        CommitChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((GlobalValues) getApplication()).GetCompleteList().get(MatrixPosition)
                        .updateCols(ChangedCol.getValue());
                ((GlobalValues) getApplication()).GetCompleteList().get(MatrixPosition)
                        .updateRows(ChangedRow.getValue());

                ((GlobalValues) getApplication()).matrixAdapter.notifyDataSetChanged();

                setResult(SUCCESS);
                finish();

            }
        });

        CancelChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
