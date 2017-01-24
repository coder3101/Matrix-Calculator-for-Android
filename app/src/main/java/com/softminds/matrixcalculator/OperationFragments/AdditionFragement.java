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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.softminds.matrixcalculator.Matrix;
import com.softminds.matrixcalculator.R;
import com.softminds.matrixcalculator.base_activities.GlobalValues;
import com.softminds.matrixcalculator.base_activities.ShowResult;
import com.softminds.matrixcalculator.base_fragments.VariableListAdd;

import java.util.ArrayList;

public class AdditionFragement extends Fragment {

    View root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((GlobalValues)getActivity().getApplication()).MatrixQueue.clear();
        //Empty the Queue
        VariableListAdd variableList = new VariableListAdd();
       FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.AdapterAddContainer,variableList,"VARIABLE_ADDER");
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
        View view =inflater.inflate(R.layout.addition_fragement, container, false);
        root = view;
        Button proceedAdd = (Button) view.findViewById(R.id.ConfirmAdd);
        proceedAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((GlobalValues)getActivity().getApplication()).MatrixQueue.size()>=2){
                    Intent intent = new Intent(getContext(), ShowResult.class);
                    intent.putExtras(SumAll().GetDataBundled());
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
    private Matrix SumAll(){
        ArrayList<Matrix> buffer =((GlobalValues)getActivity().getApplication()).MatrixQueue;
        Matrix res = new Matrix(buffer.get(1).GetRow(),buffer.get(1).GetCol(),buffer.get(1).GetType());
        for(int i=0;i<buffer.size();i++){
            res.AddtoThis(buffer.get(i));
        }
        return res;
    }
    private void RemoveFromQueue(){
        TextView textView = (TextView) root.findViewById(R.id.AdditionStatus);
            String Initial = textView.getText().toString();
            if(Initial.isEmpty()){
                ((GlobalValues)getActivity().getApplication()).MatrixQueue.clear();
                Toast.makeText(getContext(),R.string.NothingTORemove,Toast.LENGTH_SHORT).show();
            }
            else {
                if(Initial.contains("+")) {
                    String NewName = Initial.substring(0, Initial.lastIndexOf("+"));
                    textView.setText(NewName);
                    ((GlobalValues)getActivity().getApplication()).MatrixQueue.remove(((GlobalValues)getActivity().getApplication()).MatrixQueue.size()-1);
                }
                else
                {
                    textView.setText(null);
                    ((GlobalValues)getActivity().getApplication()).MatrixQueue.clear();
                }
            }
        }

}