package com.appmath.geometry;

import com.appmath.custom.MyLine;
import com.appmath.custom.MyPoint2D;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

import static com.appmath.custom.MyLine.*;

public class GeometryTask {

    private static final int NUMBER_RECTANGLE_POINTS = 4;

    private ArrayList<MyPoint2D> points = null;
    private int[] answerForSquare = new int[NUMBER_RECTANGLE_POINTS];
    private int[] answerForPerimeter = new int[NUMBER_RECTANGLE_POINTS];

    private MyPoint2D[] endpoints = new MyPoint2D[NUMBER_RECTANGLE_POINTS];
    private int[] endpointsIndexes = new int[NUMBER_RECTANGLE_POINTS];
    private MyPoint2D[] endpointsNext = new MyPoint2D[NUMBER_RECTANGLE_POINTS];

    private class RectangleCharacteristics {
        BigInteger square;
        BigInteger perimeter;
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

    private void findStartEndpoints() {
        int pointsListSize = points.size();
        BigInteger minX = points.get(0).getX(), maxX = points.get(0).getX();
        BigInteger minY = points.get(0).getY(), maxY = points.get(0).getY();

        for (int i = 0; i < pointsListSize; i++) {
            MyPoint2D point = points.get(i);
            BigInteger x = point.getX();
            BigInteger y = point.getY();
            if (x.compareTo(minX) <= 0) {
                endpoints[0] = points.get(i);
                endpointsIndexes[0] = i;
                endpointsNext[0] = (i == 0) ? points.get(pointsListSize - 1) : points.get(i - 1);
                minX = x;
            } else if (x.compareTo(maxX) >= 0) {
                endpoints[2] = points.get(i);
                endpointsIndexes[2] = i;
                endpointsNext[2] = (i == 0) ? points.get(pointsListSize - 1) : points.get(i - 1);
                maxX = x;
            }

            if (y.compareTo(minY) <= 0) {
                endpoints[1] = points.get(i);
                endpointsIndexes[1] = i;
                endpointsNext[1] = (i == 0) ? points.get(pointsListSize - 1) : points.get(i - 1);
                minY = y;
            } else if (y.compareTo(maxY) >= 0) {
                endpoints[3] = points.get(i);
                endpointsIndexes[3] = i;
                endpointsNext[3] = (i == 0) ? points.get(pointsListSize - 1) : points.get(i - 1);
                maxY = y;
            }
        }
    }

    private MyPoint2D countTriangleSquare(MyPoint2D p, MyPoint2D v1, MyPoint2D v2) {
        BigInteger x1 = p.getX(), y1 = p.getY();
        BigInteger x2 = v1.getX().add(x1), y2 = v1.getY().add(y1);
        BigInteger x3 = v2.getX().add(x1), y3 = v2.getY().add(y2);

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

        MyPoint2D v1 = new MyPoint2D(endpointsNext[numPrevMinAngle].getX().subtract(endpoints[numPrevMinAngle].getX()),
                endpointsNext[numPrevMinAngle].getY().subtract(endpoints[numPrevMinAngle].getY()));

        pointIndex = pointIndex < NUMBER_RECTANGLE_POINTS - 1 ? pointIndex + 1 : 0;
        MyPoint2D v2 = new MyPoint2D(endpointsNext[pointIndex].getY().subtract(endpoints[pointIndex].getY()).negate(),
                endpointsNext[pointIndex].getX().subtract(endpoints[pointIndex].getX()));

        MyPoint2D v = countTriangleSquare(bearingPoint, v1, v2);
        if (v.equals(v1))
            minAngleIndex = numPrevMinAngle;
        else
            minAngleIndex = pointIndex;

        pointIndex = pointIndex < NUMBER_RECTANGLE_POINTS - 1 ? pointIndex + 1 : 0;
        MyPoint2D v3 = new MyPoint2D(endpointsNext[pointIndex].getX().subtract(endpoints[pointIndex].getX()).negate(),
                endpointsNext[pointIndex].getY().subtract(endpoints[pointIndex].getY()).negate());

        v = countTriangleSquare(bearingPoint, v, v3);
        if (v.equals(v3))
            minAngleIndex = pointIndex;

        pointIndex = pointIndex < NUMBER_RECTANGLE_POINTS - 1 ? pointIndex + 1 : 0;
        MyPoint2D v4 = new MyPoint2D(endpointsNext[pointIndex].getY().subtract(endpoints[pointIndex].getY()),
                endpointsNext[pointIndex].getX().subtract(endpoints[pointIndex].getX()));

        v = countTriangleSquare(bearingPoint, v, v4);
        if (v.equals(v4))
            minAngleIndex = pointIndex;

        return minAngleIndex;
    }

