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
package com.softminds.matrixcalculatorpro;


import android.app.ProgressDialog;
import android.os.Bundle;

public class Matrix {
    private int NumberofRows, NumberofCols;
    private Type type;
    private String name;
    private float Elements[][]=new float[9][9];
    public  Matrix(int r,int c,Type t)
    {
        this.NumberofRows=r;
        this.NumberofCols=c;
        this.type=t;
        this.name="New Normal 3";
    }
    public Matrix(int p)
    {
        NumberofRows=p;
        NumberofCols=p;
        type=Type.Normal;
        name="New Square 1";

    }
    public Matrix()
    {
        NumberofRows=9;
        NumberofCols=9;
        type=Type.Normal;
        name="New Matrix";
    }
    public int GetRow()
    {
        return NumberofRows;
    }
    public int GetCol()
    {
        return NumberofCols;
    }
    public void SetRow(int r)
    {
        NumberofRows=r;
    }
    public void SetCol(int c)
    {
        NumberofCols=c;
    }
    public void SetType(Type t1)
    {
        this.type=t1;
    }
    public boolean is_squareMatrix()
    {
        return (NumberofCols==NumberofRows);
    }
    public Type GetType()
    {
        return this.type;
    }
    public boolean ApplyType() {
        if (this.type == Type.Normal)
            return false;
        else {
            switch (this.type) {
                case Identity:
                    MakeIdentity();
                    break;
                case Diagonal:
                    MakeDiagonal();
                    break;
                case Null:
                    MakeNull();
                    break;
            }

        }
        return true;
    }
    public void  MakeNull()
    {
       for (int i=0;i<this.GetRow();i++){
           for (int j=0;j<this.GetCol();j++)
           {
               this.Elements[i][j]=0;
           }
       }
    }
    public void MakeIdentity()
    {
        for (int i=0;i<this.GetRow();i++){
            for (int j=0;j<this.GetCol();j++)
            {
                if(i==j)
                    this.Elements[i][j]=1;
                else
                    this.Elements[i][j]=0;
            }
        }
    }
    public String GetName()
    {
        return this.name;
    }
    public void SetName(String nam)
    {
        this.name=nam;
    }
    private void MakeDiagonal()
    {
        for (int i=0;i<this.GetRow();i++) {
            for (int j = 0; j < this.GetCol(); j++) {
                if (i == j)
                    this.Elements[i][j] = 0;
            }
        }
    }
    public void AddtoThis(Matrix m)
    {
        if(isSameOrder(m)) {
            for (int i = 0; i < m.GetRow(); i++)
                for (int j = 0; j < m.GetCol(); j++)
                    this.Elements[i][j] = this.Elements[i][j] + m.Elements[i][j];
        }
    }
    public void SubtoThis(Matrix m)
    {
        if(isSameOrder(m)) {
            for (int i = 0; i < m.GetRow(); i++)
                for (int j = 0; j < m.GetCol(); j++)
                    this.Elements[i][j] = this.Elements[i][j] - m.Elements[i][j];
        }
    }
    public boolean isSameOrder(Matrix a)
    {
        return (a.GetRow()==this.GetRow()&&a.GetCol()==this.GetCol());
    }
    public void CopyThisto(Matrix p)
    {
        if(isSameOrder(p))
        {
            for(int i=0;i<p.GetRow();i++)
                for (int j=0;j<p.GetCol();j++)
                    p.Elements[i][j]=this.Elements[i][j];
        }

    }
    public void CopyFrom(Matrix p)
    {
        if(isSameOrder(p))
        {
            for(int i=0;i<p.GetRow();i++)
                for (int j=0;j<p.GetCol();j++)
                    this.Elements[i][j]=p.Elements[i][j];
        }
    }
    public Matrix Transpose()
    {
       Matrix p = new Matrix(this.GetCol(),this.GetRow(),this.GetType());
        for(int i=0;i<this.GetRow();i++)
            for (int j=0;j<this.GetCol();j++)
                p.Elements[j][i]=this.Elements[i][j];
        return p;
    }
    public void SquareTranspose()
    {
        Matrix p = new Matrix(this.GetCol());
        for(int i=0;i<this.GetRow();i++)
            for (int j=0;j<this.GetCol();j++)
                p.Elements[j][i]=this.Elements[i][j];
        this.CopyFrom(p);
    }
    public void SwapWith(Matrix h)
    {
        if(isSameOrder(h))
        {
            for(int i=0;i<this.GetRow();i++)
                for (int j=0;j<this.GetCol();j++)
                {
                    this.Elements[i][j]=this.Elements[i][j]+h.Elements[i][j]; // Algorithm to Swap the Numbers
                    h.Elements[i][j]=this.Elements[i][j]-h.Elements[i][j];
                    this.Elements[i][j]=this.Elements[i][j]-h.Elements[i][j];
                }
        }

    }
    private boolean AreMultipliabe(Matrix h)
    {
        return this.GetCol()==h.GetRow();
    }
    public Matrix MultipyWith(Matrix j)
    {
        if(AreMultipliabe(j))
        {
            Matrix m= new Matrix(this.GetRow(),j.GetCol(),this.GetType());
            for(int i=0;i<this.GetRow();i++)
                for(int js=0;js<m.GetCol();js++)
                {
                    m.Elements[i][js]=0;
                    for(int k=0;k<this.GetCol();k++)
                    {
                        m.Elements[i][js]+=this.Elements[i][k]*j.Elements[k][js];
                    }
                }
            return m;
        }
        else
            return null;

    }
    public void MultiplytoThis(Matrix m) throws ExceptionInInitializerError{
        if (AreMultipliabe(m)) {
            Matrix mh = new Matrix(this.GetRow(), m.GetCol(), this.GetType());
            for (int i = 0; i < this.GetRow(); i++)
                for (int js = 0; js < m.GetCol(); js++) {
                    mh.Elements[i][js] = 0;
                    for (int k = 0; k < this.GetCol(); k++) {
                        mh.Elements[i][js] += this.Elements[i][k] * m.Elements[k][js];
                    }
                }
            this.CloneFrom(mh);
        }
        else{
            throw new ExceptionInInitializerError();
        }
    }
    private void PushAt(int R_index,int C_index,float Elt)
    {
        this.Elements[R_index][C_index]=Elt;
    }
    public double GetDeterminant(ProgressDialog px) //Make sure a Square Matrix is only Calling this, Incases Matrix Result is always Zero
    {
      double  Result=0;
        int total = 0;
        int flag=0,a=0,b=0;
        int Order =this.GetRow();
        if(Order==1)
        {
            Result= this.Elements[0][0];
            px.setProgress(100);
            return Result;
        }
        if(Order==2)
        {
            float l=this.Elements[0][0]*this.Elements[1][1];
            float m=this.Elements[1][0]*this.Elements[0][1];
            Result=l-m;
            px.setProgress(100);
            return Result;

        }
        else
        {
            for(;flag<Order;flag++) //3
            {
                Matrix pointer= new Matrix(Order-1);
                for(int i=1;i<Order;i++) //4
                {
                    px.setProgress((total*100)/(Order*Order));
                    total++;
                    for(int j=0;j< Order;j++)
                    {
                        if(flag!=j)
                        {
                            float pg=this.Elements[i][j];
                            pointer.PushAt(a,b,pg);
                            b++;
                        }
                    }
                    a++;
                    b=0;

                }
                a=0;
                b=0;
                double z=pointer.GetDeterminant();
                Result +=Math.pow(-1,flag)*(this.Elements[0][flag]*z);
            }
        }
    px.setProgress(100);
    return Result;
    }

