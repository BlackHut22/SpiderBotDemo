package com.example.spiderbotdemo;

import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import java.util.ArrayList;
import java.util.HashMap;

public class Spider {
    HashMap<String, Leg> legs = new HashMap<>();
    int percentage = 0;
    Point3D middlePoint;
    public Spider(Point3D middlePoint){
        this.middlePoint = middlePoint;

        Leg R1 = new Leg(calcLegStartP(22.5,1000),22.5);
        Leg R2 = new Leg(calcLegStartP(67.5,1000),67.5);
        Leg R3 = new Leg(calcLegStartP(112.5,1000),112.5);
        Leg R4 = new Leg(calcLegStartP(157.5,1000),157.5);
        Leg L1 = new Leg(calcLegStartP(-22.5,1000),-22.5);
        Leg L2 = new Leg(calcLegStartP(-67.5,1000),-67.5);
        Leg L3 = new Leg(calcLegStartP(-112.5,1000),-112.5);
        Leg L4 = new Leg(calcLegStartP(-157.5,1000),-157.5);

        this.legs.put("R1", R1);
        this.legs.put("R2", R2);
        this.legs.put("R3", R3);
        this.legs.put("R4", R4);
        this.legs.put("L4", L4);
        this.legs.put("L3", L3);
        this.legs.put("L2", L2);
        this.legs.put("L1", L1);
    }
    private Point3D calcLegStartP(double phi, int distance){
        Point3D v = new Spherical(0,phi,distance).toPoint3D();
        return getMiddlePoint().add(v);
    }
    public Point3D getMiddlePoint() {
        return middlePoint;
    }
    public int getPercentage() {
        return percentage;
    }
    public int getPercentage(int add) {
        return (percentage + add) % 100;
    }
    public void incrementPercentage(){
        percentage += 1;
        if (getPercentage() == 100){
            percentage = 0;
        }
    }
    public void increaseHeight(String leg, int x){
        this.legs.put(leg, this.legs.get(leg).increaseHeight(x));
    }
    public Spider increaseHeight(int x){
        getLegs().forEach((name, leg) -> increaseHeight(name, x));
        return this;
    }
    public Spider moveStraight(String leg, Spherical s, int percentage){
        this.legs.put(leg, this.legs.get(leg).moveStraight(s, percentage));
        return this;
    }

    public Spider moveStraight(Spherical s){
        incrementPercentage();
        return this
                .moveStraight("R1", s, getPercentage(0))
                .moveStraight("R2", s, getPercentage(50))
                .moveStraight("R3", s, getPercentage(25))
                .moveStraight("R4", s, getPercentage(75))
                .moveStraight("L4", s, getPercentage(0))
                .moveStraight("L3", s, getPercentage(50))
                .moveStraight("L2", s, getPercentage(25))
                .moveStraight("L1", s, getPercentage(75));

    }
    public Spider moveDown(String leg){
        this.legs.put(leg, this.legs.get(leg).moveDown());
        return this;
    }
    public Spider moveDown(){
        incrementPercentage();
        return this
                .moveDown("R1")
                .moveDown("R2")
                .moveDown("R3")
                .moveDown("R4")
                .moveDown("L4")
                .moveDown("L3")
                .moveDown("L2")
                .moveDown("L1");
    }
    public Spider rotateBody(String leg, int direction, int percentage){
        //this.legs.put(leg, this.legs.get(leg).rotateBody(getMiddlePoint(), direction ,percentage));
        return this;
    }
    public Spider rotateBody(int direction){
        incrementPercentage();
        return this
                .rotateBody("R1", direction, getPercentage(75))
                .rotateBody("R2", direction, getPercentage(25))
                .rotateBody("R3", direction, getPercentage(75))
                .rotateBody("R4", direction, getPercentage(25))
                .rotateBody("L4", direction, getPercentage(75))
                .rotateBody("L3", direction, getPercentage(25))
                .rotateBody("L2", direction, getPercentage(75))
                .rotateBody("L1", direction, getPercentage(25));
    }





    public HashMap<String, Leg> getLegs() {
        return this.legs;
    }




    public ArrayList<Node> getDisplayNodes(){
        ArrayList<Node> returnable = new ArrayList<>();
        this.getLegs().forEach((name, leg) -> returnable.addAll(leg.getDisplayNodes()));
        return returnable;
    }


}
