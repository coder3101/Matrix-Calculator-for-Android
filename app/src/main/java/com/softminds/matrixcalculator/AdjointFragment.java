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


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdjointFragment extends ListFragment {


    @Override
    public void onActivityCreated(Bundle savedInstances) {
        super.onActivityCreated(savedInstances);
        ArrayList<Matrix> SquareList=new ArrayList<>();
        for(int i=0;i<((GlobalValues)getActivity().getApplication()).GetCompleteList().size();i++)
        {
            if(((GlobalValues)getActivity().getApplication()).GetCompleteList().get(i).is_squareMatrix())
                //if square matrix is present them only ajoint can be found hence only square matrix being selected
                SquareList.add(((GlobalValues)getActivity().getApplication()).GetCompleteList().get(i));
        }
        MatrixAdapter MatriXadapter = new MatrixAdapter(getActivity(),R.id.MainContent,SquareList);
        getListView().setDividerHeight(1);
        setListAdapter(MatriXadapter);

    }
    @Override
    public void onListItemClick(ListView L, View V, int position, long id)
    {

    }

}
