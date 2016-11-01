package com.appmath.geometry;

import com.appmath.custom.MyLine;
import com.appmath.custom.MyPoint2D;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;

import static com.appmath.custom.MyLine.*;

public class GeometryTask {

    private ArrayList<MyPoint2D> points = null;
    private MyPoint2D[] answerForSquare = new MyPoint2D[4];
    private MyPoint2D[] answerForPerimeter = new MyPoint2D[4];

    private static final int NUMBER_RECTANGLE_POINTS = 4;

    private class RectangleCharacteristics {
        BigInteger square;
        BigInteger perimeter;
    }

    public void setPoints(ArrayList<MyPoint2D> points) {
        this.points = points;
    }

    public String getAnswerForSquare() {
        return Arrays.toString(answerForSquare);
    }

    public String getAnswerForPerimeter() {
        return Arrays.toString(answerForPerimeter);
    }

    private int[] findStartEndpoints() {
        int pointsListSize = points.size();
        int[] endpointNumbers = {0, 0, 0, 0};
        BigInteger minX = points.get(0).getX(), maxX = points.get(0).getX();
        BigInteger minY = points.get(0).getY(), maxY = points.get(0).getY();
        for (int i = 1; i < pointsListSize; i++) {
            MyPoint2D point = points.get(i);
            BigInteger x = point.getX();
            BigInteger y = point.getY();
            if (x.compareTo(minX) < 0) {
                endpointNumbers[0] = i;
            } else if (x.compareTo(maxX) > 0) {
                endpointNumbers[1] = i;
            }

            if (y.compareTo(minY) < 0) {
                endpointNumbers[2] = i;
            } else if (y.compareTo(maxY) > 0) {
                endpointNumbers[3] = i;
            }
        }

        return endpointNumbers;
    }

    private float[] findStartAngles(int[] endpoints) {
        float[] angleSizeArray = new float[NUMBER_RECTANGLE_POINTS];
        for (int i = 0; i < NUMBER_RECTANGLE_POINTS; i++) {
            int numEndpoint = endpoints[i];
            boolean isDirectedAsY = i < 2;
            MyPoint2D firstPoint = points.get(numEndpoint);
            MyPoint2D secondPoint = numEndpoint > 0 ? points.get(numEndpoint - 1) : points.get(points.size() - 1);
            MyLine firstLine = buildLineByOnePoint(firstPoint, isDirectedAsY);
            MyLine secondLine = buildLineByTwoPoints(firstPoint, secondPoint);
            float angle = findAngleBetweenLines(firstLine, secondLine);
            angleSizeArray[i] = angle;
        }

        return angleSizeArray;
    }

    private float findMinAngle(float[] angles) {
        float minAngle = angles[0];
        for (int i = 1; i < angles.length; i++) {
            if (angles[i] < minAngle)
                minAngle = angles[i];
        }
        return minAngle;
    }

    private int recountAngles(float[] angles, float minAngle) {
        int index = 0;
        for (int i = 0; i < angles.length; i++) {
            angles[i] -= minAngle;
            if (angles[i] == 0) {
                index = i;
            }
        }

        return index;
    }

    private void recountEndpointAndAngle(int[] endpoints, float[] angle, int index) {
        int numEndpoint = endpoints[index];
        endpoints[index] = numEndpoint > 0 ? numEndpoint - 1 : points.size() - 1;

        MyPoint2D firstPoint = points.get(numEndpoint);
        MyPoint2D secondPoint = points.get(endpoints[index]);
        MyLine firstLine = buildLineByTwoPoints(firstPoint, secondPoint);

        int numNextPoint = endpoints[index] > 0 ? numEndpoint - 1 : points.size() - 1;
        firstPoint = secondPoint;
        secondPoint = points.get(numNextPoint);
        MyLine secondLine = buildLineByTwoPoints(firstPoint, secondPoint);
        angle[index] = findAngleBetweenLines(firstLine, secondLine);
    }

