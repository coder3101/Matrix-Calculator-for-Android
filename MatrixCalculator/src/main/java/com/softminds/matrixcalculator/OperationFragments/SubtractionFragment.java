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
import android.support.annotation.NonNull;
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
import com.softminds.matrixcalculator.AdLoadListener;
import com.softminds.matrixcalculator.Matrix;
import com.softminds.matrixcalculator.R;
import com.softminds.matrixcalculator.GlobalValues;
import com.softminds.matrixcalculator.base_activities.ShowResult;
import com.softminds.matrixcalculator.base_fragments.VariableListSub;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ConstantConditions")
public class SubtractionFragment extends Fragment {

    View root;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((GlobalValues) getActivity().getApplication()).MatrixQueue.clear();
        //Empty the Queue
        VariableListSub variableList = new VariableListSub();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.AdapterAddContainer, variableList, "VARIABLE_ADDER_SUB");
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
        View view = inflater.inflate(R.layout.addition_fragement, container, false);
        root = view;

        CardView adCard = view.findViewById(R.id.AdvertiseMentCardadd);


        if (!((GlobalValues) getActivity().getApplication()).DonationKeyFound()) {
            AdView adView = view.findViewById(R.id.adViewAddActivity);
            AdRequest adRequest = new AdRequest.Builder().build();
            adView.setAdListener(new AdLoadListener(adCard));
            adView.loadAd(adRequest);
        } else {
            ((ViewGroup) adCard.getParent()).removeView(adCard);
        }


        TextView tv =  view.findViewById(R.id.TitleOfAdd);
        TextView tv2 =  view.findViewById(R.id.SubtitleofADD);
        tv.setText(R.string.SubQueue);
        tv2.setText(R.string.SubTip);
        Button proceedAdd =  view.findViewById(R.id.ConfirmAdd);
        proceedAdd.setText(R.string.Proceed);
        proceedAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((GlobalValues) getActivity().getApplication()).MatrixQueue.size() >= 2) {
                    Intent intent = new Intent(getContext(), ShowResult.class);
                    intent.putExtras(SubAll().GetDataBundled());
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), R.string.Notdefined, Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button remove =  view.findViewById(R.id.RemoveLast);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RemoveFromQueue();
            }
        });
        return view;
    }

    private Matrix SubAll() {
        ArrayList<Matrix> buffer = ((GlobalValues) getActivity().getApplication()).MatrixQueue;

        Matrix first = buffer.get(0),second;
        second = new Matrix(buffer.get(0).GetRow(),buffer.get(0).GetCol(),buffer.get(0).GetType());
        for (int t=1;t<buffer.size();t++) {
                second = Matrix.Matadd(second, buffer.get(t));
        }

        return Matrix.MatSub(first,second);

    }

    private void RemoveFromQueue() {
        TextView textView =  root.findViewById(R.id.AdditionStatus);
        String Initial = textView.getText().toString();
        if (Initial.isEmpty()) {
            ((GlobalValues) getActivity().getApplication()).MatrixQueue.clear();
            Toast.makeText(getContext(), R.string.NothingTORemove, Toast.LENGTH_SHORT).show();
        } else {
            if (Initial.contains("-")) {
                String NewName = Initial.substring(0, Initial.lastIndexOf("-"));
                textView.setText(NewName);
                ((GlobalValues) getActivity().getApplication()).MatrixQueue.remove(((GlobalValues) getActivity().getApplication()).MatrixQueue.size() - 1);
            } else {
                textView.setText(null);
                VariableListSub variableListSub = (VariableListSub) getChildFragmentManager().findFragmentByTag("VARIABLE_ADDER_SUB");
                variableListSub.UpdateInfo();
                ((GlobalValues) getActivity().getApplication()).MatrixQueue.clear();
            }
        }
    }


}
