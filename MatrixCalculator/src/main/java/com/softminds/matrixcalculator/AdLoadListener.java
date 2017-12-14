package com.softminds.matrixcalculator;

import android.support.v7.widget.CardView;
import android.view.View;

import com.google.android.gms.ads.AdListener;

public class AdLoadListener extends AdListener {

    private CardView adHolder = null;

    public AdLoadListener(CardView cardView) {
        adHolder = cardView;
    }

    @Override
    public void onAdLoaded() {
        super.onAdLoaded();
        adHolder.setVisibility(View.VISIBLE);
    }
}
