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
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.softminds.matrixcalculator.MainActivity;
import com.softminds.matrixcalculator.Matrix;
import com.softminds.matrixcalculator.R;


import java.text.DecimalFormat;

public class ShowResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isDark=preferences.getBoolean("DARK_THEME_KEY",false);
        if(isDark)
            setTheme(R.style.AppThemeDark);
        else
            setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_layout);

        if(preferences.getBoolean("AUTO_TOAST_ENABLER",false)){
            Toast.makeText(getApplicationContext(),"Result Calculated",Toast.LENGTH_SHORT).show();
        }

       if(!((GlobalValues)getApplication()).DonationKeyFound()) {
           AdView mAdView = (AdView) findViewById(R.id.adViewResult);
           AdRequest adRequest = new AdRequest.Builder().build();
           mAdView.loadAd(adRequest);
       }
       else{
           CardView cd = (CardView) findViewById(R.id.AddCardResult);
           ((ViewGroup)cd.getParent()).removeView(cd);
       }

        CardView cardView = (CardView) findViewById(R.id.DynamicCard2);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String string=sharedPreferences.getString("ELEVATE_AMOUNT","4");
        String string2=sharedPreferences.getString("CARD_CHANGE_KEY","#bdbdbd");

        cardView.setCardElevation(Integer.parseInt(string));
        cardView.setCardBackgroundColor(Color.parseColor(string2));

        CardView.LayoutParams params1= new CardView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        GridLayout gridLayout = new GridLayout(getApplicationContext());
        gridLayout.setRowCount(getIntent().getExtras().getInt("ROW",0));
        gridLayout.setColumnCount(getIntent().getExtras().getInt("COL",0));
        float var[][] = (float[][]) getIntent().getExtras().getSerializable("VALUES");
        for(int i=0;i<getIntent().getExtras().getInt("ROW",0);i++)
        {
            for(int j=0;j<getIntent().getExtras().getInt("COL",0);j++)
            {
                TextView textView = new TextView(getApplicationContext());
                textView.setGravity(Gravity.CENTER);
                try {
                    textView.setText("   " + GetText(var[i][j]) + "   ");
                }catch (Exception e){
                    e.printStackTrace();
                    Log.d("Element","Element in Matrix is Null");
                }
                textView.setTextSize(SizeReturner(getIntent().getExtras().getInt("ROW",0),getIntent().getExtras().getInt("COL",0),
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).
                                getBoolean("EXTRA_SMALL_FONT",false)));
                textView.setHeight(CalculatedHeight(getIntent().getExtras().getInt("ROW",0)));
                GridLayout.Spec Row = GridLayout.spec(i,1);
                GridLayout.Spec Col = GridLayout.spec(j,1);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams(Row,Col);
                gridLayout.addView(textView,params);
            }
        }
        gridLayout.setLayoutParams(params1);
        cardView.addView(gridLayout);
        if(getIntent().getFloatExtra("DETERMINANT_FOR_INVERSE",0.0f) != 0.0f){
            TextView textView = (TextView) findViewById(R.id.TextContainer);
            String val = "1 / " + GetText(getIntent().getFloatExtra("DETERMINANT_FOR_INVERSE",0.0f));
            textView.setText(val);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.result_actionbar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.SaveResult :

                if(((GlobalValues)getApplication()).CanCreateVariable()) {
                    if (AnyExponents(((float[][]) getIntent().getExtras().getSerializable("VALUES")),
                            (getIntent().getExtras().getInt("ROW", 0)), (getIntent().getExtras().getInt("COL", 0))) ||
                            !((TextView)findViewById(R.id.TextContainer)).getText().toString().isEmpty()){

                        if (AnyExponents(((float[][]) getIntent().getExtras().getSerializable("VALUES")),
                                (getIntent().getExtras().getInt("ROW", 0)), (getIntent().getExtras().getInt("COL", 0))))
                            Toast.makeText(getApplicationContext(), R.string.CannotSave, Toast.LENGTH_SHORT).show();

                        if(!((TextView)findViewById(R.id.TextContainer)).getText().toString().isEmpty())
                            Toast.makeText(getApplicationContext(), R.string.CannotSave2, Toast.LENGTH_SHORT).show();

                    } else {
                        Matrix matrix = new Matrix();
                        matrix.SetFromBundle(getIntent().getExtras());
                        String auto_name = "Result " + String.valueOf(((GlobalValues) getApplication()).AutoSaved);
                        matrix.SetName(auto_name);
                        matrix.SetType();
                        ((GlobalValues) getApplication()).AddResultToGlobal(matrix);
                        Toast.makeText(getApplicationContext(), (getString(R.string.SavedAs) + " " + auto_name), Toast.LENGTH_SHORT).show();
                        Intent home = new Intent(getApplication(), MainActivity.class);
                        home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //clear all the activities on top of home.
                        startActivity(home);
                        finish();
                    }

                    return true;
                }
                else {
                    if (!((GlobalValues) getApplication()).AdLoaded)
                        Toast.makeText(getApplication(), R.string.ToAddMoreTurnData, Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getApplication(), R.string.LimitExceeds, Toast.LENGTH_SHORT).show();
                }
        }
        return super.onOptionsItemSelected(item);
    }


   private int CalculatedHeight(int a)
    {
        switch (a)
        {
            case 1 : return 155;
            case 2 : return 135;
            case 3 : return 125;
            case 4 : return 115;
            case 5 : return 105;
            case 6 : return 95;
            case 7 : return 85;
            case 8 : return 85;
            case 9 : return 80;

        }
        return 0;
    }
    private int SizeReturner(int r, int c,boolean b)
    {
        if(!b) {
            if (r > c) {
                switch (r) {
                    case 1:
                        return 18;
                    case 2:
                        return 17;
                    case 3:
                        return 15;
                    case 4:
                        return 13;
                    case 5:
                        return 12;
                    case 6:
                        return 11;
                    case 7:
                        return 10;
                    case 8:
                        return 10;
                    case 9:
                        return 9;
                }
            } else {
                switch (c) {
                    case 1:
                        return 18;
                    case 2:
                        return 17;
                    case 3:
                        return 15;
                    case 4:
                        return 13;
                    case 5:
                        return 12;
                    case 6:
                        return 11;
                    case 7:
                        return 10;
                    case 8:
                        return 10;
                    case 9:
                        return 9;
                }
            }
        }
        else
        {
            if (r > c) {
                switch (r) {
                    case 1:
                        return 15;
                    case 2:
                        return 14;
                    case 3:
                        return 12;
                    case 4:
                        return 10;
                    case 5:
                        return 9;
                    case 6:
                        return 8;
                    case 7:
                        return 7;
                    case 8:
                        return 7;
                    case 9:
                        return 6;
                }
            } else {
                switch (c) {
                    case 1:
                        return 15;
                    case 2:
                        return 14;
                    case 3:
                        return 12;
                    case 4:
                        return 10;
                    case 5:
                        return 9;
                    case 6:
                        return 8;
                    case 7:
                        return 7;
                    case 8:
                        return 7;
                    case 9:
                        return 6;
                }
            }
        }

        return 0;
    }

    private boolean AnyExponents(float v[][],int r, int c)
    {
        for(int i=0;i<r;i++)
            for(int j=0;j<c;j++)
            {
                if(Float.toString(v[i][j]).contains("E")||Float.toString(v[i][j]).contains("N") || Float.toString(v[i][j]).contains("Infinity"))
                    return true;
                if(v[i][j]>999999 && !PreferenceManager.getDefaultSharedPreferences(this).getBoolean("DECIMAL_USE",true))
                    return true;
            }
        return  false;
    }
    private String GetText(float res){
        if(!PreferenceManager.getDefaultSharedPreferences(this).getBoolean("DECIMAL_USE",true)){
            DecimalFormat decimalFormat = new DecimalFormat("###############");
            return decimalFormat.format(res);
        }
        else return String.valueOf(res);
    }
}
