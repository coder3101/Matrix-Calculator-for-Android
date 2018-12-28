package com.softminds.matrixcalculator;

import android.app.ProgressDialog;
import android.content.Context;

import org.junit.Test;

import java.text.DecimalFormat;

import Jama.Matrix;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
//        double va[][] = {{0, -2, 2, 7}, {0, 0, 0, 3}, {0, 1, 5, 0}, {0, -1, -9, 4}};
//        Matrix jm = Matrix.constructWithCopy(va);
//        jm.solve(Matrix.identity(4, 4).times(jm.det())).print(2, 2);
//
        DecimalFormat fourth = new DecimalFormat("########.#####");
        System.out.print(fourth.format(11.99999999));
    }
}