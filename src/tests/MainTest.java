package tests;

import com.appmath.custom.MyPoint2D;
import com.appmath.geometry.GeometryTask;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigInteger;
import java.util.ArrayList;

public class MainTest {

    private ArrayList<MyPoint2D> points = new ArrayList<MyPoint2D>();

    @BeforeMethod
    public void testDataSetup() {
        points.clear();
    }

    @Test
    public void checkTaskOnPrimitiveFigure() {
        points.add(new MyPoint2D(BigInteger.valueOf(5), BigInteger.valueOf(2)));
        points.add(new MyPoint2D(BigInteger.valueOf(8), BigInteger.valueOf(5)));
        points.add(new MyPoint2D(BigInteger.valueOf(5), BigInteger.valueOf(8)));
        points.add(new MyPoint2D(BigInteger.valueOf(2), BigInteger.valueOf(5)));

        GeometryTask task = new GeometryTask();
        task.setPoints(points);
        task.solveTask();

        int[] resultSquare = task.getAnswerForSquare();
        int[] resultPerimeter = task.getAnswerForPerimeter();

        int[] expectedResult = {3, 0, 1, 2};

        for (int i = 0; i < points.size(); i++) {
            Assert.assertEquals(resultSquare[i], expectedResult[i]);
            Assert.assertEquals(resultPerimeter[i], expectedResult[i]);
        }
    }

}
