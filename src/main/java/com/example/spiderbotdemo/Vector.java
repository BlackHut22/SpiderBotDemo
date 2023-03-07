package com.example.spiderbotdemo;

import java.math.BigDecimal;
import java.math.MathContext;

public class Vector {
    double x;
    double y;
    double z;
    public Vector(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }


    public double getMagnitude(){
        return Math.sqrt(Math.pow(getX(),2) + Math.pow(getY(),2) + Math.pow(getZ(),2));
    }

    public Spherical toSpherical(){
        double theta = Math.toDegrees(Math.asin( getZ() / getMagnitude() )) ;
        double phi = Math.toDegrees(Math.atan2( getX() , getY() ));
        return new Spherical(theta, phi, getMagnitude());

    }

    public Vector getNegative(){
        return new Vector(-getX(), -getY(), -getZ());
    }

}
