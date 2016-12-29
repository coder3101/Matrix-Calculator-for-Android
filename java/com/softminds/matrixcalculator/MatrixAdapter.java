/*
MIT License

Copyright (c) 2016 Ashar Khan

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/package com.softminds.matrixcalculator;

import android.content.Context;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.softminds.matrixcalculator.Matrix;
import com.softminds.matrixcalculator.R;

import java.util.ArrayList;

/**
 * Created by ashar on 27/12/16.
 */

public class MatrixAdapter extends ArrayAdapter<Matrix> {

    public MatrixAdapter(Context context, int t,ArrayList<Matrix> matrices){
        super(context,t,matrices);
    }

    @Override
    @NonNull
    public View getView(int position, View convertView, ViewGroup parent) {
        final Matrix m = getItem(position);

        if(convertView==null)
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.list_layout_fragment,parent,false);

        //Grab References
        TextView Row= ((TextView) convertView.findViewById(R.id.MatrixTitleRow));
        TextView Col= ((TextView) convertView.findViewById(R.id.MatrixTitleCol));
        TextView Naam= ((TextView) convertView.findViewById(R.id.MatrixTitle));
        ImageView icon = ((ImageView)convertView.findViewById(R.id.ImageForMatrix));
        //Set Values
        String r="Row : "+String.valueOf(m.GetRow());
        String c="Column : "+String.valueOf(m.GetCol());
        if(PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("DARK_THEME_KEY",false))
        {
            Row.setTextColor(Color.parseColor("#ffffff"));
            Col.setTextColor(Color.parseColor("#ffffff"));
            Naam.setTextColor(Color.parseColor("#ffffff"));
        }
        Row.setText(r);
        Col.setText(c);
        Naam.setText(m.GetName());
        icon.setImageResource(m.GetDrawable(m.GetType()));
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),m.GetType().toString()+ " Matrix",Toast.LENGTH_SHORT).show();
            }
        });

        //Return the View
        return convertView;

    }


}
