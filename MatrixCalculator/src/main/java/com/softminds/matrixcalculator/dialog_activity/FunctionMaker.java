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


package com.softminds.matrixcalculator.dialog_activity;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.text.style.SuperscriptSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.softminds.matrixcalculator.Function;
import com.softminds.matrixcalculator.Matrix;
import com.softminds.matrixcalculator.R;
import com.softminds.matrixcalculator.GlobalValues;


public class FunctionMaker extends AppCompatActivity {

    TextView CurrentTermCoefficient = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final boolean isDark = preferences.getBoolean("DARK_THEME_KEY", false);
        if (isDark)
            setTheme(R.style.AppThemeDarkDialog);
        else
            setTheme(R.style.AppThemeDialog);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function_maker);


        //Grab all Control References
        Button proceed = findViewById(R.id.ConfirmMakeFunction);
        final NumberPicker expo = findViewById(R.id.FunctionMakerExponent);
        final NumberPicker deg = findViewById(R.id.FunctionMakerDegree);
        final EditText coefficient = findViewById(R.id.MainCoefficientFiller);
        //Put Properties to Number Picker
        expo.setValue(1);
        expo.setMinValue(1);
        expo.setMaxValue(9);
        deg.setMinValue(1);
        deg.setMaxValue(7);
        deg.setValue(1);
        //Grab Default References
        final TextView ConstSign = findViewById(R.id.ConstantSign);
        final TextView FirstAuto = findViewById(R.id.FirstXAuto);
        FirstAuto.setText(ConvertToExponent(getString(R.string.X)));
        final TextView Const = findViewById(R.id.ConstantValueFunction);

        //Grab all Inactive TextViews
        final TextView Term1 = findViewById(R.id.CreatedXAuto1);
        Term1.setText(null);
        Term1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                coefficient.setText(null);
                if (CurrentTermCoefficient != null) {
                    if (isDark)
                        CurrentTermCoefficient.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.DarkcolorPrimaryDark));
                    else
                        CurrentTermCoefficient.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.grey));
                }
                CurrentTermCoefficient = Term1;
                CurrentTermCoefficient.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.cardColor));
            }
        });
        final TextView Term2 = findViewById(R.id.CreatedXAuto2);
        Term2.setText(null);
        Term2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                coefficient.setText(null);
                if (CurrentTermCoefficient != null) {
                    if (isDark)
                        CurrentTermCoefficient.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.DarkcolorPrimaryDark));
                    else
                        CurrentTermCoefficient.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.grey));
                }
                CurrentTermCoefficient = Term2;
                CurrentTermCoefficient.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.cardColor));
            }
        });
        final TextView Term3 = findViewById(R.id.CreatedXAuto3);
        Term3.setText(null);
        Term3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                coefficient.setText(null);
                if (CurrentTermCoefficient != null) {
                    if (isDark)
                        CurrentTermCoefficient.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.DarkcolorPrimaryDark));
                    else
                        CurrentTermCoefficient.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.grey));
                }
                CurrentTermCoefficient = Term3;
                CurrentTermCoefficient.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.cardColor));
            }
        });
        final TextView Term4 = findViewById(R.id.CreatedXAuto4);
        Term4.setText(null);
        Term4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                coefficient.setText(null);
                if (CurrentTermCoefficient != null) {
                    if (isDark)
                        CurrentTermCoefficient.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.DarkcolorPrimaryDark));
                    else
                        CurrentTermCoefficient.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.grey));
                }
                CurrentTermCoefficient = Term4;
                CurrentTermCoefficient.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.cardColor));
            }
        });
        final TextView Term5 = findViewById(R.id.CreatedXAuto5);
        Term5.setText(null);
        Term5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                coefficient.setText(null);
                if (CurrentTermCoefficient != null) {
                    if (isDark)
                        CurrentTermCoefficient.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.DarkcolorPrimaryDark));
                    else
                        CurrentTermCoefficient.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.grey));
                }
                CurrentTermCoefficient = Term5;
                CurrentTermCoefficient.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.cardColor));
            }
        });
        final TextView Term6 = findViewById(R.id.CreatedXAuto6);
        Term6.setText(null);
        Term6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                coefficient.setText(null);
                if (CurrentTermCoefficient != null) {
                    if (isDark)
                        CurrentTermCoefficient.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.DarkcolorPrimaryDark));
                    else
                        CurrentTermCoefficient.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.grey));
                }
                CurrentTermCoefficient = Term6;
                CurrentTermCoefficient.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.cardColor));
            }
        });


        //All Inactive Signs and Setting Click to Change Sign
        final TextView Sign1 = findViewById(R.id.AutoSign1);
        Sign1.setText(null);
        Sign1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignChanger(Sign1);
            }
        });
        final TextView Sign2 = findViewById(R.id.AutoSign2);
        Sign2.setText(null);
        Sign2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignChanger(Sign2);
            }
        });
        final TextView Sign3 = findViewById(R.id.AutoSign3);
        Sign3.setText(null);
        Sign3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignChanger(Sign3);
            }
        });
        final TextView Sign4 = findViewById(R.id.AutoSign4);
        Sign4.setText(null);
        Sign4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignChanger(Sign4);
            }
        });
        final TextView Sign5 = findViewById(R.id.AutoSign5);
        Sign5.setText(null);
        Sign5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignChanger(Sign5);
            }
        });
        final TextView Sign6 = findViewById(R.id.AutoSign6);
        Sign6.setText(null);
        Sign6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignChanger(Sign6);
            }
        });


        //Set Click Listener to each
        deg.setOnScrollListener(new NumberPicker.OnScrollListener() {
            @Override
            public void onScrollStateChange(NumberPicker numberPicker, int i) {
                //Degree maker will be here
                switch (numberPicker.getValue()) {
                    case 7:
                        Term2.setText(ConvertToExponent(getString(R.string.X)));
                        Sign2.setText("+");
                        Term6.setText(ConvertToExponent(getString(R.string.NewX) + String.valueOf(deg.getValue())));
                        Sign6.setText("+");
                        Term3.setText(ConvertToExponent(getString(R.string.X)));
                        Sign3.setText("+");
                        Term4.setText(ConvertToExponent(getString(R.string.X)));
                        Sign4.setText("+");
                        Term5.setText(ConvertToExponent(getString(R.string.X)));
                        Sign5.setText("+");
                        Term1.setText(ConvertToExponent(getString(R.string.X)));
                        Sign1.setText("+");
                        break;
                    case 6:
                        Term2.setText(ConvertToExponent(getString(R.string.X)));
                        Sign2.setText("+");
                        Term5.setText(ConvertToExponent(getString(R.string.NewX) + String.valueOf(deg.getValue())));
                        Sign5.setText("+");
                        Term3.setText(ConvertToExponent(getString(R.string.X)));
                        Sign3.setText("+");
                        Term4.setText(ConvertToExponent(getString(R.string.X)));
                        Sign4.setText("+");
                        Term1.setText(ConvertToExponent(getString(R.string.X)));
                        Sign1.setText("+");
                        Sign6.setText(null);
                        Term6.setText(null);
                        break;
                    case 5:
                        Term2.setText(ConvertToExponent(getString(R.string.X)));
                        Sign2.setText("+");
                        Term4.setText(ConvertToExponent(getString(R.string.NewX) + String.valueOf(deg.getValue())));
                        Sign4.setText("+");
                        Term3.setText(ConvertToExponent(getString(R.string.X)));
                        Sign3.setText("+");
                        Term1.setText(ConvertToExponent(getString(R.string.X)));
                        Sign1.setText("+");
                        Term5.setText(null);
                        Term6.setText(null);
                        Sign5.setText(null);
                        Sign6.setText(null);
                        break;
                    case 4:
                        Term2.setText(ConvertToExponent(getString(R.string.X)));
                        Sign2.setText("+");
                        Term3.setText(ConvertToExponent(getString(R.string.NewX) + String.valueOf(deg.getValue())));
                        Sign3.setText("+");
                        Term1.setText(ConvertToExponent(getString(R.string.X)));
                        Sign1.setText("+");
                        Term4.setText(null);
                        Term5.setText(null);
                        Term6.setText(null);
                        Sign4.setText(null);
                        Sign5.setText(null);
                        Sign6.setText(null);
                        break;
                    case 3:
                        Term1.setText(ConvertToExponent(getString(R.string.X)));
                        Sign1.setText("+");
                        Term2.setText(ConvertToExponent(getString(R.string.NewX) + String.valueOf(deg.getValue())));
                        Sign2.setText("+");
                        Term3.setText(null);
                        Term4.setText(null);
                        Term5.setText(null);
                        Term6.setText(null);
                        Sign3.setText(null);
                        Sign4.setText(null);
                        Sign5.setText(null);
                        Sign6.setText(null);
                        break;
                    case 2:
                        Term1.setText(ConvertToExponent(getString(R.string.NewX) + String.valueOf(deg.getValue())));
                        Sign1.setText("+");
                        Term2.setText(null);
                        Term3.setText(null);
                        Term4.setText(null);
                        Term5.setText(null);
                        Term6.setText(null);
                        Sign2.setText(null);
                        Sign3.setText(null);
                        Sign4.setText(null);
                        Sign5.setText(null);
                        Sign6.setText(null);
                        break;
                    case 1:
                        Term1.setText(null);
                        Sign1.setText(null);
                        Term2.setText(null);
                        Term3.setText(null);
                        Term4.setText(null);
                        Term5.setText(null);
                        Term6.setText(null);
                        Sign2.setText(null);
                        Sign3.setText(null);
                        Sign4.setText(null);
                        Sign5.setText(null);
                        Sign6.setText(null);
                        break;
                }
            }
        });
        expo.setOnScrollListener(new NumberPicker.OnScrollListener() {
            @Override
            public void onScrollStateChange(NumberPicker numberPicker, int i) {
                if (CurrentTermCoefficient != null) {
                    if (CurrentTermCoefficient.getText().toString().contains("x")) {
                        String order = CurrentTermCoefficient.getText().toString();
                        String SubString = order.substring(0, order.indexOf("x") + 1); //Get Everything except from Exponent
                        String SubString2 = SubString + String.valueOf(numberPicker.getValue());
                        CurrentTermCoefficient.setText(ConvertToExponent(SubString2));
                    }
                } else {
                    Toast.makeText(getApplicationContext(), R.string.Warning12, Toast.LENGTH_SHORT).show();
                    numberPicker.setValue(2);
                }
            }
        });

        //EditText KeyChange Listener
        coefficient.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (CurrentTermCoefficient == null) {
                    Toast.makeText(getApplicationContext(), R.string.Warning12, Toast.LENGTH_SHORT).show();
                    return true;
                } else {
                    if (CurrentTermCoefficient.getText().toString().contains("x")) {
                        String res = coefficient.getText().toString() + "x" + String.valueOf(expo.getValue());
                        CurrentTermCoefficient.setText(ConvertToExponent(res));
                    } else
                        CurrentTermCoefficient.setText(coefficient.getText().toString());
                    return false;
                }
            }
        });
        //default Changers
        ConstSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignChanger(ConstSign);
            }
        });
        //default Coefficient
        FirstAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                coefficient.setText(null);
                if (CurrentTermCoefficient != null) {
                    if (isDark)
                        CurrentTermCoefficient.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.DarkcolorPrimaryDark));
                    else
                        CurrentTermCoefficient.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.grey));
                }
                CurrentTermCoefficient = FirstAuto;
                FirstAuto.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.cardColor));
            }
        });
        //Constant changer
        Const.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                coefficient.setText(null);
                if (CurrentTermCoefficient != null) {
                    if (isDark)
                        CurrentTermCoefficient.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.DarkcolorPrimaryDark));
                    else
                        CurrentTermCoefficient.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.grey));
                }
                CurrentTermCoefficient = Const;
                Const.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.cardColor));
            }
        });

        //SetterListener

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Function EndUserCreation = GetMainFunction(deg.getValue(), Float.parseFloat
                        (Const.getText().toString()), SignReturner(ConstSign));
                ((GlobalValues) getApplication()).SendToGlobal(EndUserCreation);
                Log.d("Function is :", EndUserCreation.toString());
                setResult(1452);
                finish();

            }
        });
    }

    private void SignChanger(TextView tv) {
        if (tv.getText().toString().equals("+"))
            tv.setText("-");
        else
            tv.setText("+");
    }

    private SpannableStringBuilder ConvertToExponent(String s) { //This Function makes the Normal text into Exponents and base, position being the index of exponent
        int position = s.indexOf("x") + 1;
        SpannableStringBuilder builder = new SpannableStringBuilder(s);
        builder.setSpan(new SuperscriptSpan(), position, position + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new RelativeSizeSpan(0.50f), position, position + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }

    private Function GetMainFunction(int Term, float Const, boolean Sign) {
        if (!Sign) //if false means that  constant is negative
            Const = Const * (-1);
        Matrix Target = ((GlobalValues) getApplication()).GetCompleteList().get(getIntent().getIntExtra("MATRIX_GLOBAL_INDEX", 0));
        Function EndUserFunction = new Function(Target.GetRow(), Target.GetCol(), Const);
        //Grab All Sign and Terms
        TextView term1 = findViewById(R.id.CreatedXAuto1);
        TextView term2 = findViewById(R.id.CreatedXAuto2);
        TextView term3 = findViewById(R.id.CreatedXAuto3);
        TextView term4 = findViewById(R.id.CreatedXAuto4);
        TextView term5 = findViewById(R.id.CreatedXAuto5);
        TextView term6 = findViewById(R.id.CreatedXAuto6);
        TextView term = findViewById(R.id.FirstXAuto);
        //All Signs Grabbing
        TextView sign1 = findViewById(R.id.AutoSign1);
        TextView sign2 = findViewById(R.id.AutoSign2);
        TextView sign3 = findViewById(R.id.AutoSign3);
        TextView sign4 = findViewById(R.id.AutoSign4);
        TextView sign5 = findViewById(R.id.AutoSign5);
        TextView sign6 = findViewById(R.id.AutoSign6);

        //Extract terms and Push them to Function
        if (Term == 7) {
            String val = term6.getText().toString();
            EndUserFunction.AddTerms(GetCoefficient(val), GetExponent(val), true);
        }
        if (Term >= 6) {
            boolean sign = sign6 == null || SignReturner(sign6);
            String val = term5.getText().toString();
            EndUserFunction.AddTerms(GetCoefficient(val), GetExponent(val), sign);
        }
        if (Term >= 5) {
            boolean sign = sign5 == null || SignReturner(sign5);
            String val = term4.getText().toString();
            EndUserFunction.AddTerms(GetCoefficient(val), GetExponent(val), sign);
        }
        if (Term >= 4) {
            boolean sign = sign4 == null || SignReturner(sign4);
            String val = term3.getText().toString();
            EndUserFunction.AddTerms(GetCoefficient(val), GetExponent(val), sign);
        }
        if (Term >= 3) {
            boolean sign = sign3 == null || SignReturner(sign3);
            String val = term2.getText().toString();
            EndUserFunction.AddTerms(GetCoefficient(val), GetExponent(val), sign);
        }
        if (Term >= 2) {
            boolean sign = sign2 == null || SignReturner(sign2);
            String val = term1.getText().toString();
            EndUserFunction.AddTerms(GetCoefficient(val), GetExponent(val), sign);
        }
        if (term1.getText() != null) {
            boolean sign = sign1 == null || SignReturner(sign1);
            String val = term.getText().toString();
            EndUserFunction.AddTerms(GetCoefficient(val), GetExponent(val), sign);
        }
        return EndUserFunction;
    }

    private boolean SignReturner(TextView v) {
        return !(v.getText().toString().contentEquals("-"));
    }

    private int GetExponent(String s) {
        if (s.length() > 1)
            return Integer.parseInt(s.substring(s.indexOf("x") + 1, s.indexOf("x") + 2));
        else
            return 1;
    }

    private float GetCoefficient(String s) {
        if (s.length() > 2)
            return Float.parseFloat(s.substring(0, s.indexOf("x")));
        else
            return 1.0f;
    }

}
