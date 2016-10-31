package com.appmath.custom;

import java.math.BigInteger;

public class MyPoint2D {

    private BigInteger x = BigInteger.ZERO;
    private BigInteger y = BigInteger.ZERO;

    public MyPoint2D(BigInteger x, BigInteger y) {
        this.x = x;
        this.y = y;
    }

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

    public static BigInteger sqrt(BigInteger x) {
        BigInteger div = BigInteger.ZERO.setBit(x.bitLength()/2);
        BigInteger div2 = div;
        for(;;) {
            BigInteger y = div.add(x.divide(div)).shiftRight(1);
            if (y.equals(div) || y.equals(div2))
                return y;
            div2 = div;
            div = y;
        }
    }

    public static BigInteger countDistance(MyPoint2D p1, MyPoint2D p2) {
        BigInteger x1 = p1.getX(), x2 = p2.getX();
        BigInteger y1 = p1.getY(), y2 = p2.getY();
        BigInteger distance = (x1.min(x2)).pow(2).add((y1.min(y2)).pow(2));
        return sqrt(distance);
    }
}
