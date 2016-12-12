package tests;

import com.appmath.custom.MyPoint2D;
import com.appmath.geometry.GeometryTask;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigInteger;
import java.util.ArrayList;

public class MainTest {

    private static final int NUMBER_RECTANGLE_POINTS = 4;

    private ArrayList<MyPoint2D> points = new ArrayList<MyPoint2D>();

    @BeforeMethod
    public void testDataSetup() {
        points.clear();
    }

    @Test
    public void checkTaskOnThreeAnglesFigure() {
        points.add(new MyPoint2D(BigInteger.valueOf(1), BigInteger.valueOf(1)));
        points.add(new MyPoint2D(BigInteger.valueOf(1), BigInteger.valueOf(3)));
        points.add(new MyPoint2D(BigInteger.valueOf(2), BigInteger.valueOf(2)));

        GeometryTask task = new GeometryTask();
        task.setPoints(points);
        task.solveTask();

        int[] resultSquare = task.getAnswerForSquare();
        int[] resultPerimeter = task.getAnswerForPerimeter();

        int[] expectedResult = {2, 2, 0, 1};

        for (int i = 0; i < NUMBER_RECTANGLE_POINTS; i++) {
            Assert.assertEquals(resultSquare[i], expectedResult[i]);
            Assert.assertEquals(resultPerimeter[i], expectedResult[i]);
        }
    }

    @Test
    public void checkTaskOnFourAnglesFigure() {
        points.add(new MyPoint2D(BigInteger.valueOf(2), BigInteger.valueOf(1)));
        points.add(new MyPoint2D(BigInteger.valueOf(6), BigInteger.valueOf(3)));
        points.add(new MyPoint2D(BigInteger.valueOf(5), BigInteger.valueOf(4)));
        points.add(new MyPoint2D(BigInteger.valueOf(1), BigInteger.valueOf(2)));

        GeometryTask task = new GeometryTask();
        task.setPoints(points);
        task.solveTask();

        int[] resultSquare = task.getAnswerForSquare();
        int[] resultPerimeter = task.getAnswerForPerimeter();

        int[] expectedResult = {3, 3, 0, 1};

        for (int i = 0; i < NUMBER_RECTANGLE_POINTS; i++) {
            Assert.assertEquals(resultSquare[i], expectedResult[i]);
            Assert.assertEquals(resultPerimeter[i], expectedResult[i]);
        }
    }

    @Test
    public void checkTaskOnFiveAnglesFigure() {
        points.add(new MyPoint2D(BigInteger.valueOf(5), BigInteger.valueOf(2)));
        points.add(new MyPoint2D(BigInteger.valueOf(8), BigInteger.valueOf(5)));
        points.add(new MyPoint2D(BigInteger.valueOf(5), BigInteger.valueOf(8)));
        points.add(new MyPoint2D(BigInteger.valueOf(2), BigInteger.valueOf(6)));
        points.add(new MyPoint2D(BigInteger.valueOf(2), BigInteger.valueOf(4)));

        GeometryTask task = new GeometryTask();
        task.setPoints(points);
        task.solveTask();

        int[] resultSquare = task.getAnswerForSquare();
        int[] resultPerimeter = task.getAnswerForPerimeter();

        int[] expectedResult = {2, 3, 4, 0};

        for (int i = 0; i < NUMBER_RECTANGLE_POINTS; i++) {
            Assert.assertEquals(resultSquare[i], expectedResult[i]);
            Assert.assertEquals(resultPerimeter[i], expectedResult[i]);
        }
    }


}
