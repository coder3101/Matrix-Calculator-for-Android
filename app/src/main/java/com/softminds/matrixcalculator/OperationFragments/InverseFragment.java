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
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.softminds.matrixcalculator.base_activities.GlobalValues;
import com.softminds.matrixcalculator.Matrix;
import com.softminds.matrixcalculator.MatrixAdapter;
import com.softminds.matrixcalculator.R;
import com.softminds.matrixcalculator.base_activities.ShowResult;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class InverseFragment extends ListFragment {

    ArrayList<Matrix> SquareList;
    ProgressDialog progress;

    private static class MyHandler extends Handler{
        private final WeakReference<InverseFragment> weakReference;
        MyHandler(InverseFragment inverseFragment){
            weakReference = new WeakReference<>(inverseFragment);
        }
        @Override
        public  void handleMessage(Message message){
            if(weakReference.get().progress.isShowing()){
                Intent intent = new Intent(weakReference.get().getActivity(), ShowResult.class);
                intent.putExtras(message.getData());
                weakReference.get().progress.dismiss();
                weakReference.get().startActivity(intent);
                }

            }
    }

    MyHandler myHandler = new MyHandler(this);

    @Override
    public void onActivityCreated(Bundle savedInstances) {
        super.onActivityCreated(savedInstances);
        SquareList=new ArrayList<>();
        for(int i = 0; i<((GlobalValues)getActivity().getApplication()).GetCompleteList().size(); i++)
        {
            if(((GlobalValues)getActivity().getApplication()).GetCompleteList().get(i).is_squareMatrix())
                SquareList.add(((GlobalValues)getActivity().getApplication()).GetCompleteList().get(i));
        }
        MatrixAdapter MatriXadapter = new MatrixAdapter(getActivity(), R.id.MainContent,SquareList);
        getListView().setDividerHeight(1);
        setListAdapter(MatriXadapter);

    }
    @Override
    public void onListItemClick(ListView L, View V, int position, long id)
    {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getString(R.string.Calculating));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setIndeterminate(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        progress = progressDialog;
        RunNewGetInverse(position,progressDialog);

    }

    public void RunNewGetInverse(final int pos,final ProgressDialog pq)
    {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
               Matrix res = SquareList.get(pos).Inverse(pq);
                Message message = new Message();
                if(res!=null){
                message.setData(res.GetDataBundled());
                myHandler.sendMessage(message);
                }
                else{
                    myHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(),R.string.NoInverse,Toast.LENGTH_SHORT).show();
                        }
                    },0);
                    pq.dismiss();
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

}
