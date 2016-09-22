package com.appmath.custom;

import java.math.BigInteger;

public class MyPoint2D {

    private BigInteger x = BigInteger.ZERO;
    private BigInteger y = BigInteger.ZERO;

    public void setX(BigInteger x) {
        this.x = x;
    }

    public void setY(BigInteger y) {
        this.y = y;
    }

    public BigInteger getX() {
        return x;
    }

    public BigInteger getY() {
        return y;
    }

    public static BigInteger countDistance(MyPoint2D fisrt, MyPoint2D second) {
        BigInteger distance = BigInteger.ZERO;
        return distance;
    }
}
