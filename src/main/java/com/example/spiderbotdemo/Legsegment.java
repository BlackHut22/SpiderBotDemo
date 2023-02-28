package com.example.spiderbotdemo;

import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Arrays;
public class Legsegment {
    Point3DBIGD startingPoint;
    Spherical spherical;
    BigDecimal length;

    public Legsegment(Point3DBIGD startingPoint, Spherical spherical, BigDecimal length){
        this.startingPoint = startingPoint;
        this.spherical = spherical;
        this.length = length;
    }

    public Legsegment updateStartingPoint(Point3DBIGD startingPoint){
        return new Legsegment(startingPoint, getSpherical(), getLength());
    }

    public Legsegment addSphericalTheta(BigDecimal theta){
        return new Legsegment(getStartingPoint(), getSpherical().addTheta(theta), getLength());
    }

    public Legsegment addSphericalPhi(BigDecimal phi){
        return new Legsegment(getStartingPoint(), getSpherical().addPhi(phi), getLength());
    }

    public Legsegment setSphericalTheta(BigDecimal theta){
        return new Legsegment(getStartingPoint(), getSpherical().setTheta(theta), getLength());
    }

    public Legsegment setSphericalPhi(BigDecimal phi){
        return new Legsegment(getStartingPoint(), getSpherical().setPhi(phi), getLength());
    }

    public Vector getDirection(){
        return getSpherical().toVector();
    }


    public Point3DBIGD getEndingPoint(){
        new Point3D(1,1,1).midpoint(new Point3D(1,1,1));
        return new Point3DBIGD(getStartingPoint().getX().add(  (getLength().multiply(getDirection().getX())).divide(getDirection().getMagnitude(), MathContext.DECIMAL32)  ),
                               getStartingPoint().getY().add(  (getLength().multiply(getDirection().getY())).divide(getDirection().getMagnitude(), MathContext.DECIMAL32)  ),
                               getStartingPoint().getZ().add(  (getLength().multiply(getDirection().getZ())).divide(getDirection().getMagnitude(), MathContext.DECIMAL32)  )
        );
    }


    public Point3DBIGD getMiddlePoint(){
        return getStartingPoint().midpoint(getEndingPoint());
    }

    public Point3DBIGD getStartingPoint() {
        return startingPoint;
    }

    public Spherical getSpherical() {
        return spherical;
    }

    public BigDecimal getLength() {
        return length;
    }




    public Legsegment updateSpherical(Spherical spherical){
        return new Legsegment(getStartingPoint(), spherical, getLength());
    }

    public ArrayList<Node> getDisplayNodes(){
        Cylinder cylinder = new Cylinder();
        cylinder.setHeight(getLength().doubleValue());
        cylinder.setRadius(10);
        cylinder.translateXProperty().set(getMiddlePoint().getX().doubleValue());
        cylinder.translateYProperty().set(getMiddlePoint().getY().doubleValue());
        cylinder.translateZProperty().set(getMiddlePoint().getZ().doubleValue());
        PhongMaterial mat = new PhongMaterial();
        cylinder.setMaterial(mat);
        mat.setDiffuseColor(Color.YELLOW);

        Rotate thetaRot = new Rotate(getSpherical().getTheta().doubleValue(), Rotate.Z_AXIS);
        Rotate sigmaRot = new Rotate(90, Rotate.Y_AXIS);
        Rotate phiRot = new Rotate(-getSpherical().getPhi().doubleValue(), Rotate.Z_AXIS);

        cylinder.getTransforms().addAll(phiRot, sigmaRot, thetaRot);

        Sphere sphere1 = new Sphere();
        sphere1.setRadius(10);
        sphere1.translateXProperty().set(getStartingPoint().getX().doubleValue());
        sphere1.translateYProperty().set(getStartingPoint().getY().doubleValue());
        sphere1.translateZProperty().set(getStartingPoint().getZ().doubleValue());
        Sphere sphere2 = new Sphere();
        sphere2.setRadius(10);
        sphere2.translateXProperty().set(getEndingPoint().getX().doubleValue());
        sphere2.translateYProperty().set(getEndingPoint().getY().doubleValue());
        sphere2.translateZProperty().set(getEndingPoint().getZ().doubleValue());
        PhongMaterial mat1 = new PhongMaterial();
        PhongMaterial mat2 = new PhongMaterial();
        sphere1.setMaterial(mat1);
        sphere2.setMaterial(mat2);
        mat1.setDiffuseColor(Color.RED);
        mat2.setDiffuseColor(Color.GREEN);

        return new ArrayList<>(Arrays.asList(cylinder, sphere1, sphere2));

    }
}
