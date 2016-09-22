package com.appmath.geometry;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class GeometryTask {

    private ArrayList<Point2D> points = null;
    private ArrayList answerForSquare = null;
    private ArrayList answerForPerimeter = null;

    public void setPoints(ArrayList<Point2D> points) {
        this.points = points;
    }

    public String getAnswerForSquare() {
        return answerForSquare.toString();
    }

    public String getAnswerForPerimeter() {
        return answerForPerimeter.toString();
    }

    public void solveTaskBySquare() {

    }

    public void solveTaskByPerimeter() {

    }
}
