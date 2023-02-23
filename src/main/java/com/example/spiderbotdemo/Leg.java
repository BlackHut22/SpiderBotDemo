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
    int segmentLength = 1000;
    public Leg(Point3D startingPoint, double startingDirectionPhi){
        this.startingPoint = startingPoint;
        this.startingDirection = new Spherical(0,startingDirectionPhi,getSegmentLength());
        this.segments[0] = new Legsegment(getStartingPoint(), getStartingDirection(), getSegmentLength());
        this.segments[1] = new Legsegment(getSegments()[0].getEndingPoint(), getSegments()[0].getSpherical().getNegative().addTheta(180) ,getSegmentLength());
        this.groundPoint = getStartingPoint().add(getStartingDirection().toVector().getX(),getStartingDirection().toVector().getY(),0);
        move(getGroundPoint());
    }
    public Point3D getGroundPoint() {
        return groundPoint;
    }
    public Point3D getStartingPoint() {
        return startingPoint;
    }
    public Point3D getEndingPoint(){return getSegments()[1].getEndingPoint();}
    public Spherical getStartingDirection() {
        return startingDirection;
    }
    private int getSegmentLength() {
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
    public Leg increaseHeight(int x){
        this.startingPoint = getStartingPoint().add(0,0,x);
        return this;
    }



    public void move(Point3D point){
        shoulderPhi(new Vector(point.getX()-getStartingPoint().getX(),point.getY()-getStartingPoint().getY(),0).toSpherical().getPhi());
        double newVertexAngle = 2 *Math.toDegrees( Math.asin((point.distance(getStartingPoint())/2) / getSegmentLength()));
        double extraThetaAngleStartingpointToZ = new Vector(point.getX()-getStartingPoint().getX(), point.getY()-getStartingPoint().getY(), point.getZ()-getStartingPoint().getZ()).toSpherical().getTheta();
        shoulderTheta(((180-newVertexAngle)/2) + extraThetaAngleStartingpointToZ  );
        elbowTheta(newVertexAngle);
    }

    public Leg moveStraight(Spherical s, double percentage){
        double zValue = Math.sin(Math.max(0, ((percentage - 75.0) / (100.0 - 75.0)) * Math.PI)) * 100.0;
        Point3D fromGroundPoint = getGroundPoint().add(s.toPoint3D());
        Point3D toGroundPoint = getGroundPoint().add(s.getNegative().toPoint3D());
        if (percentage <= 75.0){
            move(fromGroundPoint.interpolate(toGroundPoint, (percentage / 75.0) ));
        }else {
            move(toGroundPoint.add(0,0,zValue).interpolate(fromGroundPoint.add(0,0,zValue), ((percentage - 75.0) / (100.0 - 75.0) )              )  );
        }
        return this;
    }

    public Leg moveDown(){
        move(getEndingPoint().subtract(0,0,(getEndingPoint().getZ() >= 10)? 10 : getEndingPoint().getZ()));
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
        Rotate phisp4 = new Rotate(90, Rotate.X_AXIS);
        sp4.getTransforms().addAll(phisp4);

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