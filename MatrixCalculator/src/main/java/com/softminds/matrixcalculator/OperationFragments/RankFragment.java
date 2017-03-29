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
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.softminds.matrixcalculator.Matrix;
import com.softminds.matrixcalculator.MatrixAdapter;
import com.softminds.matrixcalculator.R;
import com.softminds.matrixcalculator.base_activities.GlobalValues;

import java.lang.ref.WeakReference;

public class RankFragment extends ListFragment {



    private static class MyHandler extends Handler{
        private WeakReference<RankFragment> weakReference;
        private MyHandler(RankFragment rankFragment){
            weakReference = new WeakReference<>(rankFragment);
        }

        @Override
        public void handleMessage(Message msg) {
            RankFragment  frag = weakReference.get();
            int rank = msg.getData().getInt("CALCULATED_RANK",-1);
            Toast.makeText(frag.getContext(),"Rank of Matrix is : "+String.valueOf(rank),Toast.LENGTH_SHORT).show();
        }
    }

    MyHandler handler = new MyHandler(this);

    @Override
    public void onActivityCreated(Bundle saved){
        super.onActivityCreated(saved);
        MatrixAdapter adapter = new MatrixAdapter(getContext(),R.layout.list_layout_fragment,((GlobalValues)getActivity().getApplication()).GetCompleteList());
        getListView().setDividerHeight(1);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getString(R.string.Calculating));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        FindRank(position,progressDialog);
    }

    private void FindRank(final int pos,final ProgressDialog progressDialog){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Matrix mat = ((GlobalValues)getActivity().getApplication()).GetCompleteList().get(pos);
                int rank = mat.GetRank();
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putInt("CALCULATED_RANK",rank);
                message.setData(bundle);
                if(progressDialog.isShowing()) {
                    progressDialog.dismiss();
                    handler.sendMessage(message);
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }
}
