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
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import com.softminds.matrixcalculator.GlobalValues;
import com.softminds.matrixcalculator.R;

import java.text.DecimalFormat;


@SuppressWarnings("ConstantConditions")
public class ViewMatrixFragment extends Fragment {


    public ViewMatrixFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int index = getArguments().getInt("INDEX");

        View v = inflater.inflate(R.layout.view_matrix_frag, container, false);
        CardView cardView = v.findViewById(R.id.DynamicCardView);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String string = sharedPreferences.getString("ELEVATE_AMOUNT", "4");
        String string2 = sharedPreferences.getString("CARD_CHANGE_KEY", "#bdbdbd");

        cardView.setCardElevation(Integer.parseInt(string));

        final int border = getResources().getDimensionPixelOffset(R.dimen.border_width);

        CardView.LayoutParams params1 = new CardView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        cardView.setUseCompatPadding(true);

        GridLayout gridLayout = new GridLayout(getContext());
        int rows = ((GlobalValues) getActivity().getApplication()).GetCompleteList().get(index).getNumberOfRows();
        int cols = ((GlobalValues) getActivity().getApplication()).GetCompleteList().get(index).getNumberOfCols();

        gridLayout.setRowCount(rows);
        gridLayout.setColumnCount(cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                TextView textView = new TextView(getContext());
                textView.setGravity(Gravity.CENTER);
                textView.setBackgroundColor(Color.parseColor(string2));
                textView.setText(SafeSubString(GetText(((GlobalValues) getActivity().getApplication()).GetCompleteList().get(index).getElementOf(i, j)), getLength()));
                textView.setWidth(CalculatedWidth(cols));
                textView.setTextSize(SizeReturner(rows, cols, PreferenceManager.getDefaultSharedPreferences(getContext()).
                        getBoolean("EXTRA_SMALL_FONT", false)));
                textView.setHeight(CalculatedHeight(rows));
                GridLayout.Spec Row = GridLayout.spec(i, 1);
                GridLayout.Spec Col = GridLayout.spec(j, 1);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams(Row, Col);
                params.leftMargin = params.topMargin = params.bottomMargin = params.rightMargin = border;
                gridLayout.addView(textView, params);
            }
        }
        gridLayout.setLayoutParams(params1);
        cardView.addView(gridLayout);


        // Inflate the layout for this fragment
        return v;
    }

    public int CalculatedHeight(int a) {
        switch (a) {
            case 1:
                return 155;
            case 2:
                return 135;
            case 3:
                return 125;
            case 4:
                return 115;
            case 5:
                return 105;
            case 6:
                return 95;
            case 7:
                return 85;
            case 8:
                return 85;
            case 9:
                return 80;

        }
        return 0;
    }

    public int CalculatedWidth(int a) {
        switch (a) {
            case 1:
                return 150;
            case 2:
                return 130;
            case 3:
                return 120;
            case 4:
                return 110;
            case 5:
                return 100;
            case 6:
                return 90;
            case 7:
                return 80;
            case 8:
                return 80;
            case 9:
                return 74;

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

    public String SafeSubString(String s, int MaxLength) {
        if (!TextUtils.isEmpty(s)) {
            if (s.length() >= MaxLength) {
                return s.substring(0, MaxLength);
            }
        }
        return s;
    }

    public int getLength() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        boolean v = preferences.getBoolean("EXTRA_SMALL_FONT", false);
        if (v)
            return 8;
        else
            return 6;
    }

    private String GetText(double res) {

        if (!PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("DECIMAL_USE", true)) {
            DecimalFormat decimalFormat = new DecimalFormat("###############");
            return decimalFormat.format(res);
        } else {
            switch (Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(getContext()).getString("ROUNDIND_INFO", "0"))) {
                case 0:
                    return String.valueOf(res);
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


}