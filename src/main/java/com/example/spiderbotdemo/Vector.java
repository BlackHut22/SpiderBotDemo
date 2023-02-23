package com.example.spiderbotdemo;

import javafx.geometry.Point3D;

public class Vector {
    double x;
    double y;
    double z;
    public Vector(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Vector(Point3D p){
        this.x = p.getX();
        this.y = p.getY();
        this.z = p.getZ();
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
        return  Math.sqrt(Math.pow(this.getX(),2) + Math.pow(this.getY(),2) + Math.pow(this.getZ(),2));
    }



    public String print(){
        return " x:" + Math.round(getX() * 100.0) / 100.0 + " y:" + Math.round(getY() * 100.0) / 100.0 + " z:" + Math.round(getZ() * 100.0) / 100.0;
    }

    public Spherical toSpherical(){
        double theta =  Math.toDegrees(Math.asin( this.getZ() / this.getMagnitude()));
        double phi =    Math.toDegrees(Math.atan2( this.getX() , this.getY()));
        return new Spherical(theta, phi, this.getMagnitude());

    }

    public Vector getNegative(){
        return new Vector(-this.getX(), -this.getY(), -this.getZ());
    }

    public Vector scale(double x){
        return new Vector(getX() * x, getY() * x, getZ() * x);
    }


    public Vector copy(){
        return new Vector(getX(), getY(), getZ());
    }


}
