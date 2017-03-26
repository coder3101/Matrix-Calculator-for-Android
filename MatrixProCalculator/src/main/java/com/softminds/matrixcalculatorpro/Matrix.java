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
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;

public class Matrix {

    private int NumberofRows, NumberofCols;

    private Type type;

    private String name;

    private float Elements[][]=new float[9][9];

    public  Matrix(int r,int c,Type t) {
        this.NumberofRows=r;
        this.NumberofCols=c;
        this.type=t;
        this.name="New Normal 3";
    }

    public Matrix(int p) {
        NumberofRows=p;
        NumberofCols=p;
        type=Type.Normal;
        name="New Square 1";

    }

    public Matrix() {
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

    public void  MakeNull() {
       for (int i=0;i<this.GetRow();i++){
           for (int j=0;j<this.GetCol();j++)
           {
               this.Elements[i][j]=0;
           }
       }
    }

    public void MakeIdentity() {
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

    public void AddtoThis(Matrix m) {
        if(isSameOrder(m)) {
            for (int i = 0; i < m.GetRow(); i++)
                for (int j = 0; j < m.GetCol(); j++)
                    this.Elements[i][j] = this.Elements[i][j] + m.Elements[i][j];
        }
    }

    public void SubtoThis(Matrix m) {
        if(isSameOrder(m)) {
            for (int i = 0; i < m.GetRow(); i++)
                for (int j = 0; j < m.GetCol(); j++)
                    this.Elements[i][j] = this.Elements[i][j] - m.Elements[i][j];
        }
    }

    private boolean isSameOrder(Matrix a) {
        return (a.GetRow()==this.GetRow()&&a.GetCol()==this.GetCol());
    }

    private void CopyThisto(Matrix p) {
        if(isSameOrder(p))
        {
            p.Elements = this.Elements.clone();
        }

    }

    private void CopyFrom(Matrix p) {
        if(isSameOrder(p))
        {
            this.Elements = p.Elements.clone();
        }
    }

    public Matrix Transpose() {
       Matrix p = new Matrix(this.GetCol(),this.GetRow(),this.GetType());
        for(int i=0;i<this.GetRow();i++)
            for (int j=0;j<this.GetCol();j++)
                p.Elements[j][i]=this.Elements[i][j];
        return p;
    }

    public void SquareTranspose() {
        Matrix p = new Matrix(this.GetCol());
        for(int i=0;i<this.GetRow();i++)
            for (int j=0;j<this.GetCol();j++)
                p.Elements[j][i]=this.Elements[i][j];
        this.CopyFrom(p);
    }

    public void SwapWith(Matrix h) {
        if(isSameOrder(h))
        {
            Matrix buffer = new Matrix(this.GetRow(),this.GetCol(),this.GetType());
            buffer.Elements = this.Elements.clone();
            this.Elements = h.Elements.clone();
            h.Elements =buffer.Elements.clone();

        }

    }

    private boolean AreMultipliabe(Matrix h)
    {
        return this.GetCol()==h.GetRow();
    }

    @Nullable
    private Matrix MultipyWith(Matrix j) {
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
        else {
            return null;
        }

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

    public double GetDeterminant(ProgressDialog px) {
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

            if(this.RowZero()||this.ColZero()) //if a complete row or column is zero, determinant is 0
                return 0f;
            for(;flag<Order;flag++)
            {
                Matrix pointer= new Matrix(Order-1);
                for(int i=1;i<Order;i++)
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

    private double GetDeterminant() {
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
            if(this.RowZero()||this.ColZero()) //if a complete row or column is zero, determinant is 0
                    return 0f;
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

    public float GetMinor(int indexX,int indexY) {
        Matrix matrix = new Matrix(this.GetCol() - 1);
        int a = 0, b = 0;
        for (int i = 0; i < this.GetRow(); i++) {
            for (int j = 0; j < this.GetCol(); j++) {
                if (indexY != j && indexX !=i) {
                    matrix.SetElementof(this.GetElementof(i, j), a, b);
                    b++;
                }
            }
            b=0;
            if(indexX !=i)
                a++;
        }
        Log.d("Value :",String.valueOf((float)matrix.GetDeterminant()));
        Log.d("Marix : ",matrix.toString());
        return (float) matrix.GetDeterminant();
    }

    private void MakeAdjoint(ProgressDialog Progress) {
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

    public Matrix ReturnAdjoint(ProgressDialog updates) {
        Matrix Result= new Matrix(this.GetRow());
        this.CopyThisto(Result);
        Result.MakeAdjoint(updates);
        return  Result;
    }

    public Matrix Inverse(ProgressDialog progressDialog){
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

    public void Raiseto(int a){
        Matrix pi= new Matrix(this.GetRow());
        pi.MakeIdentity();
        if(a==0)
            this.MakeIdentity();
        else
        {
            try {
                for (int i = 0; i < a; i++) {
                    pi = pi != null ? pi.MultipyWith(this) : null;
                }

            }catch (NullPointerException e){
                Log.d("RaiseToError :","Non Square Matrix called the function raiseto()");
                e.printStackTrace();
                return;
            }
            this.CopyFrom(pi);
        }

    }

    public Bundle GetDataBundled() {
        Bundle AllInfo= new Bundle();
        AllInfo.putInt("ROW",this.GetRow());
        AllInfo.putInt("COL",this.GetCol());
        AllInfo.putString("NAME",name);
        AllInfo.putSerializable("TYPE",this.GetType());
        AllInfo.putSerializable("VALUES",this.Elements);
        return AllInfo;
    }

    public void SetFromBundle(Bundle bundle) throws ClassCastException {
        this.name=bundle.getString("NAME");
        this.SetRow(bundle.getInt("ROW"));
        this.SetCol(bundle.getInt("COL"));
        this.SetType((Type) bundle.getSerializable("TYPE"));
        this.Elements=(float[][]) bundle.getSerializable("VALUES");
    }

    public int GetDrawable(Type t) {
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

    public void SetType() {
        if(!this.is_squareMatrix())
            this.SetType(Type.Normal);
        else
        {
                if(this.isNull())
                    this.SetType(Type.Null);
                else
                if(this.isIdentity())
                    this.SetType(Type.Identity);
                else
                if(this.isDiagonal())
                    this.SetType(Type.Diagonal);
                else
                this.SetType(Type.Normal);
        }
    }


    public boolean isNull() {
        for(int i=0;i<this.GetRow();i++)
        {
            for(int j=0;j<this.GetCol();j++)
                if(this.Elements[i][j]!=0)
                    return false;
        }
        return true;
    }

    private boolean isIdentity() {
        for(int i=0;i<this.GetRow();i++)
        {
            for(int j=0;j<this.GetCol();j++)
                if((this.Elements[i][j]!=1 && i==j)&&(this.Elements[i][j]!=0))
                    return false;
        }
        return true;
    }

    private boolean isDiagonal() {
        int flag=0;
        for(int i=0;i<this.GetRow();i++)
        {
            for(int j=0;j<this.GetCol();j++)
                if(i!=j && this.Elements[i][j]!=0)
                    return false;
            else
                flag++;
        }
        return flag!=this.GetCol();
    }

    public float GetElementof(int r,int c) throws ExceptionInInitializerError{
        if(this.GetRow()<r || this.GetCol() < c || r < 0 || c < 0)
            throw new ExceptionInInitializerError();
        else
            return this.Elements[r][c];
    }

    public void SetElementof(float val,int r,int c) throws ExceptionInInitializerError{
        if(this.GetCol()<c || this.GetRow()<r || c < 0 || r < 0 )
            throw new ExceptionInInitializerError();
        else
            this.Elements[r][c]=val;
    }

    public void CloneFrom(Matrix p) {
        this.SetCol(p.GetCol());
        this.SetRow(p.GetRow());
        this.CopyFrom(p);
        this.SetType(p.GetType());
        this.SetName(p.GetName());
    }

    public Matrix ExactClone(String newname) {
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
        Matrix newMat = new Matrix(this.GetRow(),this.GetCol(),this.GetType());
        for(int i=0;i<this.GetRow();i++)
            for(int j=0;j<this.GetCol();j++)
                newMat.Elements[i][j]=this.Elements[i][j];
        newMat.ScalarMultiply(ig);
        return newMat;
    }

    public int GetRank() {
        int max = 0;
        if (this.GetRow() == 1 || this.GetCol() == 1) { // for a single ordered matrix rank is zero if all elements are zero, else 1
            for (int i = 0; i < this.GetRow(); i++)
                for (int j = 0; j < this.GetCol(); j++) {
                    if (this.Elements[i][j] != 0)
                        return 1;
                }
            return 0;
        }

        if (this.is_squareMatrix()) { //if its a square matrix then two cases arises, either its determinant will be zero or not
            if (this.GetDeterminant() != 0)
                return this.GetCol();
            else { //Matrix is Square but Determinant was Zero
                int a = 0, b = 0; //index of insertion for buffer matrix
                ArrayList<Matrix> zerominors = new ArrayList<>(); //to fill all minors of the matrix
                Matrix matrix = new Matrix(this.GetRow() - 1);
                int p = 0, q = 0; // indexes to leave while finding the Minors
                for (int m = 0; m < this.GetCol(); m++) {
                    for (int n = 0; this.GetCol() > n; n++) {
                        for (int s = 0; s < this.GetRow(); s++) {
                            for (int i = 0; i < this.GetCol(); i++) {
                                if (s != p && i != q) {
                                    matrix.SetElementof(this.GetElementof(s, i), a, b);
                                    b++;
                                }
                            }
                            a++;
                            b=0;
                        }
                        a=0;
                        b=0;
                        if (matrix.GetDeterminant() != 0)
                            return matrix.GetCol();
                        else
                            zerominors.add(matrix);
                        q++;
                    }
                    p++;
                    q=0;
                }
                for (int i = 0; i < zerominors.size(); i++) {
                    int rank = zerominors.get(i).GetRank();
                    if (max < rank) //get maximum rank from all zero minors
                        max = rank;
                }
            }
            return max;
        }
        else{ //Matrix is not a Square Matrix
            ArrayList<Matrix> reduced = new ArrayList<>();
            int min = Math.min(this.GetCol(),this.GetRow());
            for(int i=0;i<Math.max(this.GetCol(),this.GetRow());i++) {
                Matrix matrix=this.ReduceToSquare(i,i+min);
                if(matrix.GetDeterminant()!=0)
                    return matrix.GetCol();
                else{
                    reduced.add(matrix);
                }
            }
            for(int i=0;i<reduced.size();i++)
            {
                int rank = reduced.get(i).GetRank();
                if(max<rank)
                    max = rank;
            }
            return max;
        }
    }

    private Matrix ReduceToSquare(int from,int to) {
        int a = 0, b = 0;
        if(this.GetRow()>this.GetCol()) {
            if (from < to && to<9) {
                Matrix res = new Matrix(this.GetCol());
                for (int i = from; i < to; i++) {
                    for (int j = 0; j < this.GetCol(); j++) {
                        res.SetElementof(this.GetElementof(i, j), a, b);
                        b++;
                    }
                    a++;
                    b=0;
                }
                return res;
            } else {
                Matrix res = new Matrix(this.GetCol());
                for (int i = from; i < this.GetRow(); i++) {
                    for (int j = 0; j < this.GetCol(); j++) {
                        res.SetElementof(this.GetElementof(i, j), a, b);
                        b++;
                    }
                    a++;
                    b=0;
                }
                for (int i = 0; i < to-this.GetRow(); i++) {
                    for (int j = 0; j < this.GetCol(); j++) {
                        res.SetElementof(this.GetElementof(i, j), a, b);
                        b++;
                    }
                    a++;
                    b=0;
                }
                return res;
            }
        }
        else{ // if column is more

            if (from < to && to<9) {
                Matrix res = new Matrix(this.GetRow());
                for (int i = from; i < to; i++) {
                    for (int j = 0; j < this.GetRow(); j++) {
                        res.SetElementof(this.GetElementof(j, i), b, a);
                        a++;
                    }
                    b++;
                    a=0;
                }
                return res;
            } else {
                Matrix res = new Matrix(this.GetRow());
                for (int i = from; i < this.GetCol(); i++) {
                    for (int j =0; j < this.GetRow(); j++) {
                        res.SetElementof(this.GetElementof(j, i), b, a);
                        a++;
                    }
                    b++;
                    a=0;
                }
                for (int i = 0; i < to-this.GetCol(); i++) {
                    for (int j = 0; j < this.GetRow(); j++) {
                        res.SetElementof(this.GetElementof(j, i), b, a);
                        a++;
                    }
                    b++;
                    a=0;
                }
                return res;
            }

        }

    }

    @Override
    public String toString(){
        String s = "--->";
        for(int i=0;i<this.GetRow();i++) {
            for (int j = 0; j < this.GetCol(); j++)
                s += String.valueOf(this.GetElementof(i, j)) + " : ";
        s += "->";
        }
        return s;
    }





    /*         <<----------- LOGICAL FUNCTIONS ----------------->>              */

    /*Logical Functions for Optimizing heavy Matrix Calculations, they will ensure
    heavy tasks like checking if all row is zero or not or if all columns are zero or not
    thereby reducing the Processing time by many folds. They may extent the processing time if not called wisely
    THEY WILL TEST SPECIFIC PROPERTIES OF A MATRIX AND LOGICALLY RETURN THE RESULT INSTEAD OF DOING CALCULATIONS
    IN TRADITIONAL ALGORITHMS.

    The Following Properties must be satisfied by them :

    1. All Should be Called by Member functions only and in no way, directly means all should be private
    2. All Should only return a boolean
    3. No Parameter to be given to them, (they should only affect the calling object) i.e use "this" keyword
    4. They Should never under any circumstances modify the original object
    5. Some Heavy Logical function can use a new thread for processing.
     */

    private boolean RowZero(){ //checks if a complete row is zero
        int flag1=0;
        for(int i=0;i<this.GetRow();i++) {
            for (int j = 0; j < this.GetCol(); j++) {
                if(this.GetElementof(i,j) == 0)
                    flag1++;
            }
            if (flag1 == this.GetCol())
                return true;
            else {
                flag1 = 0;
            }
        }
        return false;
    }

    private boolean ColZero(){ //checks if a complete column is zero
        int flag1=0;
        for(int i=0;i<this.GetCol();i++) {
            for (int j = 0; j < this.GetRow(); j++) {
                if(this.GetElementof(i,j) == 0)
                    flag1++;
            }
            if (flag1 == this.GetRow())
                return true;
            else {
                flag1 = 0;
            }
        }
        return false;
    }


    //todo:below two methods should be improved
    /*public boolean RowEqualModulo() { //checks if rows are multiple of one another
        float array1[] = new float[this.GetRow()];
        float array2[] = new float[this.GetRow()];
        /*for(int i=0;i<this.GetCol()-1;i++){
            for(int j=0;j<this.GetRow();j++)
            {
                array1[j] = this.GetElementof(j,i);
                for(int k=0;k<this.GetCol();k++)
                {
                    if(k!=i)
                       {
                        array2[j] = this.GetElementof(j, k);
                        if (Multiples(array1,array2))
                            return true;
                    }
                }

            }
        }/*
        for(int i=0;i<this.GetRow();i++) {
            for (int j = 0; j < this.GetCol(); j++)
                array1[j] = this.GetElementof(i, j);
            for(int k=0;k<this.GetCol();k++)
            {

            }
        }
        return false;

    }*/

    /*private boolean ColEqualModulo() { //checks if rows are multiple of one another
        float array1[] = new float[this.GetCol()];
        float array2[] = new float[this.GetCol()];
        for(int i=0;i<this.GetRow()-1;i++){
            for(int j=0;j<this.GetCol();j++)
            {
                array1[j] = this.GetElementof(i,j);
                array2[j] = this.GetElementof(i+1,j);

            }
            if (Multiples(array1,array2)){
                return true;
            }
        }
        return false;

    }
    */

    private boolean Multiples (float a[],float b[]) {
        //used by above function to check if the two arrays are multiple of each other
        //a if multiples constant value
        float Constant; int index=0;
       while(index  < b.length  ){ //finds until the value of b[i] is a non zero
           if(b[index]!= 0)
           {
               Constant = a[index]/b[index];
               for(int i=index;i<a.length;i++) {
                   if (Constant != a[i] / b[i])
                       return false;
               }
                    return true;
               }
           else
               index++;
       }
        return true;
    }

    private boolean SmartProbabilityNull() {
        //used to determine if following logical functions should be called :
        //1.RowZero
        //2.ColZero
        int flag=0;
        for(int i=0;i<this.GetRow();i++)
            for(int j=0;j<this.GetCol();j++){
                if(this.GetElementof(i,j)==0)
                    flag++;
            }
        return flag > Math.min(this.GetCol(),this.GetCol());
    }


 }
