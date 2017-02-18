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

package com.softminds.matrixcalculatorpro.base_activities;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.softminds.matrixcalculatorpro.R;
import com.softminds.matrixcalculatorpro.base_fragments.MinorChooserFragment;

public class MinorChooser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isDark=preferences.getBoolean("DARK_THEME_KEY",false);
        if(isDark)
            setTheme(R.style.AppThemeDark);
        else
            setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.minor_chooser);
        TextView textView = (TextView)findViewById(R.id.ViewHints);
        textView.setText(R.string.ViewHint);
        int index =getIntent().getIntExtra("INDEX",-1);
        if(getSupportActionBar()!=null && index != (-1))
            getSupportActionBar().setTitle(((GlobalValues) getApplication()).GetCompleteList().get(index).GetName());
        MinorChooserFragment minorChooserFragment = new MinorChooserFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("INDEX",getIntent().getIntExtra("INDEX",-1));
        minorChooserFragment.setArguments(bundle);
        if(savedInstanceState==null){
            FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.FragmentContainer2,minorChooserFragment,"MINOR_TAG").commit();
        }

    }
}
