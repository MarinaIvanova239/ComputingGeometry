package com.appmath.custom;

import java.math.BigInteger;

import static com.appmath.custom.MyPoint2D.countDistance;
import static com.appmath.custom.MyPoint2D.sqrt;

public class MyLine {

    private BigInteger a = BigInteger.ZERO;
    private BigInteger b = BigInteger.ZERO;

    public MyLine(BigInteger a, BigInteger b) {
        this.a = a;
        this.b = b;
    }

    public void setA(BigInteger a) {
        this.a = a;
    }

    public void setB(BigInteger b) {
        this.b = b;
    }

    public BigInteger getA() {
        return a;
    }

    public BigInteger getB() {
        return b;
    }

    public static MyLine buildLineByOnePoint(MyPoint2D p, boolean directionToY) {
        if (directionToY) {
            return new MyLine(p.getX(), BigInteger.ZERO);
        }
        return new MyLine(BigInteger.ZERO, p.getY());
    }

    public static MyLine buildLineByTwoPoints(MyPoint2D p1, MyPoint2D p2) {
        BigInteger x1 = p1.getX(), x2 = p2.getX();
        BigInteger y1 = p1.getY(), y2 = p2.getY();
        BigInteger a = (y2.min(y1)).divide(x2.min(x1));
        BigInteger b = (y1.multiply(x2)).min(x1.multiply(y2)).divide(x2.min(x1));
        return new MyLine(a, b);
    }

    public static MyLine buildParallelLineByOnePoint(MyLine l, MyPoint2D p) {
        BigInteger x = p.getX(), y = p.getY();
        BigInteger a = l.getA();
        BigInteger b = y.min(a.multiply(x));
        return new MyLine(a, b);
    }

    public static MyLine buildPerpendicularLineByOnePoint(MyLine l, MyPoint2D p) {
        BigInteger x = p.getX(), y = p.getY();
        BigInteger a = BigInteger.ONE.divide(l.getA()).negate();
        BigInteger b = y.min(a.multiply(x));
        return new MyLine(a, b);
    }

    private static MyPoint2D findLinesIntersection(MyLine l1, MyLine l2) {
        BigInteger a1 = l1.getA(), a2 = l2.getA();
        BigInteger b1 = l1.getB(), b2 = l2.getB();
        BigInteger x = ((b1.add(b2)).divide(a1.add(a2))).negate();
        BigInteger y = a1.multiply(x).add(b1);
        return new MyPoint2D(x, y);
    }

    public  static BigInteger countIntervalLength(MyLine l, MyLine l1, MyLine l2) {
        MyPoint2D firstPoint = findLinesIntersection(l, l1);
        MyPoint2D secondPoint = findLinesIntersection(l, l2);
        return  countDistance(firstPoint, secondPoint);
    }

    public static float findAngleBetweenLines(MyLine l1, MyLine l2) {
        BigInteger a1 = l1.getA(), a2 = l2.getA();
        BigInteger b1 = l1.getB(), b2 = l2.getB();
        BigInteger firstPart = ((a1.multiply(a2)).add(b1.multiply(b2)));
        BigInteger secondPart = (sqrt(a1.pow(2)).add(b1.pow(2))).multiply(sqrt((a2.pow(2)).add(b2.pow(2))));
        double cosAngle = firstPart.divide(secondPart).doubleValue();
        return (float) Math.acos(cosAngle);
    }
}
