package com.softminds.matrixcalculatorpro.OperationFragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;


import com.softminds.matrixcalculatorpro.Matrix;
import com.softminds.matrixcalculatorpro.MatrixAdapter;
import com.softminds.matrixcalculatorpro.R;
import com.softminds.matrixcalculatorpro.base_activities.GlobalValues;
import com.softminds.matrixcalculatorpro.base_activities.MinorChooser;

import java.util.ArrayList;


public class MinorFragment extends ListFragment {

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayList<Matrix> SquareList = new ArrayList<>();
        for(int i=0;i<((GlobalValues)getActivity().getApplication()).GetCompleteList().size();i++){
            if(((GlobalValues)getActivity().getApplication()).GetCompleteList().get(i).is_squareMatrix())
                SquareList.add(((GlobalValues)getActivity().getApplication()).GetCompleteList().get(i));
        }
        MatrixAdapter squareadapter = new MatrixAdapter(getContext(),R.layout.list_layout_fragment,SquareList);
        getListView().setDividerHeight(1);
        setListAdapter(squareadapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent =new Intent(getActivity().getApplication(),MinorChooser.class);
        intent.putExtra("INDEX",position);
        startActivity(intent);
    }
}
