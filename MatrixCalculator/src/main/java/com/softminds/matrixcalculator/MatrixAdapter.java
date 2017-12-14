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
package com.softminds.matrixcalculator;

import android.content.Context;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MatrixAdapter extends ArrayAdapter<Matrix> {

    public MatrixAdapter(Context context, int t, ArrayList<Matrix> matrices) {
        super(context, t, matrices);
    }

    @Override
    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        final Matrix m = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_layout_fragment, parent, false);

        //Grab References
        TextView Row = ((TextView) convertView.findViewById(R.id.MatrixTitleRow));
        TextView Col = ((TextView) convertView.findViewById(R.id.MatrixTitleCol));
        TextView Naam = ((TextView) convertView.findViewById(R.id.MatrixTitle));
        ImageView icon = ((ImageView) convertView.findViewById(R.id.ImageForMatrix));
        CardView cardView = ((CardView) convertView.findViewById(R.id.Matrix_holder));

        //Set Values
        @SuppressWarnings({"ConstantConditions"})
        String r = "Row : " + String.valueOf(m.GetRow());
        String c = "Column : " + String.valueOf(m.GetCol());
        if (PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("DARK_THEME_KEY", false)) {
            Row.setTextColor(Color.WHITE);
            Col.setTextColor(Color.WHITE);
            Naam.setTextColor(Color.WHITE);
            cardView.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.DarkcolorPrimary));

        }
        Row.setText(r);
        Col.setText(c);
        m.SetType();
        Naam.setText(m.GetName());
        icon.setImageResource(m.GetDrawable(m.GetType()));
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), m.GetType().toString() + " Matrix", Toast.LENGTH_SHORT).show();
            }
        });

        //Return the View
        return convertView;

    }


}
