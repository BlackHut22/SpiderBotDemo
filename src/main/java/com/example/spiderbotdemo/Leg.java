package com.example.spiderbotdemo;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;

public class Leg {
    Legsegment[] segments = new Legsegment[2];
    Point3DBIGD startingPoint;
    Point3DBIGD groundPoint;
    Spherical startingDirection;
    BigDecimal segmentLength = new BigDecimal("1000");
    BigDecimal maxDistance = new BigDecimal("500");
    boolean direction;


    ArrayList<Node> toDraw = new ArrayList<>();
    public Leg(Point3DBIGD startingPoint, BigDecimal startingDirectionPhi, boolean direction){
        this.direction = direction;
        this.startingPoint = startingPoint;
        this.startingDirection = new Spherical(new BigDecimal("0"),startingDirectionPhi,getSegmentLength());
        this.segments[0] = new Legsegment(getStartingPoint(), getStartingDirection(), getSegmentLength());
        this.segments[1] = new Legsegment(getSegments()[0].getEndingPoint(), getSegments()[0].getSpherical().getNegative().addTheta(new BigDecimal("180")) ,getSegmentLength());
        this.groundPoint = getStartingPoint().add(getStartingDirection().toVector().getX(),getStartingDirection().toVector().getY(),new BigDecimal("0"));
        move(getGroundPoint());
    }
    public BigDecimal getMaxDistance() {
        return maxDistance;
    }
    public boolean getDirection() {
        return direction;
    }
    public Point3DBIGD getGroundPoint() {
        return groundPoint;
    }
    public Point3DBIGD getStartingPoint() {
        return startingPoint;
    }
    public Point3DBIGD getEndingPoint(){return getSegments()[1].getEndingPoint();}
    public Point3DBIGD getEndingPointOnGround(){return new Point3DBIGD(getEndingPoint().getX(), getEndingPoint().getY(), new BigDecimal("0"));}
    public Spherical getStartingDirection() {
        return startingDirection;
    }
    private BigDecimal getSegmentLength() {
        return segmentLength;
    }
    private Legsegment[] getSegments() {
        return segments.clone();
    }
    private void shoulderPhi(BigDecimal degree){
        this.segments[0] = getSegments()[0].setSphericalPhi(degree);
        this.segments[1] = getSegments()[1].setSphericalPhi(degree).updateStartingPoint(getSegments()[0].getEndingPoint());
    }
    private void shoulderTheta(BigDecimal degree){
        this.segments[0] = new Legsegment(getStartingPoint(), getSegments()[0].getSpherical().setTheta(degree), getSegmentLength());
    }
    private void elbowTheta(BigDecimal degree){
        this.segments[1] = new Legsegment(getSegments()[0].getEndingPoint(), getSegments()[0].getSpherical().getNegative().addTheta(degree) ,getSegmentLength());
    }
    public Leg increaseHeight(BigDecimal x){
        this.startingPoint = getStartingPoint().add(new BigDecimal("0"),new BigDecimal("0"),x);
        return this;
    }

    public ArrayList<Node> getToDraw() {
        return toDraw;
    }

    public void move(Point3DBIGD point){
        if (getDirection()) {
            double zUnitValue = Math.acos(point.distance(getGroundPoint()).divide(getMaxDistance(), MathContext.DECIMAL32).doubleValue());
            BigDecimal zValue = BigDecimal.valueOf((Double.isNaN(zUnitValue)) ? 0 : zUnitValue).multiply(new BigDecimal("100"));
            point = new Point3DBIGD(point.getX(), point.getY(), zValue);
        }
        shoulderPhi(new Vector(point.getX().subtract(getStartingPoint().getX()),point.getY().subtract(getStartingPoint().getY()),new BigDecimal("0")).toSpherical().getPhi());
        BigDecimal newVertexAngle = new BigDecimal("2").multiply(new BigDecimal(Double.toString(Math.toDegrees( Math.asin((point.distance(getStartingPoint()).divide(new BigDecimal("2"),MathContext.DECIMAL32)).divide(getSegmentLength(),MathContext.DECIMAL32).doubleValue()))))) ;
        BigDecimal extraThetaAngleStartingPointToZ = new Vector(point.getX().subtract(getStartingPoint().getX()), point.getY().subtract(getStartingPoint().getY()), point.getZ().subtract(getStartingPoint().getZ())).toSpherical().getTheta();
        shoulderTheta(((new BigDecimal("180").subtract(newVertexAngle)).divide(new BigDecimal("2"),MathContext.DECIMAL32)).add(extraThetaAngleStartingPointToZ));
        elbowTheta(newVertexAngle);
    }

