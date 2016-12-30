/*
MIT License

Copyright (c) 2016 Ashar Khan

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

package com.softminds.matrixcalculator;


import android.os.Bundle;

enum  Type {
    Null,
    Identity,
    Diagonal,
    Normal;
}
public class Matrix {
    private int NumberofRows, NumberofCols;
    private Type type;
    private String name;
    private float Elements[][]=new float[9][9]; //Todo Experimental this may Result in Memory Losses
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
        return type;
    }
    public boolean ApplyType() {
        if (Matrix.this.type == Type.Normal)
            return false;
        else {
            switch (Matrix.this.type) {
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
       for (int i=0;i<Matrix.this.GetRow();i++){
           for (int j=0;j<Matrix.this.GetCol();j++)
           {
               Matrix.this.Elements[i][j]=0;
           }
       }
    }
    public void MakeIdentity()
    {
        for (int i=0;i<Matrix.this.GetRow();i++){
            for (int j=0;j<Matrix.this.GetCol();j++)
            {
                if(i==j)
                    Matrix.this.Elements[i][j]=1;
                else
                    Matrix.this.Elements[i][j]=0;
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
    public void MakeDiagonal()
    {
        for (int i=0;i<Matrix.this.GetRow();i++) {
            for (int j = 0; j < Matrix.this.GetCol(); j++) {
                if (i == j)
                    Matrix.this.Elements[i][j] = 0;
            }
        }
    }
    public boolean isequalto(Matrix matrix) {
        if (matrix.GetCol() == Matrix.this.GetCol() && (Matrix.this.GetRow() == matrix.GetRow())) {
            int yes = 0, no = 0;
            for (int i = 0; i < Matrix.this.GetRow(); i++) {
                for (int j = 0; j < matrix.GetCol(); j++) {
                    if (matrix.Elements[i][j] == Matrix.this.Elements[i][j])
                        yes++;
                    else
                        no++;
                }
            }

            return (no!=0);

        }
        else
            return false;
    }
    public Matrix AddWith(Matrix m)
    {
        if(isSameOrder(m))
        {
            Matrix p= new Matrix(m.GetRow(),m.GetCol(),Type.Normal);
            for(int i=0;i<m.GetRow();i++)
                for (int j=0;j<m.GetCol();j++)
                    p.Elements[i][j]=Matrix.this.Elements[i][j]+m.Elements[i][j];

           return p;
        }
        else {
            return null ;
        }
    }
    public void Addthisto(Matrix p)
    {
        if(isSameOrder(p))
        {
            for(int i=0;i<p.GetRow();i++)
                for (int j=0;j<p.GetCol();j++)
                    p.Elements[i][j]=Matrix.this.Elements[i][j]+p.Elements[i][j];
        }
    }
    public Matrix SubtractWith(Matrix m)
    {
        if(isSameOrder(m))
        {
            Matrix p= new Matrix(m.GetRow(),m.GetCol(),Type.Normal);
            for(int i=0;i<m.GetRow();i++)
                for (int j=0;j<m.GetCol();j++)
                    p.Elements[i][j]=Matrix.this.Elements[i][j]-m.Elements[i][j];

            return p;
        }
        else {
            return null ;
        }
    }
    public void SubtractThisIn(Matrix p)
    {
        if(isSameOrder(p))
        {
            for(int i=0;i<p.GetRow();i++)
                for (int j=0;j<p.GetCol();j++)
                    p.Elements[i][j]=p.Elements[i][j]-Matrix.this.Elements[i][j];
        }
    }
    public boolean isSameOrder(Matrix a,Matrix b)
    {
        return (a.GetRow()==b.GetRow()&&a.GetCol()==b.GetCol());

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
                    p.Elements[i][j]=Matrix.this.Elements[i][j];
        }
    }
    public void CopyFrom(Matrix p)
    {
        if(isSameOrder(p))
        {
            for(int i=0;i<p.GetRow();i++)
                for (int j=0;j<p.GetCol();j++)
                    Matrix.this.Elements[i][j]=p.Elements[i][j];
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
    public boolean AreMultipliable(Matrix a, Matrix b)
    {
        return (a.GetCol()==b.GetRow());
    }
    public boolean AreMultipliabe(Matrix h)
    {
        return this.GetCol()==h.GetRow();
    }
    public Matrix MultipyWith(Matrix j)
    {
        if(AreMultipliabe(j))
        {
            Matrix m= new Matrix(this.GetRow(),j.GetCol(),this.GetType());
            for(int i=0;i<GetRow();i++)
                for(int js=0;js<GetCol();js++)
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
    public void PushAt(int R_index,int C_index,float Elt)
    {
        this.Elements[R_index][C_index]=Elt;
    }
    public double GetDeterminant() //Make sure a Square Matrix is only Calling this, Incases Matrix Result is always Zero
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
    public void MakeAdjoint()
    {
        int Order=this.GetCol();
        Matrix base= new Matrix(Order);
        int flag=0,a=0,b=0;
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
        }
        else
        {
            for(int k=0;k<Order;k++)
            {
                for(flag=0;flag<Order;flag++)
                {
                    Matrix pointer=new Matrix(Order-1);
                    for(int i=0;i<Order;i++)
                    {
                        for(int j=0;j<Order;j++)
                        {
                            if((flag!=j)&&(k!=i))
                            {
                                float pg= this.Elements[i][j];
                                pointer.PushAt(a,b,pg);
                                b++;
                            }
                            if(k!=i)
                                a++;
                            b=0;
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
            }
        }

    }
    public Matrix ReturnAdjoint()
    {
        Matrix Result= new Matrix(this.GetRow());
        this.CopyThisto(Result);
        this.MakeAdjoint();
        this.SwapWith(Result);
        return  Result;
    }
    public Matrix Inverse() //Must be a Square Matrix
    {
        Matrix Result= new Matrix(this.GetRow());
        Result=this.ReturnAdjoint();
        float determinant= (float) this.GetDeterminant();
        for(int i=0;i<this.GetRow();i++)
            for(int j=0; j<this.GetCol();j++)
                Result.Elements[i][j]/=determinant;
        return  Result;
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
    public Bundle GetDatabundled()
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
        String type=bundle.getString("TYPE");
        this.TypeFromString(type);
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
    public void TypeFromString(String s) {
        switch (s) {
            case "Normal":
                this.SetType(Type.Normal);
                break;
            case "Null":
                this.SetType(Type.Null);
                break;
            case "Diagonal":
                this.SetType(Type.Diagonal);
                break;
            case "Identity":
                this.SetType(Type.Identity);
                break;
        }
    }
    public void SetType(Matrix d)
    {
        if(!d.is_squareMatrix())
            d.SetType(Type.Normal);
        else
        {
            if(d.isNull())
                d.SetType(Type.Null);
            if(d.isIdentity())
                d.SetType(Type.Identity);
            if(d.isDiagonal())
                d.SetType(Type.Diagonal);
            if(!d.isNull()&&!d.isIdentity()&&!d.isDiagonal())
                d.SetType(Type.Normal);
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
    public boolean isIdentity()
    {
        for(int i=0;i<this.GetRow();i++)
        {
            for(int j=0;j<this.GetCol();j++)
                if((this.Elements[i][j]!=1 && i==j)||(this.Elements[i][j]!=0))
                    return false;
        }
        return true;
    }
    public boolean isDiagonal()
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
 }