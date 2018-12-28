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

package com.softminds.matrixcalculator.base_fragments;


import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridLayout;

import com.softminds.matrixcalculator.GlobalValues;
import com.softminds.matrixcalculator.MatrixV2;
import com.softminds.matrixcalculator.R;

import java.text.DecimalFormat;


public class EditFragment extends Fragment {

    View RootView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View V = inflater.inflate(R.layout.fragment_edit, container, false);

        CardView cardView = V.findViewById(R.id.EditMatrixCard);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String string = sharedPreferences.getString("ELEVATE_AMOUNT", "4");
        String string2 = sharedPreferences.getString("CARD_CHANGE_KEY", "#bdbdbd");

        cardView.setCardElevation(Integer.parseInt(string));

        CardView.LayoutParams params1 = new CardView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        //noinspection ConstantConditions
        int index = getArguments().getInt("INDEX");
        //noinspection ConstantConditions
        MatrixV2 m = ((GlobalValues) getActivity().getApplication()).GetCompleteList().get(index);

        GridLayout gridLayout = new GridLayout(getContext());
        gridLayout.setRowCount(m.getNumberOfRows());
        gridLayout.setColumnCount(m.getNumberOfCols());
        for (int i = 0; i < m.getNumberOfRows(); i++) {
            for (int j = 0; j < m.getNumberOfCols(); j++) {
                EditText editText = new EditText(getContext());
                editText.setId(i * 10 + j);
                editText.setBackgroundColor(Color.parseColor(string2));
                editText.setGravity(Gravity.CENTER);
                if (!PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("DECIMAL_USE", true)) {
                    editText.setInputType(InputType.TYPE_CLASS_NUMBER
                            | InputType.TYPE_NUMBER_FLAG_SIGNED);
                } else {
                    editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL
                            | InputType.TYPE_NUMBER_FLAG_SIGNED);
                }
                editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(getLength())});
                if (!PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("SMART_FIT_KEY", false)) {
                    editText.setWidth(ConvertTopx(62));
                    editText.setTextSize(SizeReturner(3, 3, PreferenceManager.getDefaultSharedPreferences(getContext()).
                            getBoolean("EXTRA_SMALL_FONT", false)));
                } else {
                    editText.setWidth(ConvertTopx(CalculatedWidth(m.getNumberOfCols())));
                    editText.setTextSize(SizeReturner(m.getNumberOfRows(), m.getNumberOfCols(),
                            PreferenceManager.getDefaultSharedPreferences(getContext()).
                                    getBoolean("EXTRA_SMALL_FONT", false)));
                }
                editText.setText(SafeSubString(GetText(m.getElementOf(i, j)), getLength()));
                editText.setSingleLine();
                GridLayout.Spec Row = GridLayout.spec(i, 1);
                GridLayout.Spec Col = GridLayout.spec(j, 1);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams(Row, Col);
                params.leftMargin = params.topMargin = params.bottomMargin = params.rightMargin = getResources().getDimensionPixelOffset(R.dimen.border_width);
                gridLayout.addView(editText, params);
            }
        }
        gridLayout.setLayoutParams(params1);
        cardView.addView(gridLayout);
        RootView = V;
        return V;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        int number;
        //noinspection ConstantConditions
        int index = getArguments().getInt("INDEX");
        try {
            //noinspection ConstantConditions
            if (!((GlobalValues) getActivity().getApplication()).GetCompleteList().isEmpty()) {
                number = ((GlobalValues) getActivity().getApplication()).
                        GetCompleteList().get(index).getNumberOfRows();
                for (int i = 0; i < number; i++)
                    for (int j = 0; j < ((GlobalValues) getActivity().getApplication()).
                            GetCompleteList().get(index).getNumberOfCols(); j++) {
                        EditText editText = RootView.findViewById(i * 10 + j);
                        if (!editText.getText().toString().isEmpty()) {
                            ((GlobalValues) getActivity().getApplication()).
                                    GetCompleteList().get(index).
                                    setElementOf(((Float.parseFloat(((EditText)
                                            RootView.findViewById(i * 10 + j)).
                                            getText().toString()))), i, j);
                        } else
                            ((GlobalValues) getActivity().getApplication()).GetCompleteList().get(index).setElementOf(0f, i, j);
                    }

            }
            ((GlobalValues) getActivity().getApplication()).GetCompleteList().get(index).setType();
            ((GlobalValues) getActivity().getApplication()).matrixAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("ExceptionRaised", "RootView to Edit Text is Null");
        }

    }

    public int getLength() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        boolean v = preferences.getBoolean("EXTRA_SMALL_FONT", false);
        if (v)
            return 8;
        else
            return 6;
    }

    public int CalculatedWidth(int a) {
        switch (a) {
            case 1:
                return 70;
            case 2:
                return 65;
            case 3:
                return 60;
            case 4:
                return 55;
            case 5:
                return 50;
            case 6:
                return 45;
            case 7:
                return 42;
            case 8:
                return 40;
            case 9:
                return 38;

        }
        return 0;
    }

    public int SizeReturner(int r, int c, boolean b) {
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
            return SizeReturner(r, c, false) - 2;
        }
        return 0;
    }

    public String SafeSubString(String s, int MaxLength) {
        if (!TextUtils.isEmpty(s)) {
            if (s.length() >= MaxLength) {
                return s.substring(0, MaxLength);
            }
        }
        return s;
    }

    private int ConvertTopx(float dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return ((int) (dp * ((float) metrics.densityDpi) / DisplayMetrics.DENSITY_DEFAULT));

    }

    private String GetText(double res) {

        if (!PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("DECIMAL_USE", true)) {
            DecimalFormat decimalFormat = new DecimalFormat("###############");
            return decimalFormat.format(res);
        } else {
            switch (Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(getContext()).getString("ROUNDIND_INFO", "0"))) {
                case 0:
                    DecimalFormat zeroth = new DecimalFormat("########.######");
                    return zeroth.format(res);
                case 1:
                    DecimalFormat single = new DecimalFormat("########.#");
                    return single.format(res);
                case 2:
                    DecimalFormat Double = new DecimalFormat("########.##");
                    return Double.format(res);
                case 3:
                    DecimalFormat triple = new DecimalFormat("########.###");
                    return triple.format(res);
                default:
                    DecimalFormat fourth = new DecimalFormat("########.#####");
                    return fourth.format(res);
            }
        }
    }

    public void RevertChanges() {
        //noinspection ConstantConditions
        MatrixV2 m = ((GlobalValues) getActivity().getApplication()).current_editing;
        if (m != null) {
            for (int i = 0; i < m.getNumberOfRows(); i++) {
                for (int j = 0; j < m.getNumberOfCols(); j++) {
                    EditText e = RootView.findViewById(i * 10 + j);
                    e.setText(GetText(m.getElementOf(i, j)));
                }
            }
        }
    }
}
