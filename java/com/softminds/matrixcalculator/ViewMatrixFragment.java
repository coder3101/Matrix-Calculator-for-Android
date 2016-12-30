/*
MIT License

Copyright (c) 2016 Ashar Khan

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

package com.softminds.matrixcalculator;


import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;


public class ViewMatrixFragment extends Fragment {


    public ViewMatrixFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int index= getArguments().getInt("INDEX");

        View v=inflater.inflate(R.layout.view_matrix_frag, container, false);
        CardView cardView = (CardView) v.findViewById(R.id.DynamicCardView);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String string=sharedPreferences.getString("ELEVATE_AMOUNT","4");
        String string2=sharedPreferences.getString("CARD_CHANGE_KEY","#bdbdbd");

        cardView.setCardElevation(Integer.parseInt(string));
        cardView.setCardBackgroundColor(Color.parseColor(string2));

        CardView.LayoutParams params1= new CardView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        GridLayout gridLayout = new GridLayout(getContext());
        gridLayout.setRowCount(((GlobalValues)getActivity().getApplication()).GetCompleteList().get(index).GetRow());
        gridLayout.setColumnCount(((GlobalValues)getActivity().getApplication()).GetCompleteList().get(index).GetCol());
        for(int i=0;i<((GlobalValues)getActivity().getApplication()).GetCompleteList().get(index).GetRow();i++)
        {
            for(int j=0;j<((GlobalValues)getActivity().getApplication()).GetCompleteList().get(index).GetCol();j++)
            {
                TextView textView = new TextView(getContext());
                textView.setGravity(Gravity.CENTER);
                textView.setText(SafeSubString( String.valueOf(((GlobalValues)getActivity().getApplication()).GetCompleteList().get(index).GetElementof(i,j)),getLenght()));
                textView.setWidth(CalculatedWidth(((GlobalValues)getActivity().getApplication()).GetCompleteList().get(index).GetCol()));
                textView.setTextSize(SizeReturner(((GlobalValues)getActivity().getApplication()).GetCompleteList().get(index).GetRow(),((GlobalValues)getActivity().getApplication()).GetCompleteList().get(index).GetCol(),
                        PreferenceManager.getDefaultSharedPreferences(getContext()).
                                getBoolean("EXTRA_SMALL_FONT",false)));
                textView.setHeight(CalculatedHeight(((GlobalValues)getActivity().getApplication()).GetCompleteList().get(index).GetRow()));
                GridLayout.Spec Row = GridLayout.spec(i,1);
                GridLayout.Spec Col = GridLayout.spec(j,1);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams(Row,Col);
                gridLayout.addView(textView,params);
            }
        }
        gridLayout.setLayoutParams(params1);
        cardView.addView(gridLayout);



        // Inflate the layout for this fragment
        return v;
    }
    public int CalculatedHeight(int a)
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
    public String SafeSubString(String s, int MaxLength)
    {
        if(!TextUtils.isEmpty(s))
        {
            if(s.length()>=MaxLength){
                return s.substring(0,MaxLength);
            }
        }
        return s;
    }
    public int getLenght()
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        boolean v=preferences.getBoolean("EXTRA_SMALL_FONT",false);
        if(v)
            return 8;
        else
            return 6;
    }

}
