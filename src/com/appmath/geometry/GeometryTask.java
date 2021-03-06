package com.appmath.geometry;

import com.appmath.custom.MyFraction;
import com.appmath.custom.MyLine;
import com.appmath.custom.MyPoint2D;

import java.math.BigInteger;
import java.util.ArrayList;

import static com.appmath.custom.MyLine.*;
import static java.math.BigInteger.ONE;

public class GeometryTask {

    private static final int NUMBER_RECTANGLE_POINTS = 4;

    private ArrayList<MyPoint2D> points = null;
    private int[] answerForSquare = new int[NUMBER_RECTANGLE_POINTS];
    private int[] answerForPerimeter = new int[NUMBER_RECTANGLE_POINTS];

    private MyPoint2D[] endpoints = new MyPoint2D[NUMBER_RECTANGLE_POINTS];
    private int[] endpointsIndexes = new int[NUMBER_RECTANGLE_POINTS];
    private MyPoint2D[] endpointsNext = new MyPoint2D[NUMBER_RECTANGLE_POINTS];

    private class RectangleCharacteristics {
        MyFraction square;
        MyFraction perimeter;

        RectangleCharacteristics() {
            square = new MyFraction(BigInteger.ZERO, ONE);
            perimeter = new MyFraction(BigInteger.ZERO, ONE);
        }
    }

    public void setPoints(ArrayList<MyPoint2D> points) {
        this.points = points;
    }

    public int[] getAnswerForSquare() {
        return answerForSquare;
    }

    public int[] getAnswerForPerimeter() {
        return answerForPerimeter;
    }

    private void setEndpointsValue(int index, int num, int pointsListSize) {
        endpoints[index] = points.get(num);
        endpointsIndexes[index] = num;
        endpointsNext[index] = (num == pointsListSize - 1) ? points.get(0) : points.get(num + 1);
    }

    private void findStartEndpoints() {
        int pointsListSize = points.size();
        BigInteger minX = points.get(0).getX(), maxX = points.get(0).getX();
        BigInteger minY = points.get(0).getY(), maxY = points.get(0).getY();

        for (int i = 0; i < NUMBER_RECTANGLE_POINTS; i++) {
            setEndpointsValue(i, 0, pointsListSize);
        }

        for (int i = 1; i < pointsListSize; i++) {
            MyPoint2D point = points.get(i);
            BigInteger x = point.getX();
            BigInteger y = point.getY();

            if (x.compareTo(minX) <= 0) {
                setEndpointsValue(0, i, pointsListSize);
                minX = x;
            } else if (x.compareTo(maxX) >= 0) {
                setEndpointsValue(2, i, pointsListSize);
                maxX = x;
            }

            if (y.compareTo(minY) <= 0) {
                setEndpointsValue(1, i, pointsListSize);
                minY = y;
            } else if (y.compareTo(maxY) >= 0) {
                setEndpointsValue(3, i, pointsListSize);
                maxY = y;
            }
        }
    }

    private MyPoint2D countTriangleSquare(MyPoint2D p, MyPoint2D v1, MyPoint2D v2) {
        BigInteger x1 = p.getX(), y1 = p.getY();
        BigInteger x2 = v1.getX().add(x1), y2 = v1.getY().add(y1);
        BigInteger x3 = v2.getX().add(x1), y3 = v2.getY().add(y1);

        // count determinant
        BigInteger square = x1.multiply(y2).subtract(x2.multiply(y1))
                .add(x3.multiply(y1).subtract(x1.multiply(y3)))
                .add(x2.multiply(y3).subtract(x3.multiply(y2)));

        if (square.compareTo(BigInteger.ZERO) > 0)
            return v1;
        return v2;
    }

    private int findMinAngle(int numPrevMinAngle) {
        int minAngleIndex = 0, pointIndex = numPrevMinAngle;

        MyPoint2D bearingPoint = endpoints[numPrevMinAngle];

        // find vector which contains bearing endpoint
        MyPoint2D v1 = new MyPoint2D(endpointsNext[numPrevMinAngle].getX().subtract(endpoints[numPrevMinAngle].getX()),
                endpointsNext[numPrevMinAngle].getY().subtract(endpoints[numPrevMinAngle].getY()));

        // find vector which contains next endpoint
        pointIndex = pointIndex < NUMBER_RECTANGLE_POINTS - 1 ? pointIndex + 1 : 0;
        MyPoint2D v2 = new MyPoint2D(endpointsNext[pointIndex].getY().subtract(endpoints[pointIndex].getY()),
                endpointsNext[pointIndex].getX().subtract(endpoints[pointIndex].getX()).negate());

        // find which of them forms smaller angle
        MyPoint2D v = countTriangleSquare(bearingPoint, v1, v2);
        if (v.equals(v1))
            minAngleIndex = numPrevMinAngle;
        else
            minAngleIndex = pointIndex;

        // find vector which contains third endpoint
        pointIndex = pointIndex < NUMBER_RECTANGLE_POINTS - 1 ? pointIndex + 1 : 0;
        MyPoint2D v3 = new MyPoint2D(endpointsNext[pointIndex].getX().subtract(endpoints[pointIndex].getX()).negate(),
                endpointsNext[pointIndex].getY().subtract(endpoints[pointIndex].getY()).negate());

        // compare angles
        v = countTriangleSquare(bearingPoint, v, v3);
        if (v.equals(v3))
            minAngleIndex = pointIndex;

        // vector with forth endpoint
        pointIndex = pointIndex < NUMBER_RECTANGLE_POINTS - 1 ? pointIndex + 1 : 0;
        MyPoint2D v4 = new MyPoint2D(endpointsNext[pointIndex].getY().subtract(endpoints[pointIndex].getY()).negate(),
                endpointsNext[pointIndex].getX().subtract(endpoints[pointIndex].getX()));

        // compare angles
        v = countTriangleSquare(bearingPoint, v, v4);
        if (v.equals(v4))
            minAngleIndex = pointIndex;

        return minAngleIndex;
    }

