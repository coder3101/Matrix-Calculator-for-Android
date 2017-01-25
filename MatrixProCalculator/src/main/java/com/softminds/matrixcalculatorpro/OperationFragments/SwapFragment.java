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

package com.softminds.matrixcalculatorpro.OperationFragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.softminds.matrixcalculatorpro.MatrixAdapter;
import com.softminds.matrixcalculatorpro.R;
import com.softminds.matrixcalculatorpro.base_activities.GlobalValues;
import com.softminds.matrixcalculatorpro.dialog_activity.SwappingDialog;


public class SwapFragment extends ListFragment{
    @Override
    public void onActivityCreated(Bundle savedInstances){
        super.onActivityCreated(savedInstances);
        MatrixAdapter adapter = new MatrixAdapter(getContext(),R.layout.list_layout_fragment,((GlobalValues)getActivity().getApplication()).GetCompleteList());
        getListView().setDividerHeight(1);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Bundle bundle = new Bundle();
        bundle.putInt("ROW_INDEX",((GlobalValues)getActivity().getApplication()).GetCompleteList().get(position).GetRow());
        bundle.putInt("COL_INDEX",((GlobalValues)getActivity().getApplication()).GetCompleteList().get(position).GetCol());
        bundle.putInt("POSITION",position);
        Intent intent = new Intent(getContext(), SwappingDialog.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
