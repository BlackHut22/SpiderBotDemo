package com.example.spiderbotdemo;

import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;

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
        return new Legsegment(startingPoint, getSpherical(), getLength());
    }

    public Legsegment addSphericalTheta(double theta){
        return new Legsegment(getStartingPoint(), getSpherical().addTheta(theta), getLength());
    }

    public Legsegment addSphericalPhi(double phi){
        return new Legsegment(getStartingPoint(), getSpherical().addPhi(phi), getLength());
    }

    public Legsegment setSphericalTheta(double theta){
        return new Legsegment(getStartingPoint(), getSpherical().setTheta(theta), getLength());
    }

    public Legsegment setSphericalPhi(double phi){
        return new Legsegment(getStartingPoint(), getSpherical().setPhi(phi), getLength());
    }

    public Vector getDirection(){
        return getSpherical().toVector();
    }


    public Point3D getEndingPoint(){
        new Point3D(1,1,1).midpoint(new Point3D(1,1,1));
        return new Point3D(  getStartingPoint().getX() + ( (getLength() * getDirection().getX()) / getDirection().getMagnitude()  ),
                                getStartingPoint().getY() + ( (getLength() * getDirection().getY()) / getDirection().getMagnitude()  ),
                                getStartingPoint().getZ() + ( (getLength() * getDirection().getZ()) / getDirection().getMagnitude()  )
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

    public ArrayList<Node> getDisplayNodes(){
        Cylinder cylinder = new Cylinder();
        cylinder.setHeight(getLength());
        cylinder.setRadius(10);
        cylinder.translateXProperty().set(getMiddlePoint().getX());
        cylinder.translateYProperty().set(getMiddlePoint().getY());
        cylinder.translateZProperty().set(getMiddlePoint().getZ());
        PhongMaterial mat = new PhongMaterial();
        cylinder.setMaterial(mat);
        mat.setDiffuseColor(Color.YELLOW);

        Rotate thetaRot = new Rotate(getSpherical().getTheta(), Rotate.Z_AXIS);
        Rotate sigmaRot = new Rotate(90, Rotate.Y_AXIS);
        Rotate phiRot = new Rotate(-getSpherical().getPhi(), Rotate.Z_AXIS);

        cylinder.getTransforms().addAll(phiRot, sigmaRot, thetaRot);

        Sphere sphere1 = new Sphere();
        sphere1.setRadius(10);
        sphere1.translateXProperty().set(getStartingPoint().getX());
        sphere1.translateYProperty().set(getStartingPoint().getY());
        sphere1.translateZProperty().set(getStartingPoint().getZ());
        Sphere sphere2 = new Sphere();
        sphere2.setRadius(10);
        sphere2.translateXProperty().set(getEndingPoint().getX());
        sphere2.translateYProperty().set(getEndingPoint().getY());
        sphere2.translateZProperty().set(getEndingPoint().getZ());
        PhongMaterial mat1 = new PhongMaterial();
        PhongMaterial mat2 = new PhongMaterial();
        sphere1.setMaterial(mat1);
        sphere2.setMaterial(mat2);
        mat1.setDiffuseColor(Color.RED);
        mat2.setDiffuseColor(Color.GREEN);

        return new ArrayList<>(Arrays.asList(cylinder, sphere1, sphere2));

    }
}
