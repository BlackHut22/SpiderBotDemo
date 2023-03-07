package com.example.spiderbotdemo;

import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;


import java.util.ArrayList;
import java.util.List;

public class Leg {
    Legsegment[] segments = new Legsegment[2];
    Point3D startingPoint;
    Point3D groundPoint;
    Spherical startingDirection;
    double segmentLength = 1000;
    double maxDistance = 500;
    boolean direction;


    ArrayList<Node> toDraw = new ArrayList<>();
    public Leg(Point3D startingPoint, double startingDirectionPhi, boolean direction){
        this.direction = direction;
        this.startingPoint = startingPoint;
        this.startingDirection = new Spherical(0,startingDirectionPhi,getSegmentLength());
        this.segments[0] = new Legsegment(getStartingPoint(), getStartingDirection(), getSegmentLength());
        this.segments[1] = new Legsegment(getSegments()[0].getEndingPoint(), getSegments()[0].getSpherical().getNegative().addTheta(180) ,getSegmentLength());
        this.groundPoint = getStartingPoint().add(getStartingDirection().toVector().getX(),getStartingDirection().toVector().getY(),0);
        move(getGroundPoint());
    }
    public double getMaxDistance() {
        return maxDistance;
    }
    public boolean getDirection() {
        return direction;
    }
    public Point3D getGroundPoint() {
        return groundPoint;
    }
    public Point3D getStartingPoint() {
        return startingPoint;
    }
    public Point3D getEndingPoint(){return getSegments()[1].getEndingPoint();}
    public Point3D getEndingPointOnGround(){return new Point3D(getEndingPoint().getX(), getEndingPoint().getY(), 0);}
    public Spherical getStartingDirection() {
        return startingDirection;
    }
    private double getSegmentLength() {
        return segmentLength;
    }
    private Legsegment[] getSegments() {
        return segments.clone();
    }
    private void shoulderPhi(double degree){
        this.segments[0] = getSegments()[0].setSphericalPhi(degree);
        this.segments[1] = getSegments()[1].setSphericalPhi(degree).updateStartingPoint(getSegments()[0].getEndingPoint());
    }
    private void shoulderTheta(double degree){
        this.segments[0] = new Legsegment(getStartingPoint(), getSegments()[0].getSpherical().setTheta(degree), getSegmentLength());
    }
    private void elbowTheta(double degree){
        this.segments[1] = new Legsegment(getSegments()[0].getEndingPoint(), getSegments()[0].getSpherical().getNegative().addTheta(degree) ,getSegmentLength());
    }
    public Leg increaseHeight(double x){
        this.startingPoint = getStartingPoint().add(0,0,x);
        return this;
    }

    public ArrayList<Node> getToDraw() {
        return toDraw;
    }

    public void move(Point3D point){
        if (getDirection()) {
            double zUnitValue = Math.acos(point.distance(getGroundPoint()) / getMaxDistance());
            double zValue = zUnitValue * 100;
            point = new Point3D(point.getX(), point.getY(), zValue);
        }
        shoulderPhi(new Vector(point.getX() - getStartingPoint().getX(),point.getY() - getStartingPoint().getY(),0).toSpherical().getPhi());
        double newVertexAngle = 2 * Math.toDegrees( Math.asin((point.distance(getStartingPoint()) / 2) / getSegmentLength())) ;
        double extraThetaAngleStartingPointToZ = new Vector(point.getX() - getStartingPoint().getX(), point.getY() - getStartingPoint().getY(), point.getZ() - getStartingPoint().getZ()).toSpherical().getTheta();
        shoulderTheta(((180 - newVertexAngle) / 2) + extraThetaAngleStartingPointToZ);
        elbowTheta(newVertexAngle);
    }

    public Leg moveStraight(Spherical s){
        Point3D toPoint;
        if (getDirection()) {
            double a =  (((getEndingPointOnGround().getX() - getGroundPoint().getX()) * s.toVector().getX()) + ((getEndingPointOnGround().getY() - getGroundPoint().getY()) * s.toVector().getY())) / (Math.pow(s.toPoint3D().getX(),2) + Math.pow(s.toPoint3D().getY(),2));
            double x = getGroundPoint().getX() + (a * s.toPoint3D().getX());
            double y = getGroundPoint().getY() + (a * s.toPoint3D().getY());
            Point3D projectedPoint = new Point3D(x,y,0);
            double distance = getEndingPointOnGround().distance(projectedPoint);
            double newDistance = Math.max(0, distance - s.getLength() );
            double Length = Math.sqrt(Math.pow(getMaxDistance(),2) - Math.pow(distance,2));
            double newLength = Math.sqrt(Math.pow(getMaxDistance(),2) - Math.pow(newDistance,2));
            double addSLength = newLength - Length;

            Point3D newPoint = projectedPoint.interpolate(getEndingPointOnGround(),(distance == 0)? 0 : newDistance / distance);

            toPoint = newPoint.add(s.addLength(addSLength).toPoint3D());

        }else{
            toPoint = getEndingPointOnGround().add(s.getNegative().toPoint3D());
        }

        move(toPoint);



        return this;
    }

    public Leg rotateBody(Point3D middlePoint, int direction) {
        return this;
    }

    public Boolean checkDirectionChange(){
        return getGroundPoint().distance(getEndingPointOnGround()) > getMaxDistance();
    }

    public Leg reverseDirection(){
        this.direction = !getDirection();
        return this;
    }
    public Leg moveDown(){
        move(getEndingPoint().subtract(0,0, Math.min(10,getEndingPoint().getZ())));
        return this;
    }
    //public Leg rotateBody(Point3D middlePoint, int direction, int percentage){}




    public ArrayList<Node> getDisplayNodes(){
        ArrayList<Node> returnable = new ArrayList<>();
        for (Legsegment segment: this.getSegments()){
            returnable.addAll(segment.getDisplayNodes());
        }

        Point3D p4 = getStartingPoint().add(getStartingDirection().toVector().getX(),getStartingDirection().toVector().getY(),0);
        Cylinder sp4 = new Cylinder();
        sp4.setRadius(500);
        sp4.setHeight(1);
        sp4.translateXProperty().set(p4.getX());
        sp4.translateYProperty().set(p4.getY());
        sp4.translateZProperty().set(0);
        PhongMaterial mat4 = new PhongMaterial();
        sp4.setMaterial(mat4);
        mat4.setDiffuseColor(Color.RED);
        Rotate boxRotX = new Rotate(90, Rotate.X_AXIS);
        sp4.getTransforms().addAll(boxRotX);

        Sphere sp3 = new Sphere();
        sp3.setRadius(50);
        sp3.translateXProperty().set(getEndingPoint().getX());
        sp3.translateYProperty().set(getEndingPoint().getY());
        sp3.translateZProperty().set(getEndingPoint().getZ());
        PhongMaterial mat3 = new PhongMaterial();
        sp3.setMaterial(mat3);
        mat3.setDiffuseColor(Color.BLUE);
        if (getEndingPoint().getZ() > 0.1){
            sp3.setRadius(0);
        }

        returnable.addAll(new ArrayList<>(List.of(sp3,sp4)));
        return returnable;
    }

}