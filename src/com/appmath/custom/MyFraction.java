package com.appmath.custom;

import java.math.BigInteger;

public class MyFraction {

    private BigInteger num = BigInteger.ZERO;
    private BigInteger denum = BigInteger.ZERO;

    public MyFraction(BigInteger num, BigInteger denum) {
        this.num = num;
        this.denum = denum;
    }

    public void setNum(BigInteger num) {
        this.num = num;
    }

    public void setDenum(BigInteger denum) {
        this.denum = denum;
    }

    public BigInteger getNum() {
        return num;
    }

    public BigInteger getDenum() {
        return denum;
    }

}
