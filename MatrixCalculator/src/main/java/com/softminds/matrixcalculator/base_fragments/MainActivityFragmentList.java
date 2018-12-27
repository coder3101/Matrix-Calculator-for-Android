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
package com.softminds.matrixcalculator.base_fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.softminds.matrixcalculator.GlobalValues;
import com.softminds.matrixcalculator.base_activities.ViewCreatedMatrix;
import com.softminds.matrixcalculator.MatrixAdapter;
import com.softminds.matrixcalculator.R;
import com.softminds.matrixcalculator.dialog_activity.OrderChanger;
import com.softminds.matrixcalculator.dialog_activity.RenameCreated;

@SuppressWarnings("ConstantConditions")
public class MainActivityFragmentList extends ListFragment {

    int position1 = -1;

    @Override
    public void onActivityCreated(Bundle savedInstances) {
        super.onActivityCreated(savedInstances);

        ((GlobalValues) getActivity().getApplication()).matrixAdapter = new MatrixAdapter(getActivity(), R.layout.list_layout_fragment,
                ((GlobalValues) getActivity().getApplication()).GetCompleteList());
        getListView().setDividerHeight(0);
        setListAdapter(((GlobalValues) getActivity().getApplication()).matrixAdapter);

        registerForContextMenu(getListView());

    }

    @Override
    public void onListItemClick(ListView L, View V, int position, long id) { //Last Index from Global is 1 more that the position provided

        Intent intent = new Intent(getActivity().getApplication(), ViewCreatedMatrix.class);
        intent.putExtra("INDEX", position);
        startActivity(intent);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.quick_main_action, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.QuickDelete:
                ((GlobalValues) getActivity().getApplication()).GetCompleteList().remove(info.position);
                ((GlobalValues) getActivity().getApplication()).matrixAdapter.notifyDataSetChanged();
                Toast.makeText(getContext(), R.string.Deleted, Toast.LENGTH_SHORT).show();
                break;
            case R.id.QuickRename:
                position1 = info.position;
                Intent rename = new Intent(getContext(), RenameCreated.class);
                rename.putExtra("TITLE_OF_THIS_FORMATION", ((GlobalValues) getActivity().
                        getApplication()).GetCompleteList().get(info.position).getName());
                startActivityForResult(rename, 100);
                break;
            case R.id.QuickChange:
                Intent order = new Intent(getContext(), OrderChanger.class);
                order.putExtra("INDEX_OF_SELECTED_MATRIX", info.position);
                startActivityForResult(order, 12);

        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestcode, int resultCode, Intent data) {
        super.onActivityResult(requestcode, resultCode, data);
        if (resultCode == 100) {
            ((GlobalValues) getActivity().getApplication()).GetCompleteList().get(position1).
                    setName(data.getStringExtra("NEW_NAME_FOR_THIS_MATRIX"));
            ((GlobalValues) getActivity().getApplication()).matrixAdapter.notifyDataSetChanged();

        }
        if (resultCode == 12)
            Toast.makeText(getContext(), R.string.ChangedOrder, Toast.LENGTH_SHORT).show();
    }
}
