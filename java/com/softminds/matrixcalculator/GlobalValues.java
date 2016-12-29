/*
MIT License

Copyright (c) 2016 Ashar Khan

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
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
