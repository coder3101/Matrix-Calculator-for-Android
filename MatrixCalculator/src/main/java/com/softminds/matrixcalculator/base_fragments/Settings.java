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
package com.softminds.matrixcalculator.base_fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.widget.Toast;

import com.softminds.matrixcalculator.R;
import com.softminds.matrixcalculator.GlobalValues;

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
            if(((GlobalValues)getActivity().getApplication()).DonationKeyFound())
                UpgradePro();
            Preference preference = findPreference("DARK_THEME_KEY");
            preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    if(PreferenceManager.getDefaultSharedPreferences(getActivity()).getBoolean("DARK_THEME_KEY",false))
                            preference.getEditor().putString("CARD_CHANGE_KEY","#006064").apply();
                    else
                        preference.getEditor().putString("CARD_CHANGE_KEY","#bdbdbd").apply();

                   getActivity().recreate();
                    return true;
                }
            });
            Preference preference3 = findPreference("DECIMAL_USE");
            preference3.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    if(!((SwitchPreference) findPreference("NO_FRACTION_ENABLED")).isChecked()) {
                        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();
                        editor.putBoolean("NO_FRACTION_ENABLED", true);
                        editor.apply();
                        Toast.makeText(getActivity(),R.string.AutoEnabled,Toast.LENGTH_SHORT).show();
                        getActivity().recreate();
                    }
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
                        editor.putBoolean("SMART_FIT_KEY",false);
                        editor.putBoolean("EXTRA_SMALL_FONT",false);
                        //Calculation
                        editor.putBoolean("NO_FRACTION_ENABLED",false);
                        editor.putString("ROUNDIND_INFO","0");
                        editor.putBoolean("DECIMAL_USE",true);
                        //Random Numbers
                        editor.putBoolean("SIGNED_RANDOM",false);
                        editor.putString("MAX_INT","100");
                        editor.putString("MIN_INT_KEY","0");
                        //extra Settings
                        editor.putBoolean("AUTO_TOAST_ENABLER",false);
                        editor.putBoolean("TRANSPOSE_PROMPT",true);
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

        private void UpgradePro() {
            Preference darkKey = findPreference("DARK_THEME_KEY");
                darkKey.setEnabled(true);
                darkKey.setTitle(R.string.DarkThemePro);
            Preference SmallFonts = findPreference("EXTRA_SMALL_FONT");
                SmallFonts.setEnabled(true);
                SmallFonts.setTitle(R.string.ExtraSmallFontTPro);
            Preference Rounding = findPreference("ROUNDIND_INFO");
                Rounding.setTitle(R.string.EnableRoundingPro);
                Rounding.setEnabled(true);
            Preference Max = findPreference("MAX_INT");
                Max.setEnabled(true);
                Max.setTitle(R.string.MaximumTPro);
            Preference Min = findPreference("MIN_INT_KEY");
                Min.setTitle(R.string.MinimumTPro);
                Min.setEnabled(true);
        }
    }
}
