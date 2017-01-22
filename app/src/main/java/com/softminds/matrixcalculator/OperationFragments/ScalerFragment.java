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
import com.softminds.matrixcalculator.base_activities.GlobalValues;
import com.softminds.matrixcalculator.base_activities.ShowResult;
import com.softminds.matrixcalculator.dialog_activity.MultiplierSetter;

public class ScalerFragment extends ListFragment {
    private int ClickPos;

    @Override
    public void onActivityCreated( Bundle savedInstances){
        super.onActivityCreated(savedInstances);
        MatrixAdapter adapter = new MatrixAdapter(getContext(),R.id.MainContent,((GlobalValues)getActivity().getApplication()).GetCompleteList());
        getListView().setDividerHeight(1);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        ClickPos = position;
        Intent getMultiplier = new Intent(getContext(), MultiplierSetter.class);
        startActivityForResult(getMultiplier,1054);
    }

    @Override
    public void onActivityResult(int req,int res,Intent data) {
        super.onActivityResult(req,res,data);
        if (res == 1054) {
            Intent intent = new Intent(getContext(), ShowResult.class);
            Matrix Result =  ((GlobalValues)getActivity().getApplication()).
                    GetCompleteList().get(ClickPos).ReturnScaler
                    (data.getFloatExtra("MULTIPLIER_VAL",0));
            intent.putExtras(Result.GetDataBundled());
            startActivity(intent);
        }
    }
}
