package com.softminds.matrixcalculator.OperationFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softminds.matrixcalculator.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SwapFragment extends Fragment {


    public SwapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.swap_fragment, container, false);
    }

}
