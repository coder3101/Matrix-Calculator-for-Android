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

package com.softminds.matrixcalculatorpro.base_activities;

import android.app.Application;

import com.softminds.matrixcalculatorpro.Function;
import com.softminds.matrixcalculatorpro.Matrix;
import com.softminds.matrixcalculatorpro.MatrixAdapter;
import com.softminds.matrixcalculatorpro.R;

import java.util.ArrayList;

public class GlobalValues extends Application {

   private ArrayList<Matrix> createdValues=new ArrayList<>();
    public Matrix current_editing = null;
    public ArrayList<Matrix> MatrixQueue = new ArrayList<>();

    private int LAST_INDEX=0; //LastIndex of ArrayList

    public int AutoSaved =1; //To automatically name the saved result

    private Function EndUserFunction;

    public MatrixAdapter matrixAdapter;

    public void AddToGlobal(Matrix mk)
    {
        createdValues.add(mk);
        if(matrixAdapter!=null)
            matrixAdapter.notifyDataSetChanged();
        LAST_INDEX++;
    }
    public void AddResultToGlobal(Matrix mk)
    {
        AddToGlobal(mk);
        AutoSaved++;
    }
    public ArrayList<Matrix> GetCompleteList()
    {

        return createdValues;
    }
    public void ClearAllMatrix()
    {
        createdValues.clear();
        matrixAdapter.notifyDataSetChanged();
        LAST_INDEX=0;
    }
    public int GetLastIndex()
    {
        return LAST_INDEX;
    }
    public boolean hasProfainity(String s)
    {
        String buffer=s.toUpperCase();
        String values[]= getResources().getStringArray(R.array.Words);
        for(int i=0;i<values.length;i++)
            if(buffer.contains(values[i].toUpperCase()))
                return true;

        return false;
    }
    public void SendToGlobal(Function function){
        EndUserFunction = function;
    }
    public Function getFunction(){
        return EndUserFunction;
    }

}