    private RectangleCharacteristics countRectangleCharacteristics(int indexOfBearingPoint) {
        int indexOfPrevPoint = (endpointsIndexes[indexOfBearingPoint] == 0) ?
                points.size() - 1 : endpointsIndexes[indexOfBearingPoint] - 1;
        MyLine firstLine = buildLineByTwoPoints(endpoints[indexOfBearingPoint], points.get(indexOfPrevPoint));
        int secondPointIndex = (indexOfBearingPoint < 2) ? indexOfBearingPoint + 2 : indexOfBearingPoint - 2;
        int thirdPointIndex = (indexOfBearingPoint % 2 == 0) ? 1 : 0;
        MyLine secondLine = buildPerpendicularLineByOnePoint(firstLine, endpoints[thirdPointIndex]);

        // find sides of rectangle
        MyFraction firstSide = countDistanceBetweenLineAndPoint(firstLine, endpoints[secondPointIndex]);
        MyFraction secondSide = countDistanceBetweenLineAndPoint(secondLine, endpoints[thirdPointIndex + 2]);

        // count rectangle's square and perimeter
        RectangleCharacteristics rectangle = new RectangleCharacteristics();
        rectangle.square.setNum(firstSide.getNum().multiply(secondSide.getNum()));
        rectangle.square.setDenum(firstSide.getDenum());

        rectangle.perimeter.setNum(firstSide.getNum().add(secondSide.getNum()).pow(2));
        rectangle.perimeter.setDenum(firstSide.getDenum());

        return rectangle;
    }

    public void solveTask() {
        int numIterations = 0, numPoints = points.size();
        int minAngle = 1;

        MyFraction minSquare = new MyFraction(BigInteger.ONE, BigInteger.ONE.negate());
        MyFraction minPerimeter = new MyFraction(BigInteger.ONE, BigInteger.ONE.negate());
        int[] minSquarePoints = new int[NUMBER_RECTANGLE_POINTS];
        int[] minPerimeterPoints = new int[NUMBER_RECTANGLE_POINTS];

        findStartEndpoints();

        minAngle = findMinAngle(minAngle);
        endpoints[minAngle] = endpointsNext[minAngle];
        endpointsIndexes[minAngle] = (endpointsIndexes[minAngle] == points.size() - 1) ?
                0 : endpointsIndexes[minAngle] + 1;
        endpointsNext[minAngle] = (endpointsIndexes[minAngle] == points.size() - 1) ?
                points.get(0) : points.get(endpointsIndexes[minAngle] + 1);

        do {
            // find square and perimeter of current rectangle
            RectangleCharacteristics rect = countRectangleCharacteristics(minAngle);

            // save min square and min perimeter points
            MyFraction rectSquare = rect.square;
            MyFraction rectPerimeter = rect.perimeter;


            if ( (!rectSquare.getNum().equals(BigInteger.ZERO) &&
                    rectSquare.getNum().multiply(minSquare.getDenum())
                            .compareTo(minSquare.getNum().multiply(rectSquare.getDenum())) < 0)) {

                minSquare = rectSquare;
                for (int i = 0; i < NUMBER_RECTANGLE_POINTS; i++) {
                    if (i >= minAngle)
                        minSquarePoints[i - minAngle] = endpointsIndexes[i];
                    else
                        minSquarePoints[i + NUMBER_RECTANGLE_POINTS - minAngle] = endpointsIndexes[i];
                }
            }

            if ( (!rectPerimeter.getNum().equals(BigInteger.ZERO) &&
                    !rectSquare.getNum().equals(BigInteger.ZERO) &&
                    rectPerimeter.getNum().multiply(minPerimeter.getDenum()).
                            compareTo(minPerimeter.getNum().multiply(rectPerimeter.getDenum())) < 0)) {

                minPerimeter = rectPerimeter;
                for (int i = 0; i < NUMBER_RECTANGLE_POINTS; i++) {
                    if (i >= minAngle)
                        minPerimeterPoints[i - minAngle] = endpointsIndexes[i];
                    else
                        minPerimeterPoints[i + NUMBER_RECTANGLE_POINTS - minAngle] = endpointsIndexes[i];
                }
            }

            // find endpoint with min angle and recount endpoint
            minAngle = findMinAngle(minAngle);
            endpoints[minAngle] = endpointsNext[minAngle];
            endpointsIndexes[minAngle] = (endpointsIndexes[minAngle] == points.size() - 1) ?
                    0 : endpointsIndexes[minAngle] + 1;
            endpointsNext[minAngle] = (endpointsIndexes[minAngle] == points.size() - 1) ?
                    points.get(0) : points.get(endpointsIndexes[minAngle] + 1);

            // new iteration
            numIterations++;

        } while (numIterations <= 4 * numPoints);

        System.arraycopy(minSquarePoints, 0, answerForSquare, 0, endpoints.length);
        System.arraycopy(minPerimeterPoints, 0, answerForPerimeter, 0, endpoints.length);
    }
}
