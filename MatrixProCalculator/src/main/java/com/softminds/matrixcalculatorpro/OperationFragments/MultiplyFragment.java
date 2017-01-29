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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.softminds.matrixcalculatorpro.Matrix;
import com.softminds.matrixcalculatorpro.R;
import com.softminds.matrixcalculatorpro.base_activities.GlobalValues;
import com.softminds.matrixcalculatorpro.base_activities.ShowResult;
import com.softminds.matrixcalculatorpro.base_fragments.VariableMul;

import java.util.ArrayList;

public class MultiplyFragment extends Fragment {

    View root;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((GlobalValues)getActivity().getApplication()).MatrixQueue.clear();
        //Empty the Queue
        VariableMul variableList = new VariableMul();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.AdapterAddContainer,variableList,"VARIABLE_ADDER_MUL");
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
        View view =inflater.inflate(R.layout.addition_fragement, container, false);
        root = view;
        TextView tv = (TextView) view.findViewById(R.id.TitleOfAdd);
        TextView tv2 = (TextView) view.findViewById(R.id.SubtitleofADD);
        tv.setText(R.string.MulQueue);
        tv2.setText(R.string.MulTip);
        Button proceedAdd = (Button) view.findViewById(R.id.ConfirmAdd);
        proceedAdd.setText(R.string.ProceedMul);
        proceedAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((GlobalValues)getActivity().getApplication()).MatrixQueue.size()>=2){
                    Intent intent = new Intent(getContext(), ShowResult.class);
                    intent.putExtras(MultiplyAll().GetDataBundled());
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getContext(),R.string.Notdefined,Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button remove = (Button) view.findViewById(R.id.RemoveLast);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RemoveFromQueue();
            }
        });
        return view;
    }
    private void RemoveFromQueue(){
        TextView textView = (TextView) root.findViewById(R.id.AdditionStatus);
        String Initial = textView.getText().toString();
        if(Initial.isEmpty()){
            ((GlobalValues)getActivity().getApplication()).MatrixQueue.clear();
            Toast.makeText(getContext(),R.string.NothingTORemove,Toast.LENGTH_SHORT).show();
        }
        else {
            if(Initial.contains("x")) {
                String NewName = Initial.substring(0, Initial.lastIndexOf("x"));
                textView.setText(NewName);
                int r = 2;
                int c = 3;
                ((GlobalValues)getActivity().getApplication()).MatrixQueue.remove(((GlobalValues)getActivity().getApplication()).MatrixQueue.size()-1);
                int Size = ((GlobalValues)getActivity().getApplication()).MatrixQueue.size();
                try {
                    r = ((GlobalValues) getActivity().getApplication()).MatrixQueue.get(Size - 1).GetRow();
                    c = ((GlobalValues) getActivity().getApplication()).MatrixQueue.get(Size - 1).GetCol();
                }catch (ArrayIndexOutOfBoundsException e){
                    Log.d("ArrayIndexOutOfBound","size of queue is 0");
                    e.printStackTrace();
                }
                try {
                    VariableMul child = ((VariableMul) getChildFragmentManager().findFragmentByTag("VARIABLE_ADDER_MUL"));
                    child.Update(r,c);
                }catch (NullPointerException e){
                    Log.d("NullPointerException","Unable to get the child fragment");
                    Toast.makeText(getContext(),R.string.ErrorGoBack,Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
            else
            {
                textView.setText(null);
                ((GlobalValues)getActivity().getApplication()).MatrixQueue.clear();
                VariableMul variableMul = (VariableMul)getChildFragmentManager().findFragmentByTag("VARIABLE_ADDER_MUL");
                variableMul.Update();
            }
        }
    }
    private Matrix MultiplyAll(){
        ArrayList<Matrix> buffer =((GlobalValues)getActivity().getApplication()).MatrixQueue;
        Matrix res = new Matrix(buffer.get(0).GetRow(),buffer.get(0).GetCol(),buffer.get(1).GetType());
        res.CloneFrom(buffer.get(0));
        try {
            for (int i = 1; i < buffer.size(); i++) {
                res.MultiplytoThis(buffer.get(i));
            }
        }catch (ExceptionInInitializerError error){
            error.printStackTrace();
            Log.d("Multiplication","Invalid Order to Multiply",error.getCause());
        }
        return res;
    }


}
