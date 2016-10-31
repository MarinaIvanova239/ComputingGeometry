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

    public void solveTaskForSquare() {
        int[] endpoints = findEndpoints();
        float[] angles = findAngles(endpoints);
    }

    public void solveTaskForPerimeter() {
        //answerForPerimeter = points;
    }
}
