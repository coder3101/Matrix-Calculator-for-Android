/*
MIT License

Copyright (c) 2016 Ashar Khan

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

package com.softminds.matrixcalculator;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class Settings extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstances) {
        super.onCreate(savedInstances);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();


    }

    public static class MyPreferenceFragment extends PreferenceFragment {

        @Override
        public void onCreate(final Bundle savedInstances) {
            super.onCreate(savedInstances);
            addPreferencesFromResource(R.xml.preferences);
            Preference preference = findPreference("DARK_THEME_KEY");
            preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                   getActivity().recreate();
                    return true;
                }
            });
           Preference preference1 =findPreference("RESTORE_ALL");
            if(preference1!=null)
            {
                preference1.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                    @Override
                    public boolean onPreferenceClick(Preference preference) {
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                        SharedPreferences.Editor editor = preferences.edit();
                        //UserInterface
                        editor.putBoolean("DARK_THEME_KEY",false);
                        editor.putString("CARD_CHANGE_KEY","#bdbdbd");
                        editor.putString("ELEVATE_AMOUNT","4");
                        editor.putBoolean("EXTRA_SMALL_FONT",false);
                        //Calculation
                        editor.putBoolean("NO_FRACTION_ENABLED",false);
                        editor.putString("ROUNDIND_INFO","0");
                        //Random Numbers
                        editor.putBoolean("SIGNED_RANDOM",false);
                        editor.putString("MAX_INT","100");
                        editor.putString("MIN_INT_KEY","0");
                        //extra Settings
                        editor.putBoolean("AUTO_SAVE_RESULT",false);
                        editor.putBoolean("AUTO_TOAST_ENABLER",false);
                        //apply changes
                        editor.apply();
                        //close activity
                        getActivity().recreate();
                        Toast.makeText(getActivity(),R.string.RestoreComplete,Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
            }


        }
    }
}
