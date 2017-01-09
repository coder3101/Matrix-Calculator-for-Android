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
import android.support.design.widget.Snackbar;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.softminds.matrixcalculator.BaseActivities.GlobalValues;
import com.softminds.matrixcalculator.Matrix;
import com.softminds.matrixcalculator.MatrixAdapter;
import com.softminds.matrixcalculator.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class DeterminantFragment extends ListFragment {

    ArrayList<Matrix> SquareList;

    //Inner Class
    private  static class MyHandler extends Handler{
        private final WeakReference<DeterminantFragment> determinantFragmentWeakReference;// a weak Reference to Outer Fragment
        private MyHandler(DeterminantFragment fragment){
             determinantFragmentWeakReference = new WeakReference<>(fragment); //Initialize the Weak Reference with the Fragment
        }
        @Override
        public void handleMessage(Message msg) //override this method
        {
            DeterminantFragment determinantFragment = determinantFragmentWeakReference.get();
            if(determinantFragment!=null) {

                Bundle val;
                val = msg.getData();
                final Snackbar snackbar;
                if (determinantFragment.isVisible()) {
                    snackbar = Snackbar.make(determinantFragment.getListView(), "Determinant is : " + String.valueOf(val.getDouble("RESULTANT")), Snackbar.LENGTH_INDEFINITE);
                    snackbar.show();
                    snackbar.setAction("GOT IT", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            snackbar.dismiss();
                        }
                    });
                }
                else
                    Log.d("Determinant : ", "not shown, no context");
            }
        }
    }

    private final MyHandler myhandler = new MyHandler(this);



    @Override
    public void onActivityCreated(Bundle savedInstances) {
        super.onActivityCreated(savedInstances);
         SquareList = new ArrayList<>();
        for (int i = 0; i < ((GlobalValues) getActivity().getApplication()).GetCompleteList().size(); i++) {
            if (((GlobalValues) getActivity().getApplication()).GetCompleteList().get(i).is_squareMatrix())
                SquareList.add(((GlobalValues) getActivity().getApplication()).GetCompleteList().get(i));
        }
        MatrixAdapter MatriXadapter = new MatrixAdapter(getActivity(), R.id.MainContent, SquareList);
        getListView().setDividerHeight(1);
        setListAdapter(MatriXadapter);
    }

    @Override
    public void onListItemClick(ListView L, View V, int position, long id) { //Todo Make it Comaptible for Dark theme Also
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getString(R.string.Calculating));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        RunToGetDeterminant(position,progressDialog);
    }



    public void RunToGetDeterminant(final int pos, final ProgressDialog px)
    {
       Runnable runnable = new Runnable() {
           @Override
           public void run() {
               double var = SquareList.get(pos).GetDeterminant();
               Message message = new Message();
               Bundle bundle = new Bundle();
               bundle.putDouble("RESULTANT",var);
               message.setData(bundle);
               myhandler.sendMessage(message);

           }
       };
        Thread thread = new Thread(runnable);
        thread.start();
    }

}
