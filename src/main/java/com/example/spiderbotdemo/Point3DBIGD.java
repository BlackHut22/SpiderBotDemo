package com.example.spiderbotdemo;


import java.math.BigDecimal;
import java.math.MathContext;

public class Point3DBIGD {
    private final BigDecimal x;
    private final BigDecimal y;
    private final BigDecimal z;
    public Point3DBIGD(BigDecimal x, BigDecimal y, BigDecimal z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public BigDecimal getX() {
        return x;
    }

    public BigDecimal getY() {
        return y;
    }

    public BigDecimal getZ() {
        return z;
    }

    public BigDecimal distance(BigDecimal x1, BigDecimal y1, BigDecimal z1) {
        BigDecimal a = getX().subtract(x1);
        BigDecimal b = getY().subtract(y1);
        BigDecimal c = getZ().subtract(z1);
        return a.pow(2).add(b.pow(2)).add(c.pow(2)).sqrt(MathContext.DECIMAL32);
    }

    public BigDecimal distance(Point3DBIGD  point) {
        return distance(point.getX(), point.getY(), point.getZ());
    }

    public Point3DBIGD add(BigDecimal x, BigDecimal y, BigDecimal z) {
        return new Point3DBIGD(
                getX().add(x),
                getY().add(y),
                getZ().add(z));
    }

    public Point3DBIGD add(Point3DBIGD point) {
        return add(point.getX(), point.getY(), point.getZ());
    }

    public Point3DBIGD subtract(BigDecimal x, BigDecimal y, BigDecimal z) {
        return new Point3DBIGD(
                getX().subtract(x),
                getY().subtract(y),
                getZ().subtract(z));
    }

    public Point3DBIGD subtract(Point3DBIGD point) {
        return subtract(point.getX(), point.getY(), point.getZ());
    }

    public Point3DBIGD multiply(BigDecimal factor) {
        return new Point3DBIGD(getX().multiply(factor), getY().multiply(factor), getZ().multiply(factor));
    }

    public Point3DBIGD normalize() {
        final BigDecimal mag = magnitude();

        if (mag.equals(new BigDecimal("0.0"))) {
            return new Point3DBIGD(new BigDecimal("0.0"), new BigDecimal("0.0"), new BigDecimal("0.0"));
        }

        return new Point3DBIGD(
                getX().divide(mag,MathContext.DECIMAL32),
                getY().divide(mag,MathContext.DECIMAL32),
                getZ().divide(mag,MathContext.DECIMAL32));
    }

    public Point3DBIGD midpoint(BigDecimal x, BigDecimal y, BigDecimal z) {
        return new Point3DBIGD(
                x.add((getX().subtract(x)).divide(new BigDecimal("2"),MathContext.DECIMAL32)),
                y.add((getY().subtract(y)).divide(new BigDecimal("2"),MathContext.DECIMAL32)),
                z.add((getZ().subtract(z)).divide(new BigDecimal("2"),MathContext.DECIMAL32)));
    }

    public Point3DBIGD midpoint(Point3DBIGD point) {
        return midpoint(point.getX(), point.getY(), point.getZ());
    }

    public BigDecimal angle(BigDecimal x, BigDecimal y, BigDecimal z) {
        final BigDecimal ax = getX();
        final BigDecimal ay = getY();
        final BigDecimal az = getZ();

        final BigDecimal delta = (ax.multiply(x).add(ay.multiply(y)).add(az.multiply(z))).divide((((ax.pow(2).add(ay.pow(2)).add(az.pow(2))).multiply((x.pow(2).add(y.pow(2)).add(z.pow(2))))).sqrt(MathContext.DECIMAL32)),MathContext.DECIMAL32);

        if (delta.compareTo(new BigDecimal("1")) > 0) {
            return new BigDecimal("0.0");
        }
        if (delta.compareTo(new BigDecimal("-1")) < 0) {
            return new BigDecimal("180.0");
        }

        return new BigDecimal(Double.toString(Math.toDegrees(Math.acos(delta.doubleValue()))));
    }

    public BigDecimal angle(Point3DBIGD point) {
        return angle(point.getX(), point.getY(), point.getZ());
    }

    public BigDecimal angle(Point3DBIGD p1, Point3DBIGD p2) {
        final BigDecimal x = getX();
        final BigDecimal y = getY();
        final BigDecimal z = getZ();

        final BigDecimal ax = p1.getX().subtract(x);
        final BigDecimal ay = p1.getY().subtract(y);
        final BigDecimal az = p1.getZ().subtract(z);
        final BigDecimal bx = p2.getX().subtract(x);
        final BigDecimal by = p2.getY().subtract(y);
        final BigDecimal bz = p2.getZ().subtract(z);

        final BigDecimal delta = (ax.multiply(bx).add(ay.multiply(by)).add(az.multiply(bz))).divide((((ax.pow(2).add(ay.pow(2)).add(az.pow(2))).multiply((bx.pow(2).add(by.pow(2)).add(bz.pow(2))))).sqrt(MathContext.DECIMAL32)),MathContext.DECIMAL32);

        if (delta.compareTo(new BigDecimal("1")) > 0) {
            return new BigDecimal("0.0");
        }
        if (delta.compareTo(new BigDecimal("-1")) < 0) {
            return new BigDecimal("180.0");
        }

        return new BigDecimal(Double.toString(Math.toDegrees(Math.acos(delta.doubleValue()))));
    }

    public BigDecimal magnitude() {
        final BigDecimal x = getX();
        final BigDecimal y = getY();
        final BigDecimal z = getZ();

        return (x.pow(2).add(y.pow(2)).add(z.pow(2))).sqrt(MathContext.DECIMAL32);
    }

    public BigDecimal dotProduct(BigDecimal x, BigDecimal y, BigDecimal z) {
        return getX().multiply(x).add(getY().multiply(y)).add(getZ().multiply(z));
    }

    public BigDecimal dotProduct(Point3DBIGD vector) {
        return dotProduct(vector.getX(), vector.getY(), vector.getZ());
    }

    public Point3DBIGD crossProduct(BigDecimal x, BigDecimal y, BigDecimal z) {
        final BigDecimal ax = getX();
        final BigDecimal ay = getY();
        final BigDecimal az = getZ();

        return new Point3DBIGD(
                ay.multiply(z).subtract(az.multiply(y)),
                az.multiply(x).subtract(ax.multiply(z)),
                ax.multiply(y).subtract(ay.multiply(x)));
    }

    public Point3DBIGD crossProduct(Point3DBIGD vector) {
        return crossProduct(vector.getX(), vector.getY(), vector.getZ());
    }

    public Point3DBIGD interpolate(Point3DBIGD endValue, BigDecimal t) {
        if (t.compareTo(new BigDecimal("0.0")) <= 0.0) return this;
        if (t.compareTo(new BigDecimal("1.0")) >= 0.0) return endValue;
        return new Point3DBIGD(
                getX().add((endValue.getX().subtract(getX())).multiply(t)),
                getY().add((endValue.getY().subtract(getY())).multiply(t)),
                getZ().add((endValue.getZ().subtract(getZ())).multiply(t))
        );
    }

    @Override public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj instanceof Point3DBIGD) {
            Point3DBIGD other = (Point3DBIGD) obj;
            return getX().compareTo(other.getX()) == 0 && getY().compareTo(other.getY()) == 0 && getZ().compareTo(other.getZ()) == 0;
        } else return false;
    }



    @Override public String toString() {
        return "Point3DBIGD [x = " + getX() + ", y = " + getY() + ", z = " + getZ() + "]";
    }
}












