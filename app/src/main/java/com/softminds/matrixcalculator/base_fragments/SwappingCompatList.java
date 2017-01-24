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


import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.softminds.matrixcalculator.Matrix;
import com.softminds.matrixcalculator.MatrixAdapter;
import com.softminds.matrixcalculator.R;
import com.softminds.matrixcalculator.base_activities.GlobalValues;
import com.softminds.matrixcalculator.dialog_activity.SwappingDialog;

import java.util.ArrayList;

public class SwappingCompatList extends ListFragment {

   ArrayList<Matrix> CompatList= new ArrayList<>();
    @Override
    public void onActivityCreated(Bundle savedInstances){
        super.onActivityCreated(savedInstances);
        for(int i=0;i<((GlobalValues)getActivity().getApplication()).GetCompleteList().size();i++)
        {
            if(getArguments().getInt("ROW_INDEX")==((GlobalValues)getActivity().
                    getApplication()).GetCompleteList().get(i).GetRow()&&
                    getArguments().getInt("COL_INDEX")==((GlobalValues)getActivity()
                            .getApplication()).GetCompleteList().get(i).GetCol()&&getArguments()
                    .getInt("POSITION")!=(((GlobalValues) getActivity().getApplication())
                    .GetCompleteList().indexOf(((GlobalValues)getActivity().getApplication())
                            .GetCompleteList().get(i)))) {
                CompatList.add(((GlobalValues) getActivity().getApplication()).GetCompleteList().get(i));
            }
            if(CompatList.isEmpty())
            {
                ((SwappingDialog)getActivity()).SetMidText(getString(R.string.NoneSwap));
            }
            MatrixAdapter adapter = new MatrixAdapter(getContext(),R.layout.list_layout_fragment,CompatList);
            getListView().setDividerHeight(1);
            setListAdapter(adapter);
        }
    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Matrix clickedmatrix = ((GlobalValues)getActivity().getApplication()).GetCompleteList().get(position);
        Matrix original = ((GlobalValues)getActivity().getApplication()).GetCompleteList().get(getArguments().getInt("POSITION"));
        original.SwapWith(clickedmatrix);
        Toast.makeText(getActivity(),R.string.SwapSucc,Toast.LENGTH_SHORT).show();
        onDetach();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ((GlobalValues)getActivity().getApplication()).matrixAdapter.notifyDataSetChanged();
        getActivity().finish();
    }
}