    private double GetDeterminant() //Should never be called directly but only by other functions
    {
        double  Result=0;
        int flag=0,a=0,b=0;
        int Order =this.GetRow();
        if(Order==1)
        {
            Result= this.Elements[0][0];
            return Result;
        }
        if(Order==2)
        {
            float l=this.Elements[0][0]*this.Elements[1][1];
            float m=this.Elements[1][0]*this.Elements[0][1];
            Result=l-m;
            return Result;

        }
        else
        {
            for(;flag<Order;flag++)
            {
                Matrix pointer= new Matrix(Order-1);
                for(int i=1;i<Order;i++)
                {
                    for(int j=0;j< Order;j++)
                    {
                        if(flag!=j)
                        {
                            float pg=this.Elements[i][j];
                            pointer.PushAt(a,b,pg);
                            b++;
                        }
                    }
                    a++;
                    b=0;

                }
                a=0;
                b=0;
                double z=pointer.GetDeterminant();
                Result +=Math.pow(-1,flag)*(this.Elements[0][flag]*z);
            }
        }

        return Result;
    }
    private void MakeAdjoint(ProgressDialog Progress)
    {
        int Order=this.GetCol();
        Matrix base= new Matrix(Order);
        int flag,a=0,b=0;
        if(Order==2)
        {
            float buffer= this.Elements[0][0];
            this.Elements[0][0]=this.Elements[1][1];
            this.Elements[1][1]=buffer;
            this.Elements[1][0]*=(-1);
            this.Elements[0][1]*=(-1);
            buffer=this.Elements[1][0];
            this.Elements[1][0]=this.Elements[0][1];
            this.Elements[0][1]=buffer;
            this.SquareTranspose();
            Progress.setProgress(100);
        }
        else
        {
            for(int k=0;k<Order;k++)
            {
                Progress.setProgress((k*100)/Order);
                for(flag=0;flag<Order;flag++)
                {
                    Progress.setSecondaryProgress((flag*100)/Order);
                    Matrix pointer=new Matrix(Order-1);
                    for(int i=0;i<Order;i++)
                    {
                        for (int j = 0; j < Order; j++)
                        {
                            if ((flag != j) && (k != i))
                            {
                                float pg = this.Elements[i][j];
                                pointer.PushAt(a, b, pg);
                                b++;
                            }
                        }
                            if (k != i)
                                a++;
                            b = 0;
                    }
                        float z=(float) pointer.GetDeterminant();
                        int variable= k+flag;
                        base.Elements[k][flag]= (float) Math.pow((-1),variable)*z;
                        a=0;
                        b=0;
                    }
                }
                this.CopyFrom(base);
                this.SquareTranspose();
            Progress.setProgress(100);
            }

    }
    public Matrix ReturnAdjoint(ProgressDialog updates)
    {
        Matrix Result= new Matrix(this.GetRow());
        this.CopyThisto(Result);
        Result.MakeAdjoint(updates);
        return  Result;
    }
    public Matrix Inverse(ProgressDialog progressDialog) //Must be a Square Matrix
    {
        float determinant= (float) this.GetDeterminant();
        if(determinant==0) //if determinant was to be zero
            return null;
        else {
            Matrix Result = this.ReturnAdjoint(progressDialog);
            for (int i = 0; i < this.GetRow(); i++)
                for (int j = 0; j < this.GetCol(); j++)
                    Result.Elements[i][j] /= determinant;
            return Result;
        }
    }
    public void Raiseto(int a) //Must be a Square Matrix check before Calling this Function.
    {
        Matrix pi= new Matrix(this.GetRow());
        pi.MakeIdentity();
        if(a==0)
            this.MakeIdentity();
        else
        {
            for(int i=0;i<a;i++)
            {
                pi=pi.MultipyWith(this);

            }
            this.CopyFrom(pi);
        }

    }
    public Bundle GetDataBundled()
    {
        Bundle AllInfo= new Bundle();
        AllInfo.putInt("ROW",this.GetRow());
        AllInfo.putInt("COL",this.GetCol());
        AllInfo.putString("NAME",name);
        AllInfo.putSerializable("TYPE",this.GetType());
        AllInfo.putSerializable("VALUES",this.Elements);
        return AllInfo;
    }
    public void SetFromBundle(Bundle bundle)
    {
        this.name=bundle.getString("NAME");
        this.SetRow(bundle.getInt("ROW"));
        this.SetCol(bundle.getInt("COL"));
        this.SetType((Type) bundle.getSerializable("TYPE"));
        this.Elements=(float[][]) bundle.getSerializable("VALUES");
    }
    public int GetDrawable(Type t)
    {
        switch (t)
        {
            case Normal:
                return R.drawable.normal;
            case Null:
                return R.drawable.nullmatrix;
            case Identity:
                return R.drawable.identity;
            case Diagonal:
                return R.drawable.diagonal;

        }
        return 0;
    }
    public void SetType()
    {
        if(!this.is_squareMatrix())
            this.SetType(Type.Normal);
        else
        {
            if(this.isNull())
                this.SetType(Type.Null);
            if(this.isIdentity())
                this.SetType(Type.Identity);
            if(this.isDiagonal())
                this.SetType(Type.Diagonal);
            if(!this.isNull()&&!this.isIdentity()&&!this.isDiagonal())
                this.SetType(Type.Normal);
        }
    }
    public boolean isNull()
    {
        for(int i=0;i<this.GetRow();i++)
        {
            for(int j=0;j<this.GetCol();j++)
                if(this.Elements[i][j]!=0)
                    return false;
        }
        return true;
    }
    private boolean isIdentity()
    {
        for(int i=0;i<this.GetRow();i++)
        {
            for(int j=0;j<this.GetCol();j++)
                if((this.Elements[i][j]!=1 && i==j)||(this.Elements[i][j]!=0))
                    return false;
        }
        return true;
    }
    private boolean isDiagonal()
    {
        for(int i=0;i<this.GetRow();i++)
        {
            for(int j=0;j<this.GetCol();j++)
                if((this.Elements[i][j]!=0 && i==j)||(this.Elements[i][j]!=0))
                    return false;
        }
        return true;
    }
    public float GetElementof(int r,int c){
        return this.Elements[r][c];
    }
    public void SetElementof(float val,int r,int c){
        this.Elements[r][c]=val;
    }
    public void CloneFrom(Matrix p)
    {
        this.SetCol(p.GetCol());
        this.SetRow(p.GetRow());
        this.CopyFrom(p);
        this.SetType(p.GetType());
        this.SetName(p.GetName());
    }
    public Matrix ExactClone(String newname)
    {
        Matrix matrix = new Matrix(this.GetRow(),this.GetCol(),this.GetType());
        this.CopyThisto(matrix);
        matrix.SetName(newname);
        return matrix;
    }
    public void ScalarMultiply(float multiplier){
        for(int i=0;i<this.GetRow();i++)
            for(int j=0;j<this.GetCol();j++)
                this.Elements[i][j]*=multiplier;
    }
    public Matrix ReturnScaler(float ig){
        Matrix re  = new Matrix(this.GetRow(),this.GetCol(),this.GetType());
        re.CopyFrom(this);
        re.ScalarMultiply(ig);
        return re;
    }
 }
