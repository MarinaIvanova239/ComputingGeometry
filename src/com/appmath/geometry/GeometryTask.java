package com.appmath.geometry;

import com.appmath.custom.MyLine;
import com.appmath.custom.MyPoint2D;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

import static com.appmath.custom.MyLine.*;

public class GeometryTask {

    private ArrayList<MyPoint2D> points = null;
    private int[] answerForSquare = new int[4];
    private int[] answerForPerimeter = new int[4];

    private static final int NUMBER_RECTANGLE_POINTS = 4;

    private class RectangleCharacteristics {
        BigDecimal square;
        BigDecimal perimeter;
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

    private int[] findStartEndpoints() {
        int pointsListSize = points.size();
        int[] endpointNumbers = {0, 0, 0, 0};
        BigInteger minX = points.get(0).getX(), maxX = points.get(0).getX();
        BigInteger minY = points.get(0).getY(), maxY = points.get(0).getY();
        for (int i = 0; i < pointsListSize; i++) {
            MyPoint2D point = points.get(i);
            BigInteger x = point.getX();
            BigInteger y = point.getY();
            if (x.compareTo(minX) < 0) {
                endpointNumbers[0] = i;
                minX = x;
            } else if (x.compareTo(maxX) > 0) {
                endpointNumbers[2] = i;
                maxX = x;
            }

            if (y.compareTo(minY) < 0) {
                endpointNumbers[1] = i;
                minY = y;
            } else if (y.compareTo(maxY) > 0) {
                endpointNumbers[3] = i;
                maxY = y;
            }
        }

        return endpointNumbers;
    }

    private float[] findStartAngles(int[] endpoints) {
        float[] angleSizeArray = new float[NUMBER_RECTANGLE_POINTS];
        for (int i = 0; i < NUMBER_RECTANGLE_POINTS; i++) {
            int numEndpoint = endpoints[i];
            boolean isDirectedAsY = (i % 2 == 0);
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

        int numNextPoint = endpoints[index] > 0 ? endpoints[index] - 1 : points.size() - 1;
        firstPoint = secondPoint;
        secondPoint = points.get(numNextPoint);
        MyLine secondLine = buildLineByTwoPoints(firstPoint, secondPoint);
        angle[index] = findAngleBetweenLines(firstLine, secondLine);
    }

    private RectangleCharacteristics countRectangleCharacteristics(int[] endpoints, int indexOfBearingPoint) {
        // find pair of calipers, which include endpoint
        int bearingEndpoint = endpoints[indexOfBearingPoint];
        int parallelBearingEndpointIndex = (indexOfBearingPoint < 2) ? indexOfBearingPoint + 2 : indexOfBearingPoint - 2;
        MyPoint2D firstPoint = points.get(bearingEndpoint);
        MyPoint2D secondPoint = bearingEndpoint > 0 ? points.get(bearingEndpoint - 1) : points.get(points.size() - 1);
        MyLine bearingLine = buildLineByTwoPoints(firstPoint, secondPoint);
        MyPoint2D parallelLinePoint = points.get(endpoints[parallelBearingEndpointIndex]);

        // find pair of perpendicular calipers
        int tmpEndpointIndex = (indexOfBearingPoint % 2 == 0) ? 1 : 0;
        MyPoint2D perpendicularPoint = points.get(endpoints[tmpEndpointIndex]);
        MyLine perpendicularLine = buildPerpendicularLineByOnePoint(bearingLine, perpendicularPoint);
        perpendicularPoint = points.get(endpoints[tmpEndpointIndex + 2]);

        // find rectangle's sides
        BigDecimal firstSide = countDistanceBetweenLineAndPoint(bearingLine, parallelLinePoint);
        BigDecimal secondSide = countDistanceBetweenLineAndPoint(perpendicularLine, perpendicularPoint);

        // count rectangle's square and perimeter
        RectangleCharacteristics rectangle = new RectangleCharacteristics();
        rectangle.square = firstSide.multiply(secondSide);
        rectangle.perimeter = (firstSide.add(secondSide)).multiply(BigDecimal.valueOf(2));

        return rectangle;
    }

    public void solveTask() {
        int[] endpoints = findStartEndpoints();
        float[] angles = findStartAngles(endpoints);
        int tmpEndpointFirst = endpoints[0], tmpEndpointSecond = endpoints[2];

        BigDecimal minSquare = BigDecimal.ZERO;
        BigDecimal minPerimeter = BigDecimal.ZERO;
        int[] minSquarePoints = new int[NUMBER_RECTANGLE_POINTS];
        int[] minPerimeterPoints = new int[NUMBER_RECTANGLE_POINTS];

        do {
            float minAngle = findMinAngle(angles);
            int indexOfMinAngle = recountAngles(angles, minAngle);
            RectangleCharacteristics rect = countRectangleCharacteristics(endpoints, indexOfMinAngle);
            BigDecimal rectSquare = rect.square;
            BigDecimal rectPerimeter = rect.perimeter;
            if (rectSquare.compareTo(minSquare) < 0 || minSquare.equals(BigDecimal.ZERO)) {
                minSquare = rectSquare;
                System.arraycopy(endpoints, 0, minSquarePoints, 0, endpoints.length);
            }

            if (rectPerimeter.compareTo(minPerimeter) < 0 || minPerimeter.equals(BigDecimal.ZERO)) {
                minPerimeter = rectPerimeter;
                System.arraycopy(endpoints, 0, minPerimeterPoints, 0, endpoints.length);
            }

            recountEndpointAndAngle(endpoints, angles, indexOfMinAngle);
        } while ((endpoints[2] != tmpEndpointFirst) && (endpoints[0] != tmpEndpointSecond));

        System.arraycopy(minSquarePoints, 0, answerForSquare, 0, endpoints.length);
        System.arraycopy(minPerimeterPoints, 0, answerForPerimeter, 0, endpoints.length);
    }
}
