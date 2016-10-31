package com.appmath.geometry;

import com.appmath.custom.MyLine;
import com.appmath.custom.MyPoint2D;

import java.math.BigInteger;
import java.util.ArrayList;

import static com.appmath.custom.MyLine.buildLineByOnePoint;
import static com.appmath.custom.MyLine.buildLineByTwoPoints;
import static com.appmath.custom.MyLine.findAngleBetweenLines;

public class GeometryTask {

    private ArrayList<MyPoint2D> points = null;
    private MyPoint2D[] answerForSquare = new MyPoint2D[4];
    private MyPoint2D[] answerForPerimeter = new MyPoint2D[4];

    private static final int NUMBER_RECTANGLE_POINTS = 4;

    public void setPoints(ArrayList<MyPoint2D> points) {
        this.points = points;
    }

    public String getAnswerForSquare() {
        return answerForSquare.toString();
    }

    public String getAnswerForPerimeter() {
        return answerForPerimeter.toString();
    }

    private int[] findEndpoints() {
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

    private float[] findAngles(int[] endpoints) {
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

    private void recountEndpoints(int[] endpoints) {
        for (int i = 0; i < endpoints.length; i++) {
            int numEndpoint = endpoints[i];
            endpoints[i] = numEndpoint > 0 ? numEndpoint - 1 : points.size() - 1;
        }
    }

    private BigInteger countRectangleSquare(int[] endpoints, int indexOfBearingPoint) {
        BigInteger square = BigInteger.ZERO;
        int bearingEndpoint = endpoints[indexOfBearingPoint];
        int parallelBearingEndpoint = (indexOfBearingPoint % 2 == 0) ? indexOfBearingPoint + 1 : indexOfBearingPoint - 1;
        MyPoint2D firstPoint = points.get(bearingEndpoint);
        MyPoint2D secondPoint = bearingEndpoint > 0 ? points.get(bearingEndpoint - 1) : points.get(points.size() - 1);
        MyLine bearingLine = buildLineByTwoPoints(firstPoint, secondPoint);
        MyPoint2D tmpPoint = points.get(parallelBearingEndpoint);
        MyLine parallelBearingLine = buildParallelLineByOnePoint(bearingLine, tmpPoint);
        //MyLine line;
        //MyLine line;
        // найти два отрезка
        // посчитать площадь
        return square;
    }

    public void solveTaskForSquare() {
        int[] endpoints = findEndpoints();
        int tmpEndpoint = endpoints[0];
        float[] angles = findAngles(endpoints);
        BigInteger minSquare = BigInteger.ZERO;
        int[] minEndpoints = new int[NUMBER_RECTANGLE_POINTS];
        do {
            float minAngle = findMinAngle(angles);
            int indexOfMinAngle = recountAngles(angles, minAngle);
            BigInteger rectSquare = countRectangleSquare(endpoints, indexOfMinAngle);
            if (rectSquare.compareTo(minSquare) < 0) {
                minSquare = rectSquare;
                System.arraycopy(endpoints, 0, minEndpoints, 0, endpoints.length);
            }
            recountEndpoints(endpoints);
        } while (endpoints[0] != tmpEndpoint);

        for (int i = 0; i < NUMBER_RECTANGLE_POINTS; i++) {
            answerForSquare[i] = points.get(minEndpoints[i]);
        }
    }

    public void solveTaskForPerimeter() {
        //answerForPerimeter = points;
    }
}
