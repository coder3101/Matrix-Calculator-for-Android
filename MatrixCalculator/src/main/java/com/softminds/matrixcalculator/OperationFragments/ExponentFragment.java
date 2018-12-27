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


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.softminds.matrixcalculator.MatrixV2;
import com.softminds.matrixcalculator.dialog_activity.ExponentSetter;
import com.softminds.matrixcalculator.GlobalValues;
import com.softminds.matrixcalculator.MatrixAdapter;
import com.softminds.matrixcalculator.R;
import com.softminds.matrixcalculator.base_activities.ShowResult;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

@SuppressWarnings("ConstantConditions")
public class ExponentFragment extends ListFragment {

    int Clicked_pos;
    ArrayList<MatrixV2> SquareList;

    private static class MyHandler extends Handler {
        private final WeakReference<ExponentFragment> exponentFragmentWeakReference;

        private MyHandler(ExponentFragment fragment) {
            exponentFragmentWeakReference = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            ExponentFragment exponentfrag = exponentFragmentWeakReference.get();
            Intent intent = new Intent(exponentfrag.getActivity(), ShowResult.class);
            intent.putExtras(msg.getData());
            exponentfrag.startActivity(intent);
        }

    }

    MyHandler handler = new MyHandler(this);

    @Override
    public void onActivityCreated(Bundle savedInstances) {
        super.onActivityCreated(savedInstances);
        SquareList = new ArrayList<>();
        for (int i = 0; i < ((GlobalValues) getActivity().getApplication()).GetCompleteList().size(); i++) {
            if (((GlobalValues) getActivity().getApplication()).GetCompleteList().get(i).isSquareMatrix())
                SquareList.add(((GlobalValues) getActivity().getApplication()).GetCompleteList().get(i));
        }
        MatrixAdapter MatriXadapter = new MatrixAdapter(getActivity(), R.layout.list_layout_fragment, SquareList);
        getListView().setDividerHeight(1);
        setListAdapter(MatriXadapter);

    }

    @Override
    public void onListItemClick(ListView L, View V, int position, long id) {
        Clicked_pos = position;
        Intent intent = new Intent(getActivity(), ExponentSetter.class);
        startActivityForResult(intent, 500);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 500) {
            ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage(getString(R.string.Calculating));
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setIndeterminate(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            RunAndGetResult(Clicked_pos, data.getIntExtra("QWERTYUIOP", 0), progressDialog);
        }
    }

    public void RunAndGetResult(final int pos, final int exponent, final ProgressDialog progressDialog) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                MatrixV2 res = new MatrixV2(((GlobalValues) getActivity().getApplication()).GetCompleteList().get(pos).getNumberOfRows()
                        , ((GlobalValues) getActivity().getApplication()).GetCompleteList().get(pos).getNumberOfCols()
                        , ((GlobalValues) getActivity().getApplication()).GetCompleteList().get(pos).getType());
                res.cloneFrom(((GlobalValues) getActivity().getApplication()).GetCompleteList().get(pos));
                res.raisedToPower(exponent);
                progressDialog.dismiss();
                Message message = new Message();
                message.setData(res.getDataBundled());
                handler.sendMessage(message);
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

}