    private RectangleCharacteristics countRectangleCharacteristics(int[] endpoints, int indexOfBearingPoint) {
        // find pair of calipers, which include endpoint
        int bearingEndpoint = endpoints[indexOfBearingPoint];
        int parallelBearingEndpointIndex = (indexOfBearingPoint % 2 == 0) ? indexOfBearingPoint + 1 : indexOfBearingPoint - 1;
        MyPoint2D firstPoint = points.get(bearingEndpoint);
        MyPoint2D secondPoint = bearingEndpoint > 0 ? points.get(bearingEndpoint - 1) : points.get(points.size() - 1);
        MyLine bearingLine = buildLineByTwoPoints(firstPoint, secondPoint);
        MyPoint2D tmpPoint = points.get(endpoints[parallelBearingEndpointIndex]);
        MyLine parallelBearingLine = buildParallelLineByOnePoint(bearingLine, tmpPoint);

        // find pair of perpendicular calipers
        int tmpEndpointIndex = indexOfBearingPoint > 1 ? 2 : 0;
        tmpPoint = points.get(endpoints[tmpEndpointIndex]);
        MyLine firstPerpendicularLine = buildPerpendicularLineByOnePoint(bearingLine, tmpPoint);
        tmpPoint = points.get(endpoints[tmpEndpointIndex + 1]);
        MyLine secondPerpendicularLine = buildPerpendicularLineByOnePoint(bearingLine, tmpPoint);

        // find rectangle's sides
        BigInteger lengthOfFirstInterval = countIntervalLength(bearingLine, firstPerpendicularLine, secondPerpendicularLine);
        BigInteger lengthOfSecondInterval = countIntervalLength(firstPerpendicularLine, bearingLine, parallelBearingLine);

        // count rectangle's square and perimeter
        RectangleCharacteristics rectangle = new RectangleCharacteristics();
        rectangle.square = lengthOfFirstInterval.multiply(lengthOfSecondInterval);
        rectangle.perimeter = (lengthOfFirstInterval.add(lengthOfSecondInterval)).multiply(BigInteger.valueOf(2));
        return rectangle;
    }

    public void solveTask() {
        int[] endpoints = findStartEndpoints();
        float[] angles = findStartAngles(endpoints);
        int tmpEndpointFirst = endpoints[0], tmpEndpointSecond = endpoints[1];

        BigInteger minSquare = BigInteger.ZERO;
        BigInteger minPerimeter = BigInteger.ZERO;
        int[] minSquareEndpoints = new int[NUMBER_RECTANGLE_POINTS];
        int[] minPerimeterEndpoints = new int[NUMBER_RECTANGLE_POINTS];

        do {
            float minAngle = findMinAngle(angles);
            int indexOfMinAngle = recountAngles(angles, minAngle);
            RectangleCharacteristics rect = countRectangleCharacteristics(endpoints, indexOfMinAngle);
            BigInteger rectSquare = rect.square;
            BigInteger rectPerimeter = rect.perimeter;
            if (rectSquare.compareTo(minSquare) < 0 || minSquare.equals(BigInteger.ZERO)) {
                minSquare = rectSquare;
                System.arraycopy(endpoints, 0, minSquareEndpoints, 0, endpoints.length);
            }

            if (rectPerimeter.compareTo(minPerimeter) < 0 || minPerimeter.equals(BigInteger.ZERO)) {
                minPerimeter = rectPerimeter;
                System.arraycopy(endpoints, 0, minPerimeterEndpoints, 0, endpoints.length);
            }

            recountEndpointAndAngle(endpoints, angles, indexOfMinAngle);
        } while ((endpoints[1] != tmpEndpointFirst) && (endpoints[0] != tmpEndpointSecond));

        for (int i = 0; i < NUMBER_RECTANGLE_POINTS; i++) {
            answerForSquare[i] = points.get(minSquareEndpoints[i]);
            answerForPerimeter[i] = points.get(minPerimeterEndpoints[i]);
        }
    }
}
