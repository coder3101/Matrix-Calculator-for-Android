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

package com.softminds.matrixcalculator.OperationFragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;


import com.softminds.matrixcalculator.Matrix;
import com.softminds.matrixcalculator.MatrixAdapter;
import com.softminds.matrixcalculator.R;
import com.softminds.matrixcalculator.GlobalValues;
import com.softminds.matrixcalculator.base_activities.MinorChooser;

import java.util.ArrayList;


public class MinorFragment extends ListFragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayList<Matrix> SquareList = new ArrayList<>();
        for (int i = 0; i < ((GlobalValues) getActivity().getApplication()).GetCompleteList().size(); i++) {
            if (((GlobalValues) getActivity().getApplication()).GetCompleteList().get(i).is_squareMatrix())
                SquareList.add(((GlobalValues) getActivity().getApplication()).GetCompleteList().get(i));
        }
        MatrixAdapter squareadapter = new MatrixAdapter(getContext(), R.layout.list_layout_fragment, SquareList);
        getListView().setDividerHeight(1);
        setListAdapter(squareadapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(getActivity().getApplication(), MinorChooser.class);
        intent.putExtra("INDEX", position);
        startActivity(intent);
    }
}
