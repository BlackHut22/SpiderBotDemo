package com.example.spiderbotdemo;

import javafx.scene.Node;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

public class Spider {
    HashMap<String, Leg> legs = new HashMap<>();
    Point3DBIGD middlePoint;
    public Spider(Point3DBIGD middlePoint){
        this.middlePoint = middlePoint;

        Leg R1 = new Leg(calcLegStartP(  new BigDecimal(Double.toString(22.5)),new BigDecimal("1000")),  new BigDecimal(Double.toString(22.5)), true);
        Leg R2 = new Leg(calcLegStartP(  new BigDecimal(Double.toString(67.5)),new BigDecimal("1000")),  new BigDecimal(Double.toString(67.5)),false);
        Leg R3 = new Leg(calcLegStartP( new BigDecimal(Double.toString(112.5)),new BigDecimal("1000")), new BigDecimal(Double.toString(112.5)), true);
        Leg R4 = new Leg(calcLegStartP( new BigDecimal(Double.toString(157.5)),new BigDecimal("1000")), new BigDecimal(Double.toString(157.5)),false);
        Leg L1 = new Leg(calcLegStartP( new BigDecimal(Double.toString(-22.5)),new BigDecimal("1000")), new BigDecimal(Double.toString(-22.5)),false);
        Leg L2 = new Leg(calcLegStartP( new BigDecimal(Double.toString(-67.5)),new BigDecimal("1000")), new BigDecimal(Double.toString(-67.5)), true);
        Leg L3 = new Leg(calcLegStartP(new BigDecimal(Double.toString(-112.5)),new BigDecimal("1000")),new BigDecimal(Double.toString(-112.5)),false);
        Leg L4 = new Leg(calcLegStartP(new BigDecimal(Double.toString(-157.5)),new BigDecimal("1000")),new BigDecimal(Double.toString(-157.5)), true);

        this.legs.put("R1", R1);
        this.legs.put("R2", R2);
        this.legs.put("R3", R3);
        this.legs.put("R4", R4);
        this.legs.put("L4", L4);
        this.legs.put("L3", L3);
        this.legs.put("L2", L2);
        this.legs.put("L1", L1);
    }
    private Point3DBIGD calcLegStartP(BigDecimal phi, BigDecimal distance){
        Point3DBIGD v = new Spherical(new BigDecimal("0"),phi,distance).toPoint3DBIGD();
        return getMiddlePoint().add(v);
    }
    public Point3DBIGD getMiddlePoint() {
        return middlePoint;
    }
    public void increaseHeight(String leg, BigDecimal x){
        this.legs.put(leg, this.legs.get(leg).increaseHeight(x));
    }
    public Spider increaseHeight(BigDecimal x){
        getLegs().forEach((name, leg) -> increaseHeight(name, x));
        return this;
    }
    public Spider moveStraight(String leg, Spherical s){
        this.legs.put(leg, this.legs.get(leg).moveStraight(s));
        return this;
    }

    public Spider moveStraight(Spherical s){
        return this
                //.moveStraight("R1", s)
                //.moveStraight("R2", s)
                //.moveStraight("R3", s)
                .moveStraight("R4", s)
                .moveStraight("L4", s);
                //.moveStraight("L3", s)
                //.moveStraight("L2", s)
                //.moveStraight("L1", s);

    }
    public Spider moveDown(String leg){
        this.legs.put(leg, this.legs.get(leg).moveDown());
        return this;
    }
    public Spider moveDown(){
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
    public Spider rotateBody(String leg, int direction){
        //this.legs.put(leg, this.legs.get(leg).rotateBody(getMiddlePoint(), direction));
        return this;
    }
    public Spider rotateBody(int direction){
        return this
                .rotateBody("R1", direction)
                .rotateBody("R2", direction)
                .rotateBody("R3", direction)
                .rotateBody("R4", direction)
                .rotateBody("L4", direction)
                .rotateBody("L3", direction)
                .rotateBody("L2", direction)
                .rotateBody("L1", direction);
    }





    public HashMap<String, Leg> getLegs() {
        return this.legs;
    }




    public ArrayList<Node> getDisplayNodes(){
        ArrayList<Node> returnable = new ArrayList<>();
        this.getLegs().forEach((name, leg) -> returnable.addAll(leg.getDisplayNodes()));
        this.getLegs().forEach((name, leg) -> returnable.addAll(leg.getToDraw()));
        return returnable;
    }


}
