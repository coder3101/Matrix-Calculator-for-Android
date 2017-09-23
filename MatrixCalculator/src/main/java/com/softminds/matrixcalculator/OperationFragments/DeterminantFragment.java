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
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.softminds.matrixcalculator.GlobalValues;
import com.softminds.matrixcalculator.Matrix;
import com.softminds.matrixcalculator.MatrixAdapter;
import com.softminds.matrixcalculator.R;

import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
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
            final DeterminantFragment determinantFragment = determinantFragmentWeakReference.get();
            if(determinantFragment!=null) {

                final Bundle val;
                val = msg.getData();
                if (determinantFragment.isVisible()) {

                    final String mes = determinantFragment.getString(R.string.determinant_is)+ " " + determinantFragment.GetText(val.getDouble("RESULTANT"));

                    final AlertDialog.Builder builder = new AlertDialog.Builder(determinantFragment.getContext());
                    builder.setPositiveButton(R.string.copy, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ClipboardManager clipboardManager = (ClipboardManager) determinantFragment.getActivity()
                                    .getSystemService(Context.CLIPBOARD_SERVICE);
                            ClipData clipData = ClipData.newPlainText("DETERMINANT_RES",determinantFragment.GetText(val.getDouble("RESULTANT")));
                            clipboardManager.setPrimaryClip(clipData);
                            if(clipboardManager.hasPrimaryClip()){
                                Toast.makeText(determinantFragment.getContext(),R.string.CopyToClip,Toast.LENGTH_SHORT).show();
                            }
                            else
                                Log.d("ClipData","Failed to set to Clip board");
                            dialogInterface.dismiss();
                        }
                    });

                    builder.setNeutralButton(R.string.Done, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.setMessage(mes);
                    builder.setTitle(R.string.determinant);
                    builder.setCancelable(false);
                    builder.show();

                }
                else
                    Log.d("Determinant : ", "not shown");
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
        MatrixAdapter MatriXadapter = new MatrixAdapter(getActivity(), R.layout.list_layout_fragment, SquareList);
        getListView().setDividerHeight(1);
        setListAdapter(MatriXadapter);
    }

    @Override
    public void onListItemClick(ListView L, View V, int position, long id) {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getString(R.string.Calculating));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setIndeterminate(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        RunToGetDeterminant(position,progressDialog);
    }



    public void RunToGetDeterminant(final int pos, final ProgressDialog px)
    {
       Runnable runnable = new Runnable() {
           @Override
           public void run() {
               double var = SquareList.get(pos).GetDeterminant(px);
               Message message = new Message();
               Bundle bundle = new Bundle();
               bundle.putDouble("RESULTANT",var);
               message.setData(bundle);
               px.dismiss();
               myhandler.sendMessage(message);

           }
       };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    private String GetText(double res) {

        if (!PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("DECIMAL_USE", true)) {
            DecimalFormat decimalFormat = new DecimalFormat("###############");
            return decimalFormat.format(res);
        } else
        {
            switch (Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(getContext()).getString("ROUNDIND_INFO","0"))) {
                case 0:
                    return String.valueOf(res);
                case 1:
                    DecimalFormat single = new DecimalFormat("########.#");
                    return single.format(res);
                case 2:
                    DecimalFormat Double = new DecimalFormat("########.##");
                    return Double.format(res);
                case 3:
                    DecimalFormat triple = new DecimalFormat("########.###");
                    return triple.format(res);
                default:
                    return String.valueOf(res);
            }
        }
    }

}
