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


import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.softminds.matrixcalculator.MainActivity;
import com.softminds.matrixcalculator.Matrix;
import com.softminds.matrixcalculator.MatrixAdapter;
import com.softminds.matrixcalculator.R;
import com.softminds.matrixcalculator.GlobalValues;
import com.softminds.matrixcalculator.base_fragments.MainActivityFragmentList;


@SuppressWarnings("ConstantConditions")
public class CloneFragment extends ListFragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MatrixAdapter adapter = new MatrixAdapter(getContext(), R.layout.list_layout_fragment, ((GlobalValues) getActivity().getApplication()).GetCompleteList());
        getListView().setDividerHeight(1);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if (((GlobalValues) getActivity().getApplication()).CanCreateVariable()) {
            Matrix m = ((GlobalValues) getActivity().getApplication()).GetCompleteList().get(position);
            Matrix clone;
            clone = m.ExactClone("Copy_" + m.GetName());
            ((GlobalValues) getActivity().getApplication()).GetCompleteList().add(clone);
            ((GlobalValues) getActivity().getApplication()).matrixAdapter.notifyDataSetChanged();
            Toast.makeText(getContext(), "Saved as Copy_" + m.GetName(), Toast.LENGTH_SHORT).show();
            NavigationView view = getActivity().findViewById(R.id.nav_view);
            view.setCheckedItem(R.id.Home);
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.MainContent, new MainActivityFragmentList());
            transaction.commit();
            ((MainActivity) getActivity()).SetMainActivity(true, getString(R.string.app_name), getString(R.string.MainSubtitle));
        } else {
            Toast.makeText(getContext(), R.string.LimitExceeds, Toast.LENGTH_SHORT).show();
        }


    }
}