    private RectangleCharacteristics countRectangleCharacteristics(int indexOfBearingPoint) {
        // find pair of calipers, which include endpoint
        /*int bearingEndpoint = endpoints[indexOfBearingPoint];
        int parallelBearingEndpointIndex = (indexOfBearingPoint < 2) ? indexOfBearingPoint + 2 : indexOfBearingPoint - 2;
        MyPoint2D firstPoint = points.get(bearingEndpoint);
        MyPoint2D secondPoint = bearingEndpoint > 0 ? points.get(bearingEndpoint - 1) : points.get(points.size() - 1);

         find pair of perpendicular calipers
        int tmpEndpointIndex = (indexOfBearingPoint % 2 == 0) ? 1 : 0;
        MyPoint2D perpendicularPoint = points.get(endpoints[tmpEndpointIndex]);

         find rectangle's sides
        BigDecimal firstSide = countDistanceBetweenLineAndPoint(bearingLine, parallelLinePoint);
        BigDecimal secondSide = countDistanceBetweenLineAndPoint(perpendicularLine, perpendicularPoint);*/

        // count rectangle's square and perimeter
        RectangleCharacteristics rectangle = new RectangleCharacteristics();
        rectangle.square = BigInteger.ONE;
        rectangle.perimeter = BigInteger.ONE;

        return rectangle;
    }

    public void solveTask() {
        int numIterations = 0, numPoints = points.size();
        int minAngle = 1;

        BigInteger minSquare = BigInteger.ZERO;
        BigInteger minPerimeter = BigInteger.ZERO;
        int[] minSquarePoints = new int[NUMBER_RECTANGLE_POINTS];
        int[] minPerimeterPoints = new int[NUMBER_RECTANGLE_POINTS];

        findStartEndpoints();

        do {
            // find square and perimeter of current rectangle
            RectangleCharacteristics rect = countRectangleCharacteristics(minAngle);

            // save min square and min perimeter points
            BigInteger rectSquare = rect.square;
            BigInteger rectPerimeter = rect.perimeter;
            if (rectSquare.compareTo(minSquare) < 0 || minSquare.equals(BigInteger.ZERO)) {
                minSquare = rectSquare;
                System.arraycopy(endpointsIndexes, 0, minSquarePoints, 0, endpoints.length);
            }

            if (rectPerimeter.compareTo(minPerimeter) < 0 || minPerimeter.equals(BigInteger.ZERO)) {
                minPerimeter = rectPerimeter;
                System.arraycopy(endpointsIndexes, 0, minPerimeterPoints, 0, endpoints.length);
            }

            // find endpoint with min angle and recount endpoint
            minAngle = findMinAngle(minAngle);
            endpoints[minAngle] = endpointsNext[minAngle];
            endpointsIndexes[minAngle] = (endpointsIndexes[minAngle] == 0) ? points.size() - 1 : endpointsIndexes[minAngle] - 1;
            endpointsNext[minAngle] = (endpointsIndexes[minAngle] == 0) ?
                    points.get(points.size() - 1) : points.get(endpointsIndexes[minAngle] - 1);

            // new iteration
            numIterations++;

        } while (numIterations <= 4 * numPoints);

        System.arraycopy(minSquarePoints, 0, answerForSquare, 0, endpoints.length);
        System.arraycopy(minPerimeterPoints, 0, answerForPerimeter, 0, endpoints.length);
    }
}
