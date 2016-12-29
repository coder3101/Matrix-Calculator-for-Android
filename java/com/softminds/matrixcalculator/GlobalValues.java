package com.softminds.matrixcalculator;

import android.app.Application;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by ashar on 27/12/16.
 */

public class GlobalValues extends Application {
   private ArrayList<Matrix> createdValues=new ArrayList<>();
    private int LAST_INDEX=0;
    public MatrixAdapter matrixAdapter;
    public Matrix GetLastMatrix()
    {
        if(!createdValues.isEmpty())
        {
            return (createdValues.get(LAST_INDEX));
        }
        else
            return null;
    }
    public void AddToGlobal(Matrix mk)
    {
        createdValues.add(mk);
        matrixAdapter.notifyDataSetChanged();
        LAST_INDEX++;
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
}
