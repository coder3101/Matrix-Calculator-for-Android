package com.softminds.matrixcalculator;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

public class RatingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isDark=preferences.getBoolean("DARK_THEME_KEY",false);
        if(isDark)
            setTheme(R.style.AppThemeDark);
        else
            setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        final RatingBar ratingBar = (RatingBar) findViewById(R.id.RatingStars);
        Button button = (Button) findViewById(R.id.Ratebutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast toast = Toast.makeText(getApplicationContext(),"You Rated me " + ratingBar.getRating() + " Star(s). ",Toast.LENGTH_LONG);
                toast.setGravity(0,0,0);
                toast.show();
                final Snackbar snackbar = Snackbar.make(view,"Thanks for Your Response. Still Under Development",Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction(R.string.DismissSnackbar, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        snackbar.dismiss();
                    }
                });
                snackbar.show();
            }
        });
    }
}
