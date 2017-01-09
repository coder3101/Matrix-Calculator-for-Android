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
package com.softminds.matrixcalculator.BaseFragments;


;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.softminds.matrixcalculator.BaseActivities.GlobalValues;
import com.softminds.matrixcalculator.BaseActivities.ViewCreatedMatrix;
import com.softminds.matrixcalculator.MatrixAdapter;
import com.softminds.matrixcalculator.R;

public class MainActivityFragmentList extends ListFragment {

    @Override
    public void onActivityCreated(Bundle savedInstances)
    {
        super.onActivityCreated(savedInstances);
        ((GlobalValues)getActivity().getApplication()).matrixAdapter= new MatrixAdapter(getActivity(), R.layout.list_layout_fragment,
                ((GlobalValues) getActivity().getApplication()).GetCompleteList());
        getListView().setDividerHeight(1);
        setListAdapter(((GlobalValues)getActivity().getApplication()).matrixAdapter);

    }

    @Override
    public void onListItemClick(ListView L,View V,int position,long id)
    { //Last Index from Global is 1 more that the position provided

        Intent intent =new Intent(getActivity().getApplication(),ViewCreatedMatrix.class);
        intent.putExtra("INDEX",position);
        startActivity(intent);
    }

}
