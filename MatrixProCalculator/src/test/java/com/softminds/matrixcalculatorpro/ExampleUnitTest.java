package com.softminds.matrixcalculatorpro;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void RowMultiples() throws Exception{
        Matrix matrix = new Matrix();
        matrix.SetCol(3);
        matrix.SetRow(3);
        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++)
                matrix.SetElementof(i*10+j,i,j);
        matrix.SetElementof(0,0,2);
        matrix.SetElementof(10,1,2);
        matrix.SetElementof(20,2,2);
        System.out.println(matrix.toString());
        /*assertTrue(matrix.RowEqualModulo());*/
    }
}