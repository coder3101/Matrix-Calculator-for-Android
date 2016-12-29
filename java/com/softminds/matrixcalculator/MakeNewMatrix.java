package com.softminds.matrixcalculator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

public class MakeNewMatrix extends AppCompatActivity {
    final int RESCODE=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isDark=preferences.getBoolean("DARK_THEME_KEY",false);
        if(isDark)
            setTheme(R.style.AppThemeDarkDialog);
        else
            setTheme(R.style.AppThemeDialog);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_matrix);
        Button button = (Button) findViewById(R.id.buttonMake);
        final EditText editText = (EditText) findViewById(R.id.MatrixName);
        editText.setSingleLine();
        final Spinner Typespinner = (Spinner) findViewById(R.id.MatType);
        final NumberPicker RowSpinner = (NumberPicker) findViewById(R.id.RowOrder);
        RowSpinner.setMinValue(1);
        RowSpinner.setMaxValue(9);
        RowSpinner.setValue(3);
        final NumberPicker ColSpinner = (NumberPicker) findViewById(R.id.ColOrder);
        ColSpinner.setMinValue(1);
        ColSpinner.setMaxValue(9);
        ColSpinner.setValue(3);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (NoError()) {
                    Bundle bundle = new Bundle();
                    bundle.putString("TYPE",Typespinner.getSelectedItem().toString());
                    bundle.putString("NAME",editText.getText().toString());
                    bundle.putInt("ROW",RowSpinner.getValue());
                    bundle.putInt("COL",ColSpinner.getValue());
                    Intent intent;
                    intent = new Intent(getApplication(), FillingMatrix.class);
                    intent.putExtras(bundle);
                    startActivityForResult(intent,RESCODE);

                }
                else
                    Toast.makeText(getApplication(),R.string.Warning7,Toast.LENGTH_SHORT).show();


            }

            private boolean NoError() {
                if (editText.getText().toString().isEmpty()) {
                    Toast.makeText(getApplication(), R.string.Warning1, Toast.LENGTH_LONG).show();
                    return false;
                }
                if ((Typespinner.getSelectedItem().toString().equals("Identity") || Typespinner.getSelectedItem().toString().equals("Diagonal")) && (RowSpinner.getValue() != ColSpinner.getValue())) {
                    Toast.makeText(getApplicationContext(), R.string.NotSquare, Toast.LENGTH_LONG).show();
                    return false;
                }
                return !((GlobalValues)getApplication()).hasProfainity(editText.getText().toString());

            }


        });



    }
    protected void onActivityResult(int requestcode,int resultCode,Intent data)
    {
        super.onActivityResult(resultCode,resultCode,data);
        if(resultCode==RESCODE)
        {
            setResult(1,data);
            finish();
        }

    }
}
