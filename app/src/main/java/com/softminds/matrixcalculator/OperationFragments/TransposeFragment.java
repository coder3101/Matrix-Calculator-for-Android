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
import android.preference.PreferenceManager;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.softminds.matrixcalculator.Matrix;
import com.softminds.matrixcalculator.MatrixAdapter;
import com.softminds.matrixcalculator.R;
import com.softminds.matrixcalculator.base_activities.GlobalValues;
import com.softminds.matrixcalculator.base_activities.ShowResult;
import com.softminds.matrixcalculator.dialog_activity.DialogConfirmation;

public class TransposeFragment extends ListFragment {
    int ClickPos;

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MatrixAdapter adapter = new MatrixAdapter(getContext(),
                R.id.MainContent,((GlobalValues)getActivity().
                getApplication()).GetCompleteList());
        getListView().setDividerHeight(1);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        if(PreferenceManager.getDefaultSharedPreferences(getActivity()).
                getBoolean("TRANSPOSE_PROMPT",true)&&((GlobalValues)getActivity().
                getApplication()).GetCompleteList().get(position).is_squareMatrix())
        {
            Intent intent = new Intent(getContext(), DialogConfirmation.class);
            Bundle b = new Bundle();
            b.putInt("MESSAGE",R.string.SquareTransPrompt);
            b.putInt("ACTION_OKAY",R.string.Yup);
            b.putInt("CONFIRM_TEXT",R.string.Yup);
            b.putInt("CANCEL_TEXT",R.string.Nope);
            b.putInt("RESULT_CODE",120);
            intent.putExtra("NON_RESULT_CODE",119);
            intent.putExtras(b);
            ClickPos = position;
            startActivityForResult(intent,120);

        }
        else
            Execute();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 120) //User Said Yes
        {
           ((GlobalValues)getActivity().getApplication()).GetCompleteList().get(ClickPos).SquareTranspose();
            Toast.makeText(getActivity(),R.string.SuccessTranspose,Toast.LENGTH_SHORT).show();
        }
        if(resultCode == 119)
            Execute();
    }

    private void Execute()
    {
        Intent i2 = new Intent(getContext(), ShowResult.class);
        Matrix original = ((GlobalValues)getActivity().getApplication()).GetCompleteList().get(ClickPos);
        i2.putExtras(original.Transpose().GetDataBundled());
        startActivity(i2);
    }
}
