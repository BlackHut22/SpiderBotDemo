package com.example.spiderbotdemo;

import java.math.BigDecimal;
import java.math.MathContext;

public class Vector {
    BigDecimal x;
    BigDecimal y;
    BigDecimal z;
    public Vector(BigDecimal x, BigDecimal y, BigDecimal z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public BigDecimal getX() {
        return x;
    }

    public BigDecimal getY() {
        return y;
    }

    public BigDecimal getZ() {
        return z;
    }


    public BigDecimal getMagnitude(){
        return  getX().pow(2).add(getY().pow(2)).add(getZ().pow(2)).sqrt(MathContext.DECIMAL64);
    }

    public Spherical toSpherical(){
        BigDecimal theta = new BigDecimal(Double.toString(Math.toDegrees(Math.asin( getZ().divide(getMagnitude(),MathContext.DECIMAL32).doubleValue())))) ;
        BigDecimal phi = new BigDecimal(Double.toString(Math.toDegrees(Math.atan2( getX().doubleValue() , getY().doubleValue()))));
        return new Spherical(theta, phi, getMagnitude());

    }

    public Vector getNegative(){
        return new Vector(getX().multiply(new BigDecimal(Integer.toString(-1))), getY().multiply(new BigDecimal(Integer.toString(-1))), getZ().multiply(new BigDecimal(Integer.toString(-1))));
    }

}
