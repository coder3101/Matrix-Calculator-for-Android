/*
MIT License

Copyright (c) 2016 Ashar Khan

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/package com.softminds.matrixcalculator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RenameCreated extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isDark=preferences.getBoolean("DARK_THEME_KEY",false);
        if(isDark)
            setTheme(R.style.AppThemeDarkDialog);
        else
            setTheme(R.style.AppThemeDialog);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rename_created);

        final EditText  e = (EditText) findViewById(R.id.RenameValue);
        e.setText(getIntent().getStringExtra("TITLE_OF_THIS_FORMATION"));
        Button yes = (Button) findViewById(R.id.ConfirmRename);
        Button no = (Button) findViewById(R.id.CancelRename);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(e.getText().toString().isEmpty())
                    Toast.makeText(getApplicationContext(),R.string.NoValue,Toast.LENGTH_SHORT).show();
                else {
                    if(((GlobalValues)getApplication()).hasProfainity(e.getText().toString()))
                    {
                        Toast.makeText(getApplicationContext(),R.string.Warning7,Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Intent intent = new Intent();
                        intent.putExtra("NEW_NAME_FOR_THIS_MATRIX", e.getText().toString());
                        setResult(100, intent);
                        finish();
                    }
                }

            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
