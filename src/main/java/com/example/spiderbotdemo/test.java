package com.example.spiderbotdemo;


public class test {

    public static void main(String[] args) {


        Vector v = new Vector(1,-1,0);
        Spherical s = new Spherical(0, 26.565, 2.236);


        System.out.println("::::::::::::::::::::");

        System.out.println(v.print() +" "+ v.toSpherical().print());
        System.out.println(s.print() +" "+ s.toVector().print());

        System.out.println("::::::::::::::::::::");











    }


}
