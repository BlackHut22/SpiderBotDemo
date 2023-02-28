package com.example.spiderbotdemo;

import java.math.BigDecimal;

public class Spherical {
    BigDecimal length;
    BigDecimal theta;
    BigDecimal phi;

    public Spherical(BigDecimal theta, BigDecimal phi, BigDecimal length){
        this.theta = theta;
        this.phi = phi;
        this.length = length;
    }

    public Spherical setTheta(BigDecimal theta){
        return new Spherical(theta, getPhi(), getLength());
    }
    public Spherical setPhi(BigDecimal phi){
        return new Spherical(getTheta(), phi, getLength());
    }
    public Spherical setLength(BigDecimal length) {
        return new Spherical(getTheta(), getPhi(), length);
    }
    public Spherical addTheta(BigDecimal theta){
        return new Spherical(getTheta().add(theta), getPhi(), getLength());
    }
    public Spherical addPhi(BigDecimal phi){
        return new Spherical(getTheta(), getPhi().add(phi), getLength());
    }
    public Spherical addLength(BigDecimal length){return new Spherical(getTheta(),getPhi(),getLength().add(length));}
    public BigDecimal getLength() {
        return length;
    }
    public BigDecimal getPhi() {
        return phi;
    }
    public BigDecimal getTheta() {
        return theta;
    }
    public Vector toVector(){
        double t = Math.toRadians(getTheta().doubleValue()) ;
        double p = Math.toRadians(getPhi().doubleValue()) ;
        BigDecimal cosT = new BigDecimal(Double.toString(Math.cos(t)));
        BigDecimal x = getLength().multiply(cosT).multiply(new BigDecimal(Double.toString(Math.sin(p))));
        BigDecimal y = getLength().multiply(cosT).multiply(new BigDecimal(Double.toString(Math.cos(p))));
        BigDecimal z = getLength().multiply(new BigDecimal(Double.toString(Math.sin(t))));
        return new Vector(x,y,z);
    }
    public Point3DBIGD toPoint3DBIGD(){
        double t = Math.toRadians(getTheta().doubleValue()) ;
        double p = Math.toRadians(getPhi().doubleValue()) ;
        BigDecimal cosT = new BigDecimal(Double.toString(Math.cos(t)));
        BigDecimal x = getLength().multiply(cosT).multiply(new BigDecimal(Double.toString(Math.sin(p))));
        BigDecimal y = getLength().multiply(cosT).multiply(new BigDecimal(Double.toString(Math.cos(p))));
        BigDecimal z = getLength().multiply(new BigDecimal(Double.toString(Math.sin(t))));
        return new Point3DBIGD(x,y,z);
    }
    public Spherical getNegative(){
        return new Spherical(getTheta().subtract(new BigDecimal("180")), getPhi(), getLength());
    }

}