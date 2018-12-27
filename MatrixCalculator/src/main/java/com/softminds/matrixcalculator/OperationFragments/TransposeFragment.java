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


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.softminds.matrixcalculator.MatrixV2;
import com.softminds.matrixcalculator.MatrixAdapter;
import com.softminds.matrixcalculator.R;
import com.softminds.matrixcalculator.GlobalValues;
import com.softminds.matrixcalculator.base_activities.ShowResult;

@SuppressWarnings("ConstantConditions")
public class TransposeFragment extends ListFragment {
    int ClickPos;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MatrixAdapter adapter = new MatrixAdapter(getContext(),
                R.layout.list_layout_fragment, ((GlobalValues) getActivity().
                getApplication()).GetCompleteList());
        getListView().setDividerHeight(1);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        if (PreferenceManager.getDefaultSharedPreferences(getActivity()).
                getBoolean("TRANSPOSE_PROMPT", true) && ((GlobalValues) getActivity().
                getApplication()).GetCompleteList().get(position).isSquareMatrix()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(R.string.TransposePrompt);
            builder.setPositiveButton(R.string.Yup, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ((GlobalValues) getActivity().getApplication()).GetCompleteList().get(ClickPos).transposeEquals();
                    Toast.makeText(getActivity(), R.string.SuccessTranspose, Toast.LENGTH_SHORT).show();
                    dialogInterface.dismiss();
                }
            });
            builder.setNegativeButton(R.string.Nope, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    Intent i2 = new Intent(getContext(), ShowResult.class);
                    MatrixV2 original = ((GlobalValues) getActivity().getApplication()).GetCompleteList().get(ClickPos);
                    i2.putExtras(original.transpose().getDataBundled());
                    startActivity(i2);
                }
            });
            builder.setMessage(R.string.SquareTransPrompt);
            builder.show();
        } else //Non Square MatrixV2 to transpose
        {
            Intent i2 = new Intent(getContext(), ShowResult.class);
            MatrixV2 original = ((GlobalValues) getActivity().getApplication()).GetCompleteList().get(ClickPos);
            i2.putExtras(original.transpose().getDataBundled());
            startActivity(i2);
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 120) //User Said Yes
        {
            ((GlobalValues) getActivity().getApplication()).GetCompleteList().get(ClickPos).transposeEquals();
            Toast.makeText(getActivity(), R.string.SuccessTranspose, Toast.LENGTH_SHORT).show();
        }
    }

}
