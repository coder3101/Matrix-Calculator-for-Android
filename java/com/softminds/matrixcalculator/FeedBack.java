package com.softminds.matrixcalculator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FeedBack extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isDark=preferences.getBoolean("DARK_THEME_KEY",false);
        if(isDark)
            setTheme(R.style.AppThemeDark);
        else
            setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed_back);
        final EditText editText=(EditText) findViewById(R.id.FeedT);
        Button Confirm = (Button) findViewById(R.id.Confirm);
        Button Not = (Button) findViewById(R.id.NotNow);
        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(((GlobalValues)getApplication()).hasProfainity(editText.getText().toString()))
                {
                    final Snackbar a;
                    a=Snackbar.make(findViewById(R.id.feed_back),R.string.Warning5,Snackbar.LENGTH_INDEFINITE);
                    a.show();
                    a.setAction(R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            a.dismiss();
                        }
                    });

                }
                else
                {
                    if(editText.getText().toString().isEmpty())
                        Toast.makeText(getApplicationContext(),R.string.Warning6,Toast.LENGTH_LONG).show();
                    else {
                        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "ashar786khan@gmail.com", null));
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback for the Matrix Calculator");
                        intent.putExtra(Intent.EXTRA_TEXT, editText.getText().toString());
                        startActivity(Intent.createChooser(intent, "Send Via"));
                    }
                }
            }
        });
        Not.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Okay Some Other Day",Toast.LENGTH_SHORT).show();
                finish();

            }
        });
    }

}
