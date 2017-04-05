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
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.softminds.matrixcalculator.Matrix;
import com.softminds.matrixcalculator.R;
import com.softminds.matrixcalculator.base_activities.GlobalValues;
import com.softminds.matrixcalculator.base_activities.ShowResult;
import com.softminds.matrixcalculator.base_fragments.VariableMul;

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

        if(!((GlobalValues)getActivity().getApplication()).DonationKeyFound()) {
            AdView adView = (AdView) view.findViewById(R.id.adViewAddActivity);
            AdRequest adRequest = new AdRequest.Builder().build();
            adView.loadAd(adRequest);
        }
        else
        {
            CardView advt = (CardView)view.findViewById(R.id.AdvertiseMentCardadd);
            ((ViewGroup)advt.getParent()).removeView(advt);
        }


        TextView tv = (TextView) view.findViewById(R.id.TitleOfAdd);
        TextView tv2 = (TextView) view.findViewById(R.id.SubtitleofADD);
        tv.setText(R.string.MulQueue);
        tv2.setText(R.string.MulTip);
        Button proceedAdd = (Button) view.findViewById(R.id.ConfirmAdd);
        proceedAdd.setText(R.string.Proceed);
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
                ((GlobalValues)getActivity().getApplication()).MatrixQueue.remove(((GlobalValues)getActivity().getApplication()).MatrixQueue.size()-1);
                int pos = ((GlobalValues)getActivity().getApplication()).MatrixQueue.size();
                int r =  ((GlobalValues)getActivity().getApplication()).MatrixQueue.get(pos-1).GetRow();
                int c =  ((GlobalValues)getActivity().getApplication()).MatrixQueue.get(pos-1).GetCol();
                VariableMul child = ((VariableMul) getChildFragmentManager().findFragmentByTag("VARIABLE_ADDER_MUL"));
                if(child != null){
                    child.UpdateRowCol(r,c);
                }
            }
            else
            {
                textView.setText(null);
                VariableMul child = ((VariableMul) getChildFragmentManager().findFragmentByTag("VARIABLE_ADDER_MUL"));
                child.Restore();
                ((GlobalValues)getActivity().getApplication()).MatrixQueue.clear();
            }
        }
    }
    private Matrix MultiplyAll(){
        ArrayList<Matrix> buffer =((GlobalValues)getActivity().getApplication()).MatrixQueue;
        Matrix res = new Matrix(buffer.get(0).GetRow(),buffer.get(0).GetCol(),buffer.get(0).GetType());
        res.CloneFrom(buffer.get(0));
        for(int i=1;i<buffer.size();i++){
            res.MultiplytoThis(buffer.get(i));
        }
        return res;
    }
}
