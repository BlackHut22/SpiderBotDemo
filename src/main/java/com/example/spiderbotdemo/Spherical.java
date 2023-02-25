package com.example.spiderbotdemo;
import javafx.geometry.Point3D;

public class Spherical {
    double length;
    double theta;
    double phi;
    public Spherical(double theta, double phi, double length){
        this.theta = theta;
        this.phi = phi;
        this.length = length;
    }
    public Spherical setTheta(double theta){
        return new Spherical(theta, getPhi(), getLength());
    }
    public Spherical setPhi(double phi){
        return new Spherical(getTheta(), phi, getLength());
    }
    public Spherical setLength(double length) {
        return new Spherical(getTheta(), getPhi(), length);
    }
    public Spherical addLength(double length){return new Spherical(getTheta(),getPhi(),getLength()+length);}
    public Spherical addTheta(double theta){
        return new Spherical(getTheta() + theta, getPhi(), getLength());
    }
    public Spherical addPhi(double phi){
        return new Spherical(getTheta(), getPhi() + phi, getLength());
    }
    public double getLength() {
        return length;
    }
    public double getPhi() {
        return phi;
    }
    public double getTheta() {
        return theta;
    }
    public Vector toVector(){
        double t = Math.toRadians(getTheta()) ;
        double p = Math.toRadians(getPhi()) ;
        double x = getLength() * Math.cos(t) * Math.sin(p);
        double y = getLength() * Math.cos(t) * Math.cos(p);
        double z = getLength() * Math.sin(t);
        return new Vector(x,y,z);
    }
    public Point3D toPoint3D(){
        double t = Math.toRadians(getTheta()) ;
        double p = Math.toRadians(getPhi()) ;
        double x = getLength() * Math.cos(t) * Math.sin(p);
        double y = getLength() * Math.cos(t) * Math.cos(p);
        double z = getLength() * Math.sin(t);
        return new Point3D(x,y,z);
    }
    public Spherical getNegative(){
        return new Spherical(getTheta()-180, getPhi(), getLength());
    }
    public String print(){
        return " Theta:" + Math.round(getTheta() * 100.0) / 100.0 + " Phi:" + Math.round(getPhi() * 100.0) / 100.0;
    }
}