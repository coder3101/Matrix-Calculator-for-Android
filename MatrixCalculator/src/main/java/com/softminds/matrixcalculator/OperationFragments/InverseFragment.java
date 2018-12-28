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


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.softminds.matrixcalculator.GlobalValues;
import com.softminds.matrixcalculator.MatrixV2;
import com.softminds.matrixcalculator.MatrixAdapter;
import com.softminds.matrixcalculator.R;
import com.softminds.matrixcalculator.base_activities.ShowResult;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

@SuppressWarnings("ConstantConditions")
public class InverseFragment extends ListFragment {

    final String KEY = "DETERMINANT_FOR_INVERSE";
    boolean ENABLED_NO_DECIMAL;

    ArrayList<MatrixV2> SquareList;
    ProgressDialog progress;

    private static class MyHandler extends Handler {
        private final WeakReference<InverseFragment> weakReference;

        MyHandler(InverseFragment inverseFragment) {
            weakReference = new WeakReference<>(inverseFragment);
        }

        @Override
        public void handleMessage(Message message) {
            if (weakReference.get().progress.isShowing()) {
                Intent intent = new Intent(weakReference.get().getActivity(), ShowResult.class);
                if (message.getData().getFloat("DETERMINANT", 0) == 0) {
                    intent.putExtras(message.getData());
                    weakReference.get().progress.dismiss();
                    weakReference.get().startActivity(intent);
                } else {
                    intent.putExtras(message.getData());
                    intent.putExtra(weakReference.get().KEY, message.getData().getFloat("DETERMINANT", 0));
                    weakReference.get().progress.dismiss();
                    weakReference.get().startActivity(intent);
                }

            }
        }
    }

    MyHandler myHandler = new MyHandler(this);

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

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        ENABLED_NO_DECIMAL = preferences.getBoolean("NO_FRACTION_ENABLED", false);

    }

    @Override
    public void onListItemClick(ListView L, View V, int position, long id) {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getString(R.string.Calculating));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setIndeterminate(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        progress = progressDialog;

        if (SquareList.get(position).getJamaMatrix().det() != 0) {
            if (ENABLED_NO_DECIMAL)
                RunAndGetDeterminantWithAdjoint(position, progressDialog);
            else
                RunNewGetInverse(position, progressDialog);
        } else {
            new AlertDialog.Builder(getContext())
                    .setMessage("The Determinant of the matrix was Zero and Hence its Inverse does not exist")
                    .setTitle("No Inverse Exist")
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .show();
            progressDialog.dismiss();
        }

    }

    public void RunNewGetInverse(final int pos, final ProgressDialog pq) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                MatrixV2 res = SquareList.get(pos).getInverse(pq);
                Message message = new Message();
                if (res != null) {
                    message.setData(res.getDataBundled());
                    myHandler.sendMessage(message);
                } else {
                    myHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(), R.string.NoInverse, Toast.LENGTH_SHORT).show();
                        }
                    }, 0);
                    pq.dismiss();
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    public void RunAndGetDeterminantWithAdjoint(final int i, final ProgressDialog progressDialog) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                Bundle bundle = new Bundle();
                float detr = (float) SquareList.get(i).getDeterminant(progressDialog);
                if (detr == 0.0f) {
                    myHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(), R.string.NoInverse, Toast.LENGTH_SHORT).show();
                        }
                    }, 0);
                    progressDialog.dismiss();
                } else {
                    progressDialog.setProgress(0);
                    bundle.putFloat("DETERMINANT", detr);
                    MatrixV2 res = SquareList.get(i).getAdjoint(progressDialog);
                    bundle.putAll(res.getDataBundled());
                    message.setData(bundle);
                    myHandler.sendMessage(message);
                }

            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

}
