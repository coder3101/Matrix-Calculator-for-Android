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

package com.softminds.matrixcalculatorpro.base_fragments;


import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridLayout;

import com.softminds.matrixcalculatorpro.base_activities.GlobalValues;
import com.softminds.matrixcalculatorpro.Matrix;
import com.softminds.matrixcalculatorpro.R;

import java.text.DecimalFormat;


public class EditFragment extends Fragment {

    View RootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View V=inflater.inflate(R.layout.fragment_edit, container, false);

        CardView cardView = (CardView) V.findViewById(R.id.EditMatrixCard);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String string=sharedPreferences.getString("ELEVATE_AMOUNT","4");
        String string2=sharedPreferences.getString("CARD_CHANGE_KEY","#bdbdbd");
        boolean SmartFit = sharedPreferences.getBoolean("SMART_FIT_KEY",false);

        cardView.setCardElevation(Integer.parseInt(string));
        cardView.setCardBackgroundColor(Color.parseColor(string2));

        CardView.LayoutParams params1= new CardView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        int index= getArguments().getInt("INDEX");
        Matrix m=((GlobalValues)getActivity().getApplication()).GetCompleteList().get(index);

        GridLayout gridLayout = new GridLayout(getContext());
        gridLayout.setRowCount(m.GetRow());
        gridLayout.setColumnCount(m.GetCol());
        for(int i=0;i<m.GetRow();i++)
        {
            for(int j=0;j<m.GetCol();j++)
            {
                EditText editText = new EditText(getContext());
                editText.setId(i*10+j);
                editText.setGravity(Gravity.CENTER);
                if(!PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("DECIMAL_USE",true)){
                    editText.setInputType(InputType.TYPE_CLASS_NUMBER
                            |InputType.TYPE_NUMBER_FLAG_SIGNED);
                }
                else {
                    editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL
                            | InputType.TYPE_NUMBER_FLAG_SIGNED);
                }
                editText.setFilters(new InputFilter[] { new InputFilter.LengthFilter(getLenght())});
                if(SmartFit) {
                    editText.setWidth(ConvertTopx(CalculatedWidth(m.GetCol())));
                    editText.setTextSize(SizeReturner(m.GetRow(), m.GetCol(),
                            PreferenceManager.getDefaultSharedPreferences(getContext()).
                                    getBoolean("EXTRA_SMALL_FONT", false)));
                }else{
                    editText.setWidth(ConvertTopx(62));
                    editText.setTextSize(SizeReturner(3,3,PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("EXTRA_SMALL_FONT", false)));
                }
                editText.setText(SafeSubString( GetText(m.GetElementof(i,j)),getLenght()));
                editText.setSingleLine();
                GridLayout.Spec Row = GridLayout.spec(i,1);
                GridLayout.Spec Col = GridLayout.spec(j,1);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams(Row,Col);
                gridLayout.addView(editText,params);
            }
        }
        gridLayout.setLayoutParams(params1);
        cardView.addView(gridLayout);
        RootView=V;
        return V;
    }
    @Override
    public void onDetach(){
        super.onDetach();
        int number;
        int index= getArguments().getInt("INDEX");
        if(!((GlobalValues)getActivity().getApplication()).GetCompleteList().isEmpty()) {
            number = ((GlobalValues) getActivity().getApplication()).
                    GetCompleteList().get(index).GetRow();
            for (int i = 0; i < number; i++)
                for (int j = 0; j < ((GlobalValues) getActivity().getApplication()).
                        GetCompleteList().get(index).GetCol(); j++) {
                    if (!((EditText) RootView.findViewById(i * 10 + j)).getText().toString().isEmpty()) {
                        ((GlobalValues) getActivity().getApplication()).
                                GetCompleteList().get(index).
                                SetElementof(((Float.parseFloat(((EditText)
                                        RootView.findViewById(i * 10 + j)).
                                        getText().toString()))), i, j);
                    } else
                        ((GlobalValues)getActivity().getApplication()).GetCompleteList().get(index).SetElementof(0f,i,j);
                }
            ((GlobalValues)getActivity().getApplication()).GetCompleteList().get(index).SetType();
            ((GlobalValues)getActivity().getApplication()).matrixAdapter.notifyDataSetChanged();
        }

    }

    public void RevertChanges(){
        Matrix matrix = ((GlobalValues)getActivity().getApplication()).current_editing;
        for(int i=0;i<matrix.GetRow();i++)
            for(int j=0;j<matrix.GetCol();j++)
                ((EditText) RootView.findViewById(i * 10 + j)).
                        setText(GetText(matrix.GetElementof(i,j)));

    }
    private int getLenght()
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        boolean v=preferences.getBoolean("EXTRA_SMALL_FONT",false);
        if(v)
        {
            if(!PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("SMART_FIT_KEY",false))
                return 7;
            else
                return 8;
        }
        else
            return 6;
    }
    private int CalculatedWidth(int a)
    {
        switch (a)
        {
            case 1 : return 72;
            case 2 : return 67;
            case 3 : return 62;
            case 4 : return 57;
            case 5 : return 52;
            case 6 : return 47;
            case 7 : return 44;
            case 8 : return 42;
            case 9 : return 40;

        }
        return 0;
    }
    private int SizeReturner(int r, int c,boolean b)
    {
            if (!b) {
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
            } else {
                return SizeReturner(r, c, !b) -2;
            }

        return 0;
    }
    private String SafeSubString(String s, int MaxLength)
    {
        if(!TextUtils.isEmpty(s))
        {
            if(s.length()>=MaxLength){
                return s.substring(0,MaxLength);
            }
        }
        return s;
    }
    private int ConvertTopx(float dp){
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return ((int)(dp * ((float)metrics.densityDpi) / DisplayMetrics.DENSITY_DEFAULT));

    }
    private String GetText(float res) {
        if (!PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("DECIMAL_USE", true)) {
            DecimalFormat decimalFormat = new DecimalFormat("###############");
            return decimalFormat.format(res);
        } else {
            return String.valueOf(res);
        }
    }


}
