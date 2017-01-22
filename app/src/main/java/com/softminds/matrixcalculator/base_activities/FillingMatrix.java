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
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Toast;

import com.softminds.matrixcalculator.Type;
import com.softminds.matrixcalculator.dialog_activity.CustomValueFiller;
import com.softminds.matrixcalculator.R;
import com.softminds.matrixcalculator.dialog_activity.DialogConfirmation;

import java.util.Random;

public class FillingMatrix extends AppCompatActivity {

    final String CustomValueKey = "com.softminds.matrixCalculator.CUSTOM_VALUE";
    final int RESULT=2;
    static int row,col;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isDark=preferences.getBoolean("DARK_THEME_KEY",false);

        if(isDark)
            setTheme(R.style.AppThemeDark_NoActionBar);
        else
            setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);

         Bundle bundle = getIntent().getExtras();
        col=bundle.getInt("COL");
        row=bundle.getInt("ROW");
        setContentView(R.layout.filler);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarFill);
        setSupportActionBar(toolbar);


        CardView cardView = (CardView) findViewById(R.id.DynamicCard);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String string=sharedPreferences.getString("ELEVATE_AMOUNT","4");
        String string2=sharedPreferences.getString("CARD_CHANGE_KEY","#bdbdbd");

        cardView.setCardElevation(Integer.parseInt(string));
        cardView.setCardBackgroundColor(Color.parseColor(string2));

        CardView.LayoutParams params1= new CardView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        GridLayout gridLayout = new GridLayout(getApplicationContext());
        gridLayout.setRowCount(row);
        gridLayout.setColumnCount(col);
        for(int i=0;i<row;i++)
        {
            for(int j=0;j<col;j++)
            {
                EditText editText = new EditText(getApplication());
                editText.setId(i*10+j);
                editText.setHint("A"+String.valueOf(i+1)+String.valueOf(j+1));
                editText.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL
                        |InputType.TYPE_NUMBER_FLAG_SIGNED);
                editText.setFilters(new InputFilter[] { new InputFilter.LengthFilter(getLenght())});
                editText.setWidth(CalculatedWidth(col));
                editText.setTextSize(SizeReturner(row,col,
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).
                                getBoolean("EXTRA_SMALL_FONT",false)));
                editText.setHeight(CalculatedHeight(row));
                editText.setSingleLine();
                GridLayout.Spec Row = GridLayout.spec(i,1);
                GridLayout.Spec Col = GridLayout.spec(j,1);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams(Row,Col);
                gridLayout.addView(editText,params);
            }
        }
        gridLayout.setLayoutParams(params1);
        cardView.addView(gridLayout);
        MakeType((Type)(getIntent().getExtras().getSerializable("TYPE")));
        if(GetMaximum()<GetMinimum()) {
            final Snackbar  snackbar;
            snackbar =Snackbar.make(findViewById(R.id.filling_matrix), R.string.Warning3, Snackbar.LENGTH_INDEFINITE);
            snackbar.show();
            snackbar.setAction(getString(R.string.action_settings), new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    snackbar.dismiss();
                    Intent intent = new Intent(getApplicationContext(),SettingsTab.class);
                    startActivity(intent);
                    finish();
                }
            });
        }

    }
    public int CalculatedHeight(int a)
    {
        switch (a)
        {
            case 1 : return 165;
            case 2 : return 145;
            case 3 : return 135;
            case 4 : return 125;
            case 5 : return 115;
            case 6 : return 105;
            case 7 : return 95;
            case 8 : return 95;
            case 9 : return 90;

        }
        return 0;
    }
    public int getLenght()
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean v=preferences.getBoolean("EXTRA_SMALL_FONT",false);
        if(v)
            return 8;
        else
            return 6;
    }
    public int CalculatedWidth(int a)
    {
        switch (a)
        {
            case 1 : return 150;
            case 2 : return 130;
            case 3 : return 120;
            case 4 : return 110;
            case 5 : return 100;
            case 6 : return 90;
            case 7 : return 80;
            case 8 : return 80;
            case 9 : return 74;

        }
        return 0;
    }
    public int SizeReturner(int r, int c,boolean b)
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
    @Override
    public void onBackPressed(){
        if(!AllEmpty()){
            Bundle Info = new Bundle();
            int ResultCode=70;
            Info.putInt("MESSAGE",R.string.Warning4);
            Info.putInt("CONFIRM_TEXT",R.string.Yup);
            Info.putInt("RESULT_CODE",ResultCode);
            Info.putInt("CANCEL_TEXT",R.string.CancelCustomFill);
            Intent intent3 = new Intent(getApplicationContext(), DialogConfirmation.class);
            intent3.putExtras(Info);
            startActivityForResult(intent3,ResultCode);
    }
    else
    finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.custom_fill_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.ConfirmCreate :
                if(!MatrixError()){
                    float variable[][]=GetIntoFloat();
                    Bundle bundle=new Bundle();
                    bundle.putAll(getIntent().getExtras());
                    bundle.putSerializable("VALUES",variable);
                    Intent intnt=new Intent();
                    intnt.putExtras(bundle);
                    setResult(0,intnt);
                    finish();
                    return true;
                }
                else
                break;
            case R.id.Input1Empty :
                        EmptyInput(1);
                        return true;
            case R.id.ClearAll:
                ClearAllValues();
                return true;
            case R.id.FillAllZero :
                        EmptyInput(0);
                        return  true;
            case R.id.InputRandom :
                        InputRandomInt();
                        return true;
            case R.id.InputRandomFloat :
                        InputRandomFloat();
                        return true;
            case R.id.FillCustom :
                startActivityForResult(new Intent(getApplicationContext(),CustomValueFiller.class),RESULT);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public boolean MatrixError()
    {
        for(int i=0;i<row;i++)
            for (int j=0;j<col;j++)
            {
                EditText editText= (EditText) findViewById(i*10+j);
                if(editText.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplication(),R.string.Warning2,Toast.LENGTH_SHORT).show();
                    return true;
                }
            }
        return false;
    }
    public float[][] GetIntoFloat()
    {
        float var[][] = new float[row][col];
        for(int i=0;i<row;i++)
            for(int j=0;j<col;j++) {
                EditText editText = (EditText) findViewById(i*10+j);
                String p = editText.getText().toString();
                var[i][j]=Float.parseFloat(p);
            }
        return var;
    }
    public void EmptyInput(float p) {
        for (int i = 0; i < row; i++)
            for (int j = 0; j < col; j++) {
                EditText editText = (EditText) findViewById(i * 10 + j);
                if (editText.getText().toString().isEmpty()) {
                    editText.setText(String.valueOf(p));
                }
            }
    }
    @Override
    protected void onActivityResult(int requestcode,int resultCode,Intent data)
    {
        super.onActivityResult(requestcode,resultCode,data);
        if(resultCode==RESULT)
        {
            if(data.getFloatExtra(CustomValueKey,0)!=0)
            EmptyInput(data.getFloatExtra(CustomValueKey,0));
        }
        if(resultCode ==70) //When User Presses, back key
        {
            finish();
        }
    }
    public void MakeType(Type type)
    {
        switch (type)
        {
            case Null:
                for (int i = 0; i < row; i++)
                    for (int j = 0; j < col; j++) {
                        EditText editText = (EditText) findViewById(i * 10 + j);
                            editText.setText(String.valueOf(0.0));
                    }
                float variable[][]=GetIntoFloat();
                Bundle bundle=new Bundle();
                bundle.putAll(getIntent().getExtras());
                bundle.putSerializable("VALUES",variable);
                Intent intnt=new Intent();
                intnt.putExtras(bundle);
                setResult(0,intnt);
                finish();

                break;
            case Diagonal:
                for (int i = 0; i < row; i++)
                    for (int j = 0; j < col; j++) {
                        EditText editText = (EditText) findViewById(i * 10 + j);
                        if (i!=j) {
                            editText.setText(String.valueOf(0.0));
                        }
                    }
                break;
            case Identity:
                for (int i = 0; i < row; i++)
                    for (int j = 0; j < col; j++) {
                        EditText editText = (EditText) findViewById(i * 10 + j);
                        if(i==j)
                            editText.setText(String.valueOf(1.0));
                        else
                            editText.setText(String.valueOf(0.0));
                    }
                float vari[][]=GetIntoFloat();
                Bundle bundle2=new Bundle();
                bundle2.putAll(getIntent().getExtras());
                bundle2.putSerializable("VALUES",vari);
                Intent intn=new Intent();
                intn.putExtras(bundle2);
                setResult(0,intn);
                finish();
                break;
        }
    }
    public void InputRandomInt()
    {
        for (int i = 0; i < row; i++)
            for (int j = 0; j < col; j++) {
                EditText editText = (EditText) findViewById(i * 10 + j);
                    if(!NegativeAllowed())
                        editText.setText(String.valueOf(new Random().nextInt(GetMaximum()+1-GetMinimum())+GetMinimum()));
                    else
                        editText.setText(String.valueOf(new Random().nextInt(GetMaximum()+1-((-1)*GetMaximum()))+(-1)*GetMaximum()));
            }
    }
    public void InputRandomFloat()
    {
        for (int i = 0; i < row; i++)
            for (int j = 0; j < col; j++) {
                EditText editText = (EditText) findViewById(i * 10 + j);
                if(!NegativeAllowed())
                editText.setText(String.valueOf(new Random().nextFloat()+new Random().nextInt(GetMaximum()-GetMinimum())+GetMinimum()));
                else
                    editText.setText(String.valueOf(new Random().nextFloat()+new Random().nextInt(GetMaximum()+1-((-1)*GetMaximum()))+(-1)*GetMaximum()));
            }
    }
    public boolean NegativeAllowed()
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        return (sharedPreferences.getBoolean("SIGNED_RANDOM",false));
    }
    public int GetMaximum()
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String  b=sharedPreferences.getString("MAX_INT","100");
        return Integer.parseInt(b);
    }
    public int GetMinimum()
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String a=sharedPreferences.getString("MIN_INT_KEY","0");
        return Integer.parseInt(a);
    }
    public void ClearAllValues()
    {
        for (int i = 0; i < row; i++)
            for (int j = 0; j < col; j++) {
                EditText editText = (EditText) findViewById(i * 10 + j);
                editText.setText("");
            }
    }
    public boolean AllEmpty()
    {
        for (int i = 0; i < row; i++)
            for (int j = 0; j < col; j++) {
                EditText editText = (EditText) findViewById(i * 10 + j);
                if (!editText.getText().toString().isEmpty())
                    return false;

            }
        return true;
    }

}
