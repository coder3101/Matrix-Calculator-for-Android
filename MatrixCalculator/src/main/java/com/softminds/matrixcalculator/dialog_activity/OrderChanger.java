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
import com.softminds.matrixcalculator.Matrix;
import com.softminds.matrixcalculator.R;
import com.softminds.matrixcalculator.Type;

public class OrderChanger extends AppCompatActivity {

    final int ERROR = -1; //This is the Error code if Index Does not Corresponds to any Matrix
    final int SUCCESS = 12; //this is the Success Code as set by Previous Activity


    @Override //Override the Native On create Method
    protected void onCreate(Bundle savedInstanceState) {

        final int MatrixPosition = getIntent().getIntExtra("INDEX_OF_SELECTED_MATRIX", -1);
        //Set the Value Recieved from Last Activity, The Last Activity Sends the Index of Matrix to bring Changes

        final int OriginalRows = ((GlobalValues) getApplication()).GetCompleteList().get(MatrixPosition).GetRow();
        final int OriginalCols = ((GlobalValues) getApplication()).GetCompleteList().get(MatrixPosition).GetCol();
        //Set the Original Values of Rows and Cols of the in the Matrix Corresponding to the recieved index


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isDark = preferences.getBoolean("DARK_THEME_KEY", false); //Get the Preferences to set the new theme
        if (isDark)
            setTheme(R.style.AppThemeDarkDialog); //Set DarkTheme if Settings has Dark button Pressed
        else                                       //else
            setTheme(R.style.AppThemeDialog);     //Set the Normal theme


        super.onCreate(savedInstanceState);  //call the Super Method to Initialize the Parent Constructors


        setContentView(R.layout.order_changer); //Set the UserInterface Layout


        //Grab the References
        final NumberPicker ChangedRow = findViewById(R.id.RowChangedOrder);
        final NumberPicker ChangedCol = findViewById(R.id.ColChangedOrder);


        //Initilize with Maximum Value and Minimum Value
        ChangedCol.setMaxValue(9);
        ChangedCol.setMinValue(1);
        ChangedRow.setMaxValue(9);
        ChangedRow.setMinValue(1);

        //if a Error Index has been passed then note that to log
        if (MatrixPosition == -1) {
            Log.d("Index Error", "The Matrix index could not be asserted");
            setResult(ERROR);
            finish();

        }

        //Set the Current Index on NumberPicker
        ChangedCol.setValue(((GlobalValues) getApplication()) //Grab the Value from Global Context
                .GetCompleteList().get(MatrixPosition).GetCol());
        ChangedRow.setValue(((GlobalValues) getApplication()) //Grab the Value from Global Context
                .GetCompleteList().get(MatrixPosition).GetRow());
        //All NumberPicker Values have been Initilized and Set up.

        //Grab button References
        Button CommitChanges = findViewById(R.id.CommitChanges);
        Button CancelChanges = findViewById(R.id.NoCommitChanges);

        //setting the onclick listner to the Confirm button
        CommitChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Now Check the If MatrixSize is Reduced or Increased
                boolean RowIncreased = (ChangedRow.getValue() > OriginalRows);
                boolean ColIncreased = (ChangedCol.getValue() > OriginalCols);


                //User Increased the Row or Column, then we need to Create a new Matrix because we cannot increase the size
                //as increasing the size will cause the OutofBoundException and result in Crash of the Application
                //so we are about to make a new matrix and clone the values into it
                if (RowIncreased || ColIncreased) {
                    Type type = ((GlobalValues) getApplication()).GetCompleteList().get(MatrixPosition).GetType();
                    //get the Type of Original Matrix
                    Matrix NewReplacable = new Matrix(ChangedRow.getValue(), ChangedCol.getValue(), type);
                    //New Dimensional Matrix Created with native type

                    Matrix dummy = ((GlobalValues) getApplication()).GetCompleteList().get(MatrixPosition);
                    //Getting the Clicked Matrix on a Dummy variable to avoid calling it again and again
                    NewReplacable.SetName(dummy.GetName());
                    NewReplacable.SetType(dummy.GetType());
                    //Cloning the Matrix and Setting the Value in such a way that new Matrix Include all the Values of Previous
                    for (int i = 0; i < NewReplacable.GetRow(); i++)
                        for (int j = 0; j < NewReplacable.GetCol(); j++) {
                            if (dummy.GetRow() > i && dummy.GetCol() > j)
                                NewReplacable.SetElementof(dummy.GetElementof(i, j), i, j); //if older matrix order exceeds the
                                //current index, then all remaining values must be Zero on newer one
                            else {
                                NewReplacable.SetElementof(0, i, j);
                            }
                        }

                    //Setting the New Matrix in Place of the older Matrix in List
                    ((GlobalValues) getApplication()).GetCompleteList().set(MatrixPosition, NewReplacable);


                    ((GlobalValues) getApplication()).matrixAdapter.notifyDataSetChanged();
                    //Notify the Adapter about Change in List, So that it can Refresh the MainList to Include new Matrix
                    //and Remove the Last Matrix


                    setResult(SUCCESS); //set the Result Code as Success so
                    // that Last Activity can know that Changes have been made Successfully
                    //and refresh itself to reflect the changes
                    finish();
                } else {
                    //If User has Decreased the Size of this New Matrix, Just Truncate the Matrix
                    ((GlobalValues) getApplication()).GetCompleteList().get(MatrixPosition)
                            .SetCol(ChangedCol.getValue());
                    ((GlobalValues) getApplication()).GetCompleteList().get(MatrixPosition)
                            .SetRow(ChangedRow.getValue());
                    //New Rows and Columns Updated in the Matrix.

                    ((GlobalValues) getApplication()).matrixAdapter.notifyDataSetChanged();
                    //Notify the Adapter about Change in List, So that it can Refresh the MainList to Include new Matrix
                    //and Remove the Last Matrix


                    setResult(SUCCESS);
                    //Notify Last Activity to Refresh the Matrix Order, and display the Truncated one
                    finish();
                    //finally close the this menu
                }

            }
        });

        //setting the onclick listner for Cancel Button
        CancelChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //on Cancel just finish this activity
                finish();
                //No need to set Result as last Activity does not changes if No change is commited
            }
        });

    }
}
