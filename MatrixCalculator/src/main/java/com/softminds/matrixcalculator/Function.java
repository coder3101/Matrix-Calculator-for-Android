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


import java.util.ArrayList;

public class Function {
    private MatrixV2 ConstantMatrix;
    private ArrayList<Terms> terms;

    private static class Terms {
        private int Exponent;
        private boolean Sign; //true  is Positive else False;
        private float Coefficient;

        private Terms(float coefficient, int exponent, boolean sign) {
            Exponent = exponent;
            Sign = sign;
            Coefficient = coefficient;
        }

        private int getExponent() {
            return Exponent;
        }

        private boolean getSign() {
            return Sign;
        }

        private float getCoefficient() {
            return Coefficient;
        }
    }

    public Function(int R, int C, float Const) {
        ConstantMatrix = new MatrixV2(R, C, Type.Normal);
        ConstantMatrix.makeIdentity();
        ConstantMatrix.scalarMultiplication(Const);
        terms = new ArrayList<>();
    }

    public void AddTerms(float Coe, int Expo, boolean sign) {
        Terms t = new Terms(Coe, Expo, sign);
        terms.add(t);
    }

    public MatrixV2 ComputeFunction(MatrixV2 operand) {
        MatrixV2 OriginalCopy = new MatrixV2(operand.getNumberOfRows(), operand.getNumberOfCols(), operand.getType());
        OriginalCopy.cloneFrom(operand);
        MatrixV2 Resultant = new MatrixV2(operand.getNumberOfRows(), operand.getNumberOfCols(), operand.getType());
        Resultant.makeNull();
        for (int i = 0; i < terms.size(); i++) {
            operand.cloneFrom(OriginalCopy);
            int expo = terms.get(i).getExponent();
            boolean s = terms.get(i).getSign();
            operand.raisedToPower(expo);
            if (s)
                operand.scalarMultiplication(1 * terms.get(i).getCoefficient());
            else
                operand.scalarMultiplication(-1 * terms.get(i).getCoefficient());
            Resultant.addToThis(operand);
        }
        Resultant.addToThis(ConstantMatrix);
        return Resultant;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < terms.size(); i++) {
            s.append(Sign(terms.get(i).getSign()));
            s.append(String.valueOf(terms.get(i).getCoefficient()));
            s.append(String.valueOf(terms.get(i).getExponent()));
            s.append(" ");
        }
        return s.toString();
    }

    private String Sign(boolean b) {
        if (b)
            return "+";
        else
            return "-";
    }


}
