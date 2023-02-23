package com.example.spiderbotdemo;

import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;
public class Legsegment {
    Point3D startingPoint;
    Spherical spherical;
    double length;

    public Legsegment(Point3D startingPoint, Spherical spherical, double length){
        this.startingPoint = startingPoint;
        this.spherical = spherical;
        this.length = length;
    }

    public Legsegment updateStartingPoint(Point3D startingPoint){
        return new Legsegment(startingPoint, this.getSpherical(), getLength());
    }

    public Legsegment addSphericalTheta(double theta){
        return new Legsegment(this.getStartingPoint(), this.getSpherical().addTheta(theta), getLength());
    }

    public Legsegment addSphericalPhi(double phi){
        return new Legsegment(this.getStartingPoint(), this.getSpherical().addPhi(phi), getLength());
    }

    public Legsegment setSphericalTheta(double theta){
        return new Legsegment(this.getStartingPoint(), this.getSpherical().setTheta(theta), getLength());
    }

    public Legsegment setSphericalPhi(double phi){
        return new Legsegment(this.getStartingPoint(), this.getSpherical().setPhi(phi), getLength());
    }

    public Vector getDirection(){
        return this.getSpherical().toVector();
    }


    public Point3D getEndingPoint(){
        return new Point3D(  this.getStartingPoint().getX()+((this.getLength()*this.getDirection().getX())/this.getDirection().getMagnitude()),
                            this.getStartingPoint().getY()+((this.getLength()*this.getDirection().getY())/this.getDirection().getMagnitude()),
                            this.getStartingPoint().getZ()+((this.getLength()*this.getDirection().getZ())/this.getDirection().getMagnitude())
        );
    }

    public Point3D getMiddlePoint(){
        return getStartingPoint().midpoint(getEndingPoint());
    }

    public Point3D getStartingPoint() {
        return startingPoint;
    }

    public Spherical getSpherical() {
        return spherical;
    }

    public double getLength() {
        return length;
    }




    public Legsegment updateSpherical(Spherical spherical){
        return new Legsegment(getStartingPoint(), spherical, getLength());
    }


    public void updateGrapicsNode(Node node){
        RotateTransition rotateTransition = new RotateTransition(Duration.millis(1), node);
        rotateTransition.setByAngle(180f);
        rotateTransition.setCycleCount(1);
        rotateTransition.setAutoReverse(true);

        SequentialTransition sequentialTransition = new SequentialTransition();
        sequentialTransition.getChildren().add(rotateTransition);
        sequentialTransition.setCycleCount(Timeline.INDEFINITE);
        sequentialTransition.setAutoReverse(true);
        sequentialTransition.play();
    }


    public ArrayList<Node> getDisplayNodes(){
        Cylinder cylinder = new Cylinder();
        cylinder.setHeight(this.getLength());
        cylinder.setRadius(10);
        cylinder.translateXProperty().set(this.getMiddlePoint().getX());
        cylinder.translateYProperty().set(this.getMiddlePoint().getY());
        cylinder.translateZProperty().set(this.getMiddlePoint().getZ());
        PhongMaterial mat = new PhongMaterial();
        cylinder.setMaterial(mat);
        mat.setDiffuseColor(Color.YELLOW);




        Rotate thetaRot = new Rotate(this.getSpherical().getTheta(), Rotate.Z_AXIS);
        Rotate sigmaRot = new Rotate(90, Rotate.Y_AXIS);
        Rotate phiRot = new Rotate(-this.getSpherical().getPhi(), Rotate.Z_AXIS);

        cylinder.getTransforms().addAll(phiRot, sigmaRot, thetaRot);

        Sphere sphere1 = new Sphere();
        sphere1.setRadius(10);
        sphere1.translateXProperty().set(this.getStartingPoint().getX());
        sphere1.translateYProperty().set(this.getStartingPoint().getY());
        sphere1.translateZProperty().set(this.getStartingPoint().getZ());
        Sphere sphere2 = new Sphere();
        sphere2.setRadius(10);
        sphere2.translateXProperty().set(this.getEndingPoint().getX());
        sphere2.translateYProperty().set(this.getEndingPoint().getY());
        sphere2.translateZProperty().set(this.getEndingPoint().getZ());
        PhongMaterial mat1 = new PhongMaterial();
        PhongMaterial mat2 = new PhongMaterial();
        sphere1.setMaterial(mat1);
        sphere2.setMaterial(mat2);
        mat1.setDiffuseColor(Color.RED);
        mat2.setDiffuseColor(Color.GREEN);

        return new ArrayList<>(Arrays.asList(cylinder, sphere1, sphere2));

    }
}
