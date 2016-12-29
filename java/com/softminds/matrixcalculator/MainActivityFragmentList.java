package com.softminds.matrixcalculator;


import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainActivityFragmentList extends ListFragment {

    @Override
    public void onActivityCreated(Bundle savedInstances)
    {
        super.onActivityCreated(savedInstances);
        ((GlobalValues)getActivity().getApplication()).matrixAdapter= new MatrixAdapter(getActivity(),R.layout.list_layout_fragment,
                ((GlobalValues) getActivity().getApplication()).GetCompleteList());
        getListView().setDividerHeight(1);
        setListAdapter(((GlobalValues)getActivity().getApplication()).matrixAdapter);

    }

    @Override
    public void onListItemClick(ListView L,View V,int position,long id)
    { //Last Index from Global is 1 more that the position provided

        Log.d("Index is ",String.valueOf(position));
        Log.d("Last Index is ",String.valueOf(((GlobalValues)getActivity().getApplication()).GetLastIndex()));
        Intent intent =new Intent(getActivity().getApplication(),ViewCreatedMatrix.class);
        Bundle index = new Bundle();
        index.putInt("INDEX",position);
        intent.putExtras(index);
        startActivity(intent);
    }

}
