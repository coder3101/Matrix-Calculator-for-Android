package com.softminds.matrixcalculatorpro.base_fragments;


import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.widget.Toast;

import com.softminds.matrixcalculatorpro.R;
import com.softminds.matrixcalculatorpro.base_activities.GlobalValues;

import java.lang.ref.WeakReference;
import java.text.DecimalFormat;

public class MinorChooserFragment extends Fragment {

    private static class myhandler extends Handler{
        private WeakReference<MinorChooserFragment> minorChooserFragmentWeakReference;
        private myhandler(MinorChooserFragment minorChooserFragment){
            minorChooserFragmentWeakReference = new WeakReference<>(minorChooserFragment);
        }
        @Override
        public void handleMessage(Message message){
           MinorChooserFragment minorChooserFragment = minorChooserFragmentWeakReference.get();
            Toast.makeText(minorChooserFragment.getContext(),"Minor of "+
                    String.valueOf(message.getData().getInt("REX")+1) + " , " +
                    String.valueOf(message.getData().getInt("REY")+1)+ " is : "+
                    String.valueOf(message.getData().getFloat("VALUE")),Toast.LENGTH_LONG).show();
        }
    }

    myhandler handler = new myhandler(this);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       final int index= getArguments().getInt("INDEX");

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
                final TextView textView = new TextView(getContext());
                textView.setGravity(Gravity.CENTER);
                final int rex = i;
                final int rey = j;
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Runnable runnable = new Runnable() {
                            @Override
                            public void run() {
                                float res= ((GlobalValues)getActivity().getApplication()).GetCompleteList().get(index).GetMinor(rex,rey);
                                Message message = new Message();
                                Bundle bundle = new Bundle();
                                bundle.putInt("REX",rex);
                                bundle.putInt("REY",rey);
                                bundle.putFloat("VALUE",res);
                                message.setData(bundle);
                                handler.sendMessage(message);
                            }
                        };
                        Thread thread = new Thread(runnable);
                        thread.start();
                    }
                });
                textView.setText(SafeSubString( GetText(((GlobalValues)getActivity().getApplication()).GetCompleteList().get(index).GetElementof(i,j)),getLenght()));
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
            return 9;
        else
            return 6;
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
