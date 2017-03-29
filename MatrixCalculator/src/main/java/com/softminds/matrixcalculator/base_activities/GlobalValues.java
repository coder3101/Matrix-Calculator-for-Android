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

package com.softminds.matrixcalculator.base_activities;

import android.app.Application;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.softminds.matrixcalculator.Function;
import com.softminds.matrixcalculator.Matrix;
import com.softminds.matrixcalculator.MatrixAdapter;
import com.softminds.matrixcalculator.R;

import java.util.ArrayList;

public class GlobalValues extends Application {

    private ArrayList<Matrix> createdValues = new ArrayList<>();
    public ArrayList<Matrix> MatrixQueue = new ArrayList<>();

    public  Matrix current_editing = null;
    public boolean AdLoaded = false;

    private int LAST_INDEX = 0; //LastIndex of ArrayList

    public int AutoSaved = 1; //To automatically name the saved result

    private Function EndUserFunction;

    public MatrixAdapter matrixAdapter;

    private boolean Status=false;

    public void AddToGlobal(Matrix mk)
    {
        createdValues.add(mk);
        if(matrixAdapter!=null)
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

    public int GetLastIndex() {
        return LAST_INDEX;
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

    public boolean CanCreateVariable(){
        if(DonationKeyFound())
            return true;
        if(AdLoaded){
            return  (GetCompleteList().size()<4);
        }
        else{
             return (GetCompleteList().size()<3);
        }
    }

    public boolean DonationKeyFound(){

        return Status;
    }


    public void SetDonationKeyStatus() {
        try{
            PackageManager packageManager = getPackageManager();
            packageManager.getPackageInfo("com.softminds.matrixcalculator.pro.key",0);
            //Key is Installed
            //Checking the Authenticity of the Key
            if(packageManager.checkSignatures(getPackageName(),
                    "com.softminds.matrixcalculator.pro.key")
                    ==PackageManager.SIGNATURE_MATCH)
                Status = true; //Both are Signed by me, Let User Use the Pro Features.
        }catch (PackageManager.NameNotFoundException e)
        {
            Status = false;
        }
    }
}
