/*
 * Copyright (c) 2018. <ashar786khan@gmail.com>
 * This file is part of Application 's Android Application.
 * Application' s Android Application is free software : you can redistribute it and/or modify
 * it under the terms of GNU General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 *
 * This Application is distributed in the hope that it will be useful
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General  Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with this Source File.
 *  If not, see <http:www.gnu.org/licenses/>.
 */

package com.softminds.matrixcalculator.OperationFragments;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.softminds.matrixcalculator.GlobalValues;
import com.softminds.matrixcalculator.MatrixAdapter;
import com.softminds.matrixcalculator.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NormFreb extends ListFragment {


    public NormFreb() {
        // Required empty public constructor
    }


    @Override
    public void onActivityCreated(Bundle savedInstances) {
        super.onActivityCreated(savedInstances);
        MatrixAdapter MatriXadapter = new MatrixAdapter(getActivity(), R.layout.list_layout_fragment, ((GlobalValues) getActivity().getApplication()).GetCompleteList());
        getListView().setDividerHeight(1);
        setListAdapter(MatriXadapter);
    }

    @Override
    public void onListItemClick(ListView L, View V, int position, long id) {
        final int pos = position;
        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext());
        builder.setPositiveButton(R.string.copy, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ClipboardManager clipboardManager = (ClipboardManager) getActivity()
                        .getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("NORM_FREB_RES", GetText(((GlobalValues) getActivity().getApplication()).GetCompleteList().get(pos).getNormFrobenious()));
                clipboardManager.setPrimaryClip(clipData);
                if (clipboardManager.hasPrimaryClip()) {
                    Toast.makeText(getContext(), R.string.CopyToClip, Toast.LENGTH_SHORT).show();
                } else
                    Log.d("ClipData", "Failed to set to Clip board");
                dialogInterface.dismiss();
            }
        });

        builder.setNeutralButton(R.string.Done, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setMessage("Frebenious Norm is : " + GetText(((GlobalValues) getActivity().getApplication()).GetCompleteList().get(position).getNormFrobenious()));
        builder.setTitle(R.string.normFreb);
        builder.setCancelable(false);
        builder.show();
    }

    private String GetText(double res) {

        if (!PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("DECIMAL_USE", true)) {
            DecimalFormat decimalFormat = new DecimalFormat("###############");
            return decimalFormat.format(res);
        } else {
            switch (Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(getContext()).getString("ROUNDIND_INFO", "0"))) {
                case 0:
                    DecimalFormat zeroth = new DecimalFormat("########.######");
                    return zeroth.format(res);
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
                    DecimalFormat fourth = new DecimalFormat("########.#####");
                    return fourth.format(res);
            }
        }
    }

}
