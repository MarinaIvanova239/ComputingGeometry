package com.appmath.custom;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class MyLine {

    // прямые вида ax + by + c = 0
    private BigInteger a = BigInteger.ZERO;
    private BigInteger b = BigInteger.ZERO;
    private BigInteger c = BigInteger.ZERO;

    public MyLine(BigInteger a, BigInteger b, BigInteger c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public void setA(BigInteger a) {
        this.a = a;
    }

    public void setB(BigInteger b) {
        this.b = b;
    }

    public void setC(BigInteger c) {
        this.c = c;
    }

    public BigInteger getA() {
        return a;
    }

    public BigInteger getB() {
        return b;
    }

    public BigInteger getC() {
        return c;
    }

    public static MyLine buildLineByTwoPoints(MyPoint2D p1, MyPoint2D p2) {
        BigInteger x1 = p1.getX(), x2 = p2.getX();
        BigInteger y1 = p1.getY(), y2 = p2.getY();
        BigInteger a = y2.subtract(y1);
        BigInteger b = x2.subtract(x1);
        BigInteger c = (y1.multiply(x2)).subtract(x1.multiply(y2));
        return new MyLine(a, b, c);
    }

    public static MyLine buildPerpendicularLineByOnePoint(MyLine l, MyPoint2D p) {
        BigInteger x = p.getX(), y = p.getY();
        BigInteger a = l.getA(), b = l.getB();
        BigInteger c = (a.multiply(y)).subtract(b.multiply(x));
        return new MyLine(b, a.negate(), c);
    }

    public static BigInteger countDistanceBetweenLineAndPoint(MyLine l, MyPoint2D p) {
        BigInteger x = p.getX(), y = p.getY();
        BigInteger a = l.getA(), b = l.getB(), c = l.getC();

        BigInteger numerator = a.multiply(x).add(b.multiply(y)).add(c);
        BigInteger denumerator = (a.pow(2)).add(b.pow(2));
        return numerator.pow(2).divide(denumerator);
    }
}
