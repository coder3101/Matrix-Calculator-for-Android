package com.softminds.matrixcalculator;


import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditFragment extends Fragment {


    public EditFragment() {
        // Required empty public constructor
    }

    View RootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View V=inflater.inflate(R.layout.fragment_edit, container, false);

        CardView cardView = (CardView) V.findViewById(R.id.EditMatrixCard);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String string=sharedPreferences.getString("ELEVATE_AMOUNT","4");
        String string2=sharedPreferences.getString("CARD_CHANGE_KEY","#bdbdbd");

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
                editText.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL
                        |InputType.TYPE_NUMBER_FLAG_SIGNED);
                editText.setFilters(new InputFilter[] { new InputFilter.LengthFilter(getLenght())});
                editText.setWidth(CalculatedWidth(m.GetCol()));
                editText.setTextSize(SizeReturner(m.GetRow(),m.GetCol(),
                        PreferenceManager.getDefaultSharedPreferences(getContext()).
                                getBoolean("EXTRA_SMALL_FONT",false)));
                editText.setHeight(CalculatedHeight(m.GetRow()));
                editText.setText(SafeSubString( String.valueOf(m.GetElementof(i,j)),getLenght()));
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
        int index= getArguments().getInt("INDEX");
        for(int i=0;i<((GlobalValues)getActivity().getApplication()).
                GetCompleteList().get(index).GetRow();i++)
            for(int j=0;j<((GlobalValues)getActivity().getApplication()).
                    GetCompleteList().get(index).GetCol();j++)
            {
                if(RootView.findViewById(i*10+j)!=null)
                {
                    ((GlobalValues)getActivity().getApplication()).
                            GetCompleteList().get(index).
                            SetElementof(((Float.parseFloat(((EditText)
                            RootView.findViewById(i * 10 + j)).
                                    getText().toString()))),i,j);
                }
                else
                    Log.d("Null View ", "true");
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
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
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


}
