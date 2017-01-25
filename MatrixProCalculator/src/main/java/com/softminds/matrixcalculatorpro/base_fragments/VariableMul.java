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


import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.softminds.matrixcalculatorpro.Matrix;
import com.softminds.matrixcalculatorpro.MatrixAdapter;
import com.softminds.matrixcalculatorpro.R;
import com.softminds.matrixcalculatorpro.base_activities.GlobalValues;


public class VariableMul extends ListFragment {

    MatrixAdapter adapter;
    int Row;
    int Col;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new MatrixAdapter(getContext(), R.layout.list_layout_fragment, ((GlobalValues) getActivity().getApplication()).GetCompleteList());
        getListView().setDividerHeight(1);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Matrix m = ((GlobalValues) getActivity().getApplication()).GetCompleteList().get(position);
        if (Row == 0 || Col == 0) {
            Row = m.GetRow();
            Col = m.GetCol();
            AddToQueue(m);
        } else {
            if (Col == m.GetRow()) {
                AddToQueue(m);
                Row = m.GetRow();
                Col = m.GetCol();
            } else {
                Toast.makeText(getContext(), "Select a Matrix with " + String.valueOf(Col) + " Row", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void AddToQueue(Matrix click) {
        try {
            @SuppressWarnings("ConstantConditions") //to suppress the null pointer exception of the  textview
                    TextView textView = (TextView) getParentFragment().getView().findViewById(R.id.AdditionStatus);
            String Initial = textView.getText().toString();
            if (Initial.isEmpty()) {
                textView.setText(click.GetName());
                ((GlobalValues) getActivity().getApplication()).MatrixQueue.add(click);
            } else {
                String Complete = Initial + " x " + click.GetName();
                textView.setText(Complete);
                ((GlobalValues) getActivity().getApplication()).MatrixQueue.add(click);
            }
        } catch (NullPointerException e) {
            Log.d("AddToQueue", "Exception raised, cannot get textview from parent fragment");
            e.printStackTrace();
        }

    }
    public void Update(int row, int col){
        Row = row;
        Col = col;
    }
}
