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

package com.softminds.matrixcalculatorpro.OperationFragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.softminds.matrixcalculatorpro.Function;
import com.softminds.matrixcalculatorpro.base_activities.GlobalValues;
import com.softminds.matrixcalculatorpro.Matrix;
import com.softminds.matrixcalculatorpro.MatrixAdapter;
import com.softminds.matrixcalculatorpro.R;
import com.softminds.matrixcalculatorpro.base_activities.ShowResult;
import com.softminds.matrixcalculatorpro.dialog_activity.FunctionMaker;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class FunctionalFragment extends ListFragment {

    int ClickPos;
    ArrayList<Matrix> SquareList;

    private static class MyHandler extends Handler{
        private WeakReference<FunctionalFragment> weakReference;
        MyHandler(FunctionalFragment fragment){
            weakReference = new WeakReference<>(fragment);
        }
        @Override
        public void handleMessage(Message msg){
            FunctionalFragment functionalFragment = weakReference.get();
            Intent intent = new Intent(functionalFragment.getContext(),ShowResult.class);
            intent.putExtras(msg.getData());
            functionalFragment.startActivity(intent);
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
                //search only square matrix from list
                SquareList.add(((GlobalValues)getActivity().getApplication()).GetCompleteList().get(i));
        }
        MatrixAdapter MatriXadapter = new MatrixAdapter(getActivity(), R.layout.list_layout_fragment,SquareList);
        getListView().setDividerHeight(1);
        setListAdapter(MatriXadapter);

    }
    @Override
    public void onListItemClick(ListView L, View V, int position, long id)
    {
        ClickPos = position;
        Intent intent = new Intent(getContext(), FunctionMaker.class);
        int newpos = ((GlobalValues)getActivity().getApplication()).GetCompleteList().indexOf(SquareList.get(ClickPos));
        intent.putExtra("MATRIX_GLOBAL_INDEX",newpos );
        startActivityForResult(intent,1452);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode == 1452)
        {
            try {
                final Function function = ((GlobalValues) getActivity().getApplication()).getFunction();
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        Matrix m = function.ComputeFunction(SquareList.get(ClickPos));
                        Message message = new Message();
                        message.setData(m.GetDataBundled());
                        myHandler.sendMessage(message);
                    }
                };
                Thread thread = new Thread(runnable);
                thread.start();
            }catch (NullPointerException e){
                Log.d("Exception : ", "Function at Global Context is Null");
                e.printStackTrace();
            }
        }
    }
}
