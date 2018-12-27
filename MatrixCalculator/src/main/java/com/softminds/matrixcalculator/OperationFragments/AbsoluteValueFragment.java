/*
 * Copyright (c) 2018. <ashar786khan@gmail.com>
 * This file is part of Application 's Android Application.
 * Application' s Android Application is free software : you can redistribute it and/or modify
 * it under the terms of GNU General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 *
 * This Application is distributed in the hope that it will be useful
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General  Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with this Source File.
 *  If not, see <http:www.gnu.org/licenses/>.
 */

package com.softminds.matrixcalculator.OperationFragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.softminds.matrixcalculator.GlobalValues;
import com.softminds.matrixcalculator.MatrixAdapter;
import com.softminds.matrixcalculator.MatrixV2;
import com.softminds.matrixcalculator.R;
import com.softminds.matrixcalculator.base_activities.ShowResult;

import Jama.Matrix;


public class AbsoluteValueFragment extends ListFragment {

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AbsoluteValueFragment() {

    }

    @Override
    public void onActivityCreated(Bundle savedInstances) {
        super.onActivityCreated(savedInstances);
        MatrixAdapter MatriXadapter = new MatrixAdapter(getActivity(), R.layout.list_layout_fragment, ((GlobalValues) getActivity().getApplication()).GetCompleteList());
        getListView().setDividerHeight(1);
        setListAdapter(MatriXadapter);
    }

    @Override
    public void onListItemClick(ListView L, View V, int position, long id) {
        MatrixV2 ref = ((GlobalValues) getActivity().getApplication()).GetCompleteList().get(position);
        double[][] val = ref.getJamaMatrix().getArrayCopy();
        for (int t = 0; t < ref.getNumberOfRows(); t++)
            for (int k = 0; k < ref.getNumberOfCols(); k++)
                val[t][k] = Math.abs(val[t][k]);
        MatrixV2 result = MatrixV2.constructFromJamaMatrix(Matrix.constructWithCopy(val));
        Intent intent = new Intent(getContext(), ShowResult.class);
        intent.putExtras(result.getDataBundled());
        startActivity(intent);
    }

}
