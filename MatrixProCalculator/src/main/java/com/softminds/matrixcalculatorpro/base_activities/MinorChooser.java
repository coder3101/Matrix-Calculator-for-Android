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
