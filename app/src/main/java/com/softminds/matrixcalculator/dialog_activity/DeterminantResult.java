package com.softminds.matrixcalculator.dialog_activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.softminds.matrixcalculator.R;

public class DeterminantResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isDark=preferences.getBoolean("DARK_THEME_KEY",false);
        if(isDark)
            setTheme(R.style.AppThemeDarkDialog);
        else
            setTheme(R.style.AppThemeDialog);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_confirmation);
        Button CopyIt= (Button) findViewById(R.id.ActionOkay);
        Button ignore = (Button) findViewById(R.id.ActionCancel);
        TextView result = (TextView) findViewById(R.id.MessageDisplay);
        result.setText(ConvertToNormal(getIntent().getExtras().getDouble("RESULTANT",0)));
        CopyIt.setText(R.string.copy);
        ignore.setText(R.string.Done);


        ignore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        CopyIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("DETERMINANT_RES",
                        ConvertToNormal(getIntent().getExtras().getDouble("RESULTANT",0)));
                clipboardManager.setPrimaryClip(clipData);
                if(clipboardManager.hasPrimaryClip())
                    Toast.makeText(getApplicationContext(),R.string.CopyToClip,Toast.LENGTH_SHORT).show();
                else
                    Log.d("Clipboard","Unable to set PrimaryClip");
                finish();
            }
        });
    }

    private String ConvertToNormal (double res){
       return String.valueOf(res);
    }
}
