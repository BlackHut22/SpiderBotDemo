package com.example.spiderbotdemo;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import java.util.*;

public class Main extends Application {

    private long lastTime = 0;
    double MAX_HEIGHT = 420;
    double MAX_WIDTH = 420;
    Spider spider = new Spider(new Point3D(1750,2250,0));

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage stage){
        Group world = new Group();
        Scene scene = new Scene(world,50000,5000, true, SceneAntialiasing.BALANCED);

        Image icon = new Image(Objects.requireNonNull(Main.class.getResource("images/spidertron.png")).toString());
        stage.getIcons().add(icon);
        stage.setTitle("SpiderBot");
        stage.setWidth(MAX_WIDTH);
        stage.setHeight(MAX_HEIGHT);
        stage.setResizable(false);
        stage.setX(700);
        stage.setY(300);




        //spider = spider.increaseHeight(400);
        world.getChildren().addAll(spider.getDisplayNodes());

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now - lastTime >= 1000000000 / 120) {
                    lastTime = now;
                    world.getChildren().clear();
                    spider = spider.moveStraight( new Spherical(0,90,1)).checkDirectionChange();
                    //spider = spider.moveDown();
                    //spider = spider.rotateBody(1);
                    world.getChildren().addAll(spider.getDisplayNodes());
                }
            }
        }.stop();


        stage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            world.getChildren().clear();
            switch (event.getCode()) {
                case W -> spider = spider.moveStraight( new Spherical(0,0,  5)).checkDirectionChange();
                case S -> spider = spider.moveStraight( new Spherical(0,180,5)).checkDirectionChange();
                case A -> spider = spider.moveStraight( new Spherical(0,270,5)).checkDirectionChange();
                case D -> spider = spider.moveStraight( new Spherical(0,90, 5)).checkDirectionChange();
            }
            world.getChildren().addAll(spider.getDisplayNodes());
        });











        world.getChildren().addAll(getAxisDisplayNodes());
        PerspectiveCamera camera = new PerspectiveCamera(false);
        scene.setCamera(camera);
        stage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            switch (event.getCode()){
                case UP -> camera.translateYProperty().set(camera.getTranslateY()-30);
                case DOWN -> camera.translateYProperty().set(camera.getTranslateY()+30);
                case LEFT -> camera.translateXProperty().set(camera.getTranslateX()-30);
                case RIGHT -> camera.translateXProperty().set(camera.getTranslateX()+30);
                case X -> camera.translateZProperty().set(camera.getTranslateZ()+30);
                case C -> camera.translateZProperty().set(camera.getTranslateZ()-30);
                case NUMPAD2 -> world.getTransforms().add(new Rotate(-1, Rotate.X_AXIS));
                case NUMPAD8 -> world.getTransforms().add(new Rotate(1, Rotate.X_AXIS));
                case NUMPAD4 -> world.getTransforms().add(new Rotate(-1, Rotate.Y_AXIS));
                case NUMPAD6 -> world.getTransforms().add(new Rotate(1, Rotate.Y_AXIS));
                case NUMPAD7 -> world.getTransforms().add(new Rotate(1, Rotate.Z_AXIS));
                case NUMPAD9 -> world.getTransforms().add(new Rotate(-1, Rotate.Z_AXIS));
            }
            //System.out.println(camera.getTranslateX() +" "+ camera.getTranslateY() +" "+ camera.getTranslateZ());
        });
        camera.setFieldOfView(100);
        camera.setTranslateX(1570);
        camera.setTranslateY(-1500);
        camera.setTranslateZ(-990);
        world.getTransforms().add(new Rotate(150, Rotate.X_AXIS));


        stage.setScene(scene);
        stage.show();

    }





    private List<Node> getAxisDisplayNodes(){
        Cylinder cylinderX = new Cylinder();
        Cylinder cylinderY = new Cylinder();
        Cylinder cylinderZ = new Cylinder();
        cylinderX.setHeight(10000);
        cylinderX.setRadius(1);
        cylinderX.translateXProperty().set(0);
        cylinderX.translateYProperty().set(0);
        cylinderX.translateZProperty().set(0);
        cylinderY.setHeight(10000);
        cylinderY.setRadius(1);
        cylinderY.translateXProperty().set(0);
        cylinderY.translateYProperty().set(0);
        cylinderY.translateZProperty().set(0);
        cylinderZ.setHeight(10000);
        cylinderZ.setRadius(1);
        cylinderZ.translateXProperty().set(0);
        cylinderZ.translateYProperty().set(0);
        cylinderZ.translateZProperty().set(0);

        PhongMaterial material1 = new PhongMaterial();
        PhongMaterial material2 = new PhongMaterial();
        PhongMaterial material3 = new PhongMaterial();
        material1.setDiffuseColor(Color.GREEN);
        material2.setDiffuseColor(Color.YELLOW);
        material3.setDiffuseColor(Color.BLUE);
        cylinderX.setMaterial(material1);
        cylinderY.setMaterial(material2);
        cylinderZ.setMaterial(material3);

        Rotate cylinderRotX = new Rotate(90, Rotate.Z_AXIS);
        cylinderX.getTransforms().add(cylinderRotX);
        Rotate cylinderRotZ = new Rotate(90, Rotate.X_AXIS);
        cylinderZ.getTransforms().add(cylinderRotZ);

        return new ArrayList<>(Arrays.asList(cylinderX,cylinderY,cylinderZ));
    }
}