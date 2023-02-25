package com.example.spiderbotdemo;


public class test {

    public static void main(String[] args) {




        System.out.println("::::::::::::::::::::");


        double a = ((0 - 0) * 1 + (200 - 0) * 0) / (Math.pow(1,2) + Math.pow(0,2));
        double x = 0 + a * 1;
        double y = 0 + a * 0;

        double distance = Math.sqrt(Math.pow(0 - x,2) + Math.pow(200 - y,2));
        double newDistance = Math.max(0, distance-1);

        double Length = Math.sqrt(Math.pow(500,2) - Math.pow(distance,2));
        double newLength = Math.sqrt(Math.pow(500,2) - Math.pow(newDistance,2));
        double addSLength = newLength-Length;

        System.out.println(distance);
        System.out.println(newDistance);
        System.out.println(Length);
        System.out.println(newLength);
        System.out.println(addSLength);

        System.out.println("::::::::::::::::::::");











    }


}
