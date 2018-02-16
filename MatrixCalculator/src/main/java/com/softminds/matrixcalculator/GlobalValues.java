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

package com.softminds.matrixcalculator;

import android.app.Application;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Objects;

public class GlobalValues extends Application {

    private ArrayList<Matrix> createdValues = new ArrayList<>();
    public ArrayList<Matrix> MatrixQueue = new ArrayList<>();

    public Matrix current_editing = null;
    public boolean AdLoaded = false;

    private int LAST_INDEX = 0; //LastIndex of ArrayList

    public int AutoSaved = 1; //To automatically name the saved result

    private Function EndUserFunction;

    public MatrixAdapter matrixAdapter;

    private boolean Status = false;

    public boolean Promotion = false;

    public boolean ThisSession = true;

    private String TAG = this.getClass().getSimpleName();

    public void AddToGlobal(Matrix mk) {
        createdValues.add(mk);
        if (matrixAdapter != null)
            matrixAdapter.notifyDataSetChanged();
        LAST_INDEX++;
    }

    public void AddResultToGlobal(Matrix mk) {
        AddToGlobal(mk);
        AutoSaved++;
    }

    public ArrayList<Matrix> GetCompleteList() {

        return createdValues;
    }

    public void ClearAllMatrix() {
        createdValues.clear();
        matrixAdapter.notifyDataSetChanged();
        LAST_INDEX = 0;
    }

    public boolean hasProfainity(String s) {
        String buffer = s.toUpperCase();
        String values[] = getResources().getStringArray(R.array.Words);
        for (String value : values)
            if (buffer.contains(value.toUpperCase()))
                return true;

        return false;
    }

    public void SendToGlobal(Function function) {
        EndUserFunction = function;
    }

    public Function getFunction() {
        return EndUserFunction;
    }

    public boolean CanCreateVariable() {
        if (DonationKeyFound())
            return true;
        if (AdLoaded) {
            return (GetCompleteList().size() < 4);
        } else {
            return (GetCompleteList().size() < 3);
        }
    }

    public boolean DonationKeyFound() {
        return Status;
    }

    public void UpdateStatus(boolean status) {
        Status = status;
        if (Status)
            Promotion = true;

        //user is using promotional offer
    }


    public void SetDonationKeyStatus() {

        if (BuildConfig.DEBUG) {
            Status = true;
            return;
            //if debug mode, avoid the advertisements and use pro features
        }
        try {
            PackageManager packageManager = getPackageManager();
            packageManager.getPackageInfo("com.softminds.matrixcalculator.pro.key", 0);
            //Key is Installed
            //Checking the Authenticity of the Key
            if (packageManager.checkSignatures(getPackageName(),
                    "com.softminds.matrixcalculator.pro.key")
                    == PackageManager.SIGNATURE_MATCH) {
                Log.d(TAG, "The Signature of Key Matched with Application");
                String manager = packageManager.getInstallerPackageName("com.softminds.matrixcalculator.pro.key");
                if (manager != null) {
                    //Status = true;
                    Log.d(TAG, manager);
                    if (Objects.equals(manager, "com.android.vending")) {
                        Status = true;
                    } else {
                        Status = false;
                        Toast.makeText(this, R.string.clear_warn_invalid_install, Toast.LENGTH_LONG).show();
                    }
                }
                //the Key is Genuine and was Installed from Play
            }
        } catch (Exception e) {
            Status = false;
        }
    }

}