    public Leg moveStraight(Spherical s){
        Point3DBIGD toPoint;
        if (getDirection()) {
            BigDecimal a = ((getEndingPointOnGround().getX().subtract(getGroundPoint().getX())).multiply(s.toVector().getX()).add((getEndingPointOnGround().getY().subtract(getGroundPoint().getY())).multiply(s.toVector().getY()))).divide((s.toVector().getX().pow(2).add(s.toVector().getY().pow(2))),MathContext.DECIMAL32);
            BigDecimal x = getGroundPoint().getX().add(a.multiply(s.toVector().getX()));
            BigDecimal y = getGroundPoint().getY().add(a.multiply(s.toVector().getY()));
            Point3DBIGD projectedPoint = new Point3DBIGD(x,y,new BigDecimal("0"));
            BigDecimal distance = new BigDecimal(Double.toString(Math.max(0, getEndingPointOnGround().distance(projectedPoint).doubleValue())));
            BigDecimal newDistance = distance.subtract(new BigDecimal("5"));
            BigDecimal Length = ((getMaxDistance().pow(2)).subtract(distance.pow(2))).sqrt(MathContext.DECIMAL32);
            BigDecimal newLength = ((getMaxDistance().pow(2)).subtract(newDistance.pow(2))).sqrt(MathContext.DECIMAL32);
            BigDecimal addSLength = newLength.subtract(Length);

            Point3DBIGD newPoint = projectedPoint.interpolate(getEndingPointOnGround(),( distance.compareTo(new BigDecimal("0")) == 0)? new BigDecimal("0") : newDistance.divide(distance, MathContext.DECIMAL32));

            toPoint = newPoint.add(s.addLength(addSLength).toPoint3DBIGD());

        }else{
            toPoint = getEndingPointOnGround().add(s.getNegative().toPoint3DBIGD());
        }

        move(toPoint);

        System.out.println(Math.abs(getGroundPoint().distance(toPoint).subtract(getMaxDistance()).doubleValue()));
        if (Math.abs(getGroundPoint().distance(toPoint).subtract(getMaxDistance()).doubleValue()) <= 5)
            reverseDirection();

        return this;
    }



    private void reverseDirection(){
        this.direction = !getDirection();
    }
    public Leg moveDown(){
        move(getEndingPoint().subtract(new BigDecimal("0"),new BigDecimal("0"),(getEndingPoint().getZ().compareTo(new BigDecimal("10")) >= 0)? new BigDecimal("10") : getEndingPoint().getZ()));
        return this;
    }
    //public Leg rotateBody(Point3D middlePoint, int direction, int percentage){}




    public ArrayList<Node> getDisplayNodes(){
        ArrayList<Node> returnable = new ArrayList<>();
        for (Legsegment segment: this.getSegments()){
            returnable.addAll(segment.getDisplayNodes());
        }

        Point3DBIGD p4 = getStartingPoint().add(getStartingDirection().toVector().getX(),getStartingDirection().toVector().getY(),new BigDecimal(0));
        Cylinder sp4 = new Cylinder();
        sp4.setRadius(500);
        sp4.setHeight(1);
        sp4.translateXProperty().set(p4.getX().doubleValue());
        sp4.translateYProperty().set(p4.getY().doubleValue());
        sp4.translateZProperty().set(0);
        PhongMaterial mat4 = new PhongMaterial();
        sp4.setMaterial(mat4);
        mat4.setDiffuseColor(Color.RED);
        Rotate boxRotX = new Rotate(90, Rotate.X_AXIS);
        sp4.getTransforms().addAll(boxRotX);

        Sphere sp3 = new Sphere();
        sp3.setRadius(50);
        sp3.translateXProperty().set(getEndingPoint().getX().doubleValue());
        sp3.translateYProperty().set(getEndingPoint().getY().doubleValue());
        sp3.translateZProperty().set(getEndingPoint().getZ().doubleValue());
        PhongMaterial mat3 = new PhongMaterial();
        sp3.setMaterial(mat3);
        mat3.setDiffuseColor(Color.BLUE);
        if (getEndingPoint().getZ().doubleValue() > 0.1){
            sp3.setRadius(0);
        }

        returnable.addAll(new ArrayList<>(List.of(sp3,sp4)));
        return returnable;
    }
}