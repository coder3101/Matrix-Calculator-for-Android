/*
 * Copyright (c) 2018. <ashar786khan@gmail.com>
 * This file is part of Application 's Android Application.
 * Application' s Android Application is free software : you can redistribute it and/or modify
 * it under the terms of GNU General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 *
 * This Application is distributed in the hope that it will be useful
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General  Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with this Source File.
 *  If not, see <http:www.gnu.org/licenses/>.
 */

package com.softminds.matrixcalculator;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.google.android.gms.tasks.RuntimeExecutionException;

import java.io.Serializable;

import Jama.Matrix;

public class MatrixV2 implements Serializable {

    private int numberOfRows, numberOfCols;
    private Type type;
    private String name;
    private Matrix jamaMatrix;

    public MatrixV2(int r, int c, Type t) {
        this.numberOfRows = r;
        this.numberOfCols = c;
        this.type = t;
        this.name = "Unknown Name";
        jamaMatrix = new Matrix(r, c);
    }

    public MatrixV2(int p) {
        numberOfCols = p;
        numberOfRows = p;
        type = Type.Normal;
        name = "Unknown Name";
        jamaMatrix = new Matrix(p, p);
    }

    public MatrixV2() {
        numberOfRows = -1;
        numberOfCols = -1;
        type = Type.Normal;
        name = "Unknown Name";
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public int getNumberOfCols() {
        return numberOfCols;
    }

    public Type getType() {
        return type;
    }

    public Matrix getJamaMatrix() {
        return jamaMatrix;
    }

    public String getName() {
        return name;
    }

    public void setNumberOfCols(int numberOfCols) {
        this.numberOfCols = numberOfCols;
        if (this.numberOfRows != -1) jamaMatrix = new Matrix(this.numberOfRows, this.numberOfCols);
    }

    public void setNumberOfRows(int numberOfRows) {
        this.numberOfRows = numberOfRows;
        if (this.numberOfCols != -1) jamaMatrix = new Matrix(this.numberOfRows, this.numberOfCols);
    }

    public void updateRows(int numberOfRows) {
        if (this.numberOfRows == numberOfRows) return;
        double[][] copy = this.jamaMatrix.getArrayCopy();
        Matrix new_val = new Matrix(numberOfRows, this.numberOfCols);
        for (int t = 0; t < numberOfRows; t++) {
            for (int k = 0; k < this.numberOfCols; k++) {
                try {
                    new_val.set(t, k, copy[t][k]);
                } catch (Exception e) {
                    new_val.set(t, k, 0);
                }
            }
        }
        this.jamaMatrix = new_val;
        this.numberOfRows = numberOfRows;

    }

    public void updateCols(int numberOfCols) {
        if (this.numberOfCols == numberOfCols) return;
        double[][] copy = this.jamaMatrix.getArrayCopy();
        Matrix new_val = new Matrix(this.numberOfRows, numberOfCols);
        for (int t = 0; t < this.numberOfRows; t++) {
            for (int k = 0; k < numberOfCols; k++) {
                try {
                    new_val.set(t, k, copy[t][k]);
                } catch (Exception e) {
                    new_val.set(t, k, 0);
                }
            }
        }
        this.jamaMatrix = new_val;
        this.numberOfCols = numberOfCols;
    }


    public void setType(Type type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSquareMatrix() {
        return (numberOfCols == numberOfRows);
    }

    public boolean isSameOrder(MatrixV2 other) {
        return this.numberOfCols == other.numberOfCols &&
                this.numberOfRows == other.numberOfRows;
    }

    public boolean canBeMultiplied(MatrixV2 other) {
        return numberOfCols == other.numberOfRows;
    }

    public void makeNull() {
        jamaMatrix = new Matrix(numberOfRows, numberOfCols);
    }

    public void makeIdentity() {
        if (isSquareMatrix())
            jamaMatrix = Matrix.identity(numberOfRows, numberOfCols);
        else
            throw new RuntimeException("Cannot make identity when this is not a sqaure matrix");
    }

    public void addToThis(MatrixV2 other) throws RuntimeException {
        if (isSameOrder(other))
            this.jamaMatrix.plusEquals(other.jamaMatrix);
        else throw new RuntimeException("Addition is not possible if orders are not same");
    }

    public void subToThis(MatrixV2 other) throws RuntimeException {
        if (isSameOrder(other))
            this.jamaMatrix.minusEquals(other.jamaMatrix);
        else throw new RuntimeException("Subtraction is not possible if orders are not same");
    }

    public void copyTo(MatrixV2 other) {
        other.numberOfRows = this.numberOfRows;
        other.numberOfCols = this.numberOfCols;
        double data[][] = this.jamaMatrix.getArrayCopy();
        other.jamaMatrix = Matrix.constructWithCopy(data);
    }

    public void strictCopyTo(MatrixV2 other) throws RuntimeException {
        if (isSameOrder(other))
            this.copyTo(other);
        else throw new RuntimeException("strictCopyTo cannot be done as order are not same.");
    }

    public void copyFrom(MatrixV2 other) {
        other.copyTo(this);
    }

    public void strictCopyFrom(MatrixV2 other) throws RuntimeException {
        if (isSameOrder(other))
            copyFrom(other);
        else throw new RuntimeException("strictCopyFrom cannot be done as Orders do not match.");
    }

    public double getTrace() throws RuntimeException {
        if (isSquareMatrix()) {
            return jamaMatrix.trace();
        } else throw new RuntimeException("Cannot take trace as Matrix is not Square.");
    }

    public MatrixV2 transpose() {
        return MatrixV2.constructFromJamaMatrix(this.jamaMatrix.transpose());
    }

    public void transposeEquals() throws RuntimeException {
        if (isSquareMatrix())
            this.jamaMatrix = this.jamaMatrix.transpose();
        else throw new RuntimeException("transposeEquals must be called only on Square Matrices.");
    }

    public void swapValues(MatrixV2 other) throws RuntimeException {
        if (isSameOrder(other)) {
            this.jamaMatrix.plusEquals(other.jamaMatrix);
            other.jamaMatrix = this.jamaMatrix.minus(other.jamaMatrix);
            this.jamaMatrix = this.jamaMatrix.minus(other.jamaMatrix);
        } else throw new RuntimeException("swapValues cannot be performed as Orders are not Same.");
    }

    public void multiplyToThis(MatrixV2 other) throws RuntimeExecutionException {
        if (canBeMultiplied(other)) {
            this.jamaMatrix = this.jamaMatrix.times(other.jamaMatrix);
            this.numberOfCols = this.jamaMatrix.getColumnDimension();
            this.numberOfRows = this.jamaMatrix.getRowDimension();
        }
        else
            throw new RuntimeException("multiplyToThis cannot be performed as they are not multipliable");

    }

    public double getDeterminant(ProgressDialog px) {
        px.setIndeterminate(true);
        return this.jamaMatrix.det();
    }

    private Matrix getSubMatrixExcept(int row, int col) {
        double data[][] = this.jamaMatrix.getArrayCopy();
        double subData[][] = new double[numberOfRows - 1][numberOfCols - 1];
        int a = 0, b = 0;
        for (int r = 0; r < numberOfRows; r++)
            if (r != row) {
                for (int c = 0; c < numberOfCols; c++)
                    if (c != col) {
                        subData[a][b] = data[r][c];
                        b++;
                    }
                a++;
                b = 0;
            }

        return Matrix.constructWithCopy(subData);
    }

    public MatrixV2 getMinor(int x, int y) {
        return MatrixV2.constructFromJamaMatrix(this.getSubMatrixExcept(x, y));
    }

    public double getMinorDeterminant(int indexX, int indexY) throws RuntimeException {
        if (isSquareMatrix())
            return this.getSubMatrixExcept(indexX, indexY).det();
        else throw new RuntimeException("Cannot Create a Minor from Non-Square Matrix");
    }

    public void makeAdjoint(ProgressDialog px) {
        px.setIndeterminate(true);
        this.jamaMatrix = this.jamaMatrix.inverse().times(this.jamaMatrix.det());
        this.numberOfRows = this.jamaMatrix.getRowDimension();
        this.numberOfCols = this.jamaMatrix.getColumnDimension();
    }

    public MatrixV2 getAdjoint(ProgressDialog px) {
        px.setIndeterminate(true);
        return MatrixV2.constructFromJamaMatrix(this.jamaMatrix.inverse().times(this.jamaMatrix.det()));
    }

    public MatrixV2 getInverse(ProgressDialog px) throws RuntimeException {
        px.setIndeterminate(true);
        if(this.jamaMatrix.det() != 0)
        return MatrixV2.constructFromJamaMatrix(this.jamaMatrix.inverse());
        else throw new RuntimeException("Cannot Determine Inverse. Det is 0");
    }

    public void raisedToPower(int a) {
        if (isSquareMatrix()) {
            if (a == 0) this.jamaMatrix = Matrix.identity(numberOfRows, numberOfCols);
            else
                for (int t = 0; t < a; t++)
                    this.jamaMatrix = this.jamaMatrix.times(this.jamaMatrix);
        } else
            throw new RuntimeException("raisedToPower should not be called on non square matrices.");
    }

    public Bundle getDataBundled() {
        Bundle info = new Bundle();
        info.putInt("ROW", numberOfRows);
        info.putInt("COL", numberOfCols);
        info.putString("NAME", name);
        info.putSerializable("TYPE", type);
        info.putDoubleArray("VALUES", MatrixV2.flattenValues(this.jamaMatrix.getArrayCopy(), numberOfRows, numberOfCols));
        return info;

    }

    public int getDrawable(Type t) {
        switch (t) {
            case Normal:
                return R.mipmap.normal;
            case Null:
                return R.mipmap.null2;
            case Identity:
                return R.mipmap.identity;
            case Diagonal:
                return R.mipmap.diagonal;

        }
        return 0;
    }

    public void setType() {
        if (this.isNull()) {
            this.setType(Type.Null);
            return;
        }
        if (!this.isSquareMatrix())
            this.setType(Type.Normal);
        else {
            if (this.isIdentity())
                this.setType(Type.Identity);
            else if (this.isDiagonal())
                this.setType(Type.Diagonal);
            else
                this.setType(Type.Normal);
        }
    }

    public boolean isNull() {
        for (int i = 0; i < this.getNumberOfRows(); i++) {
            for (int j = 0; j < this.getNumberOfCols(); j++)
                if (this.jamaMatrix.get(i, j) != 0)
                    return false;
        }
        return true;
    }

    private boolean isIdentity() {
        for (int i = 0; i < this.getNumberOfRows(); i++) {
            for (int j = 0; j < this.getNumberOfCols(); j++)
                if ((this.jamaMatrix.get(i, j) != 1 && i == j) && (this.jamaMatrix.get(i, j) != 0))
                    return false;
        }
        return true;
    }

    private boolean isDiagonal() {
        int flag1 = 0;
        for (int i = 0; i < this.getNumberOfRows(); i++) {
            for (int j = 0; j < this.getNumberOfCols(); j++)
                if (i != j && this.jamaMatrix.get(i, j) != 0)
                    return false;
                else
                    flag1++;
        }
        return flag1 != this.getNumberOfCols();
    }

    public double getElementOf(int r, int c) throws ExceptionInInitializerError {
        if (this.getNumberOfRows() < r || this.getNumberOfCols() < c || r < 0 || c < 0)
            throw new ExceptionInInitializerError();
        else
            return this.jamaMatrix.get(r, c);
    }

    public void setElementOf(double val, int r, int c) throws ExceptionInInitializerError {
        if (this.getNumberOfCols() < c || this.getNumberOfRows() < r || c < 0 || r < 0)
            throw new ExceptionInInitializerError();
        else
            this.jamaMatrix.set(r, c, val);
    }

    public void cloneFrom(MatrixV2 e) {
        numberOfCols = e.numberOfCols;
        numberOfRows = e.numberOfRows;
        this.copyFrom(e);
        type = e.getType();
        name = e.getName();
    }

    public MatrixV2 exactClone(String newName) {
        MatrixV2 res = MatrixV2.constructFromJamaMatrix(new Matrix(this.jamaMatrix.getArrayCopy()));
        res.name = newName;
        return res;
    }

    public void scalarMultiplication(double multiplier) {
        this.jamaMatrix.timesEquals(multiplier);
    }

    public MatrixV2 getScalarMultiplied(double multiplier) {
        return MatrixV2.constructFromJamaMatrix(this.jamaMatrix.times(multiplier));
    }

    public double[][] expand(int row, int col, double[] values) {
        return MatrixV2.restoreValues(row, col, values);
    }

    public double getRank() {
        return this.jamaMatrix.rank();
    }

    public double getNorm1() {
        return this.jamaMatrix.norm1();
    }

    public double getNormFrobenious() {
        return this.jamaMatrix.normF();
    }

    public double getNorm2() {
        return this.jamaMatrix.norm2();
    }

    public double getNormInfinity() {
        return this.jamaMatrix.normInf();
    }

    public MatrixV2 invertSign() {
        return MatrixV2.constructFromJamaMatrix(this.jamaMatrix.uminus());
    }


    public static double[][] restoreValues(int row, int col, double[] values) {
        double Values[][] = new double[row][col];
        int a = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++)
                Values[i][j] = values[a++];
        }
        return Values;
    }

    public static double[] flattenValues(double[][] elements, int row, int col) {
        double resultant[] = new double[row * col];
        int a = 0;
        for (int i = 0; i < row; i++)
            for (int j = 0; j < col; j++)
                resultant[a++] = elements[i][j];
        return resultant;
    }


    public static MatrixV2 constructFromJamaMatrix(Matrix i) {
        MatrixV2 vv = new MatrixV2(i.getRowDimension(), i.getColumnDimension(), Type.Normal);
        vv.jamaMatrix = Matrix.constructWithCopy(i.getArrayCopy());
        return vv;
    }

    public static MatrixV2 constructFromBundle(Bundle bundle) {
        int r = bundle.getInt("ROW");
        int c = bundle.getInt("COL");
        MatrixV2 matrixV2 = MatrixV2.constructFromJamaMatrix(new Matrix(MatrixV2.restoreValues(r, c, bundle.getDoubleArray("VALUES"))));
        matrixV2.setName(bundle.getString("NAME"));
        matrixV2.setType((Type) bundle.getSerializable("TYPE"));
        return matrixV2;
    }


}
