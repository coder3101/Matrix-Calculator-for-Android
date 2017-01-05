/*
MIT License

Copyright (c) 2016 Ashar Khan

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

package com.softminds.matrixcalculator;



import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ListFragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class DeterminantFragment extends ListFragment {


    @Override
    public void onActivityCreated(Bundle savedInstances) {
        super.onActivityCreated(savedInstances);
        ArrayList<Matrix> SquareList = new ArrayList<>();
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
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getString(R.string.Calculating));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        RunToGetDeterminant(position,progressDialog);
    }
    public void RunToGetDeterminant(final int pos, final ProgressDialog px)
    {
        final  Handler handler = new Handler() //Todo Problem here
        {
            @Override
            public  void handleMessage(Message msg)
            {
                Bundle val;
                val = msg.getData();
                final Snackbar snackbar;
                snackbar = Snackbar.make(getListView(),"Determinant is : " + String.valueOf(val.getDouble("RESULTANT")),Snackbar.LENGTH_INDEFINITE);
                px.dismiss();
                snackbar.show();
                snackbar.setAction("GOT IT", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        snackbar.dismiss();
                    }
                });
            }
        };
       Runnable runnable = new Runnable() {
           @Override
           public void run() {
              double var = ((GlobalValues)getActivity().getApplication()).GetCompleteList().get(pos).GetDeterminant();
               Message message = new Message();
               Bundle bundle = new Bundle();
               bundle.putDouble("RESULTANT",var);
               message.setData(bundle);
               handler.sendMessage(message);
           }
       };
        Thread thread = new Thread(runnable);
        thread.start();
    }
}
