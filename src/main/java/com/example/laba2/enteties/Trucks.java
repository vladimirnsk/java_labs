package com.example.laba2.enteties;

import com.example.laba2.core.Habitat;
import javafx.scene.image.Image;

import java.util.Random;

public class Trucks extends CarsInGeneral
{
    private static Image image = new Image(Cars.class.getResourceAsStream("/com/example/laba2/track.png"));
    private final Random random = new Random();
    public static int count = 0;
    private double x;
    private double y;
    private final int size = 50;
    private int truckslifetime = 0;
    private int trucksbirthtime = 0;
    private int id = 0;
    private double speed = 1;
    double targetX=0;
    double targetY=0;
    public Trucks(int truckslifetime,int trucksbirthtime, int id)
    {
        count++;
        x = random.nextInt(Habitat.getWidth() - size);
        y = random.nextInt(Habitat.getHeight() - size);
        this.truckslifetime=truckslifetime;
        this.trucksbirthtime=trucksbirthtime;
        this.id=id;
        if (x > Habitat.getInstance().width / 2 || y > Habitat.getInstance().height / 2) {
            targetX = random.nextInt(0,Habitat.getWidth()/2);
            targetY = random.nextInt(0,Habitat.getHeight()/2);
        } else {
            targetX = x;
            targetY = y;
        }
    }

    public Image getImage()
    {
        return image;
    }
    @Override
    public int getSize()
    {
        return size;
    }

    @Override
    public int getLifetime() {
        return truckslifetime;
    }

    @Override
    public int getBirthtime() {
        return trucksbirthtime;
    }

    @Override
    public int getId() {
        return id;
    }
    public void crashed() {
        count --;
    }

    @Override
    public String toString()
    {
        return id + " Trucks";
    }

    @Override
    public void setX(double x) {
        this.x = x;
    }

    @Override
    public void setY(double y) {
        this.y=y;
    }

    @Override
    public double getX()
    {
        return x;
    }

    @Override
    public double getY()
    {
        return y;
    }

    @Override
    public double getSpeed() {
        return speed;
    }
    @Override
    public double getTargetY() {
        return targetY;
    }
    @Override
    public double getTargetX() {
        return targetX;
    }

    @Override
    public void setBirthTime(int trucksbirthtime) {
        this.trucksbirthtime = trucksbirthtime;
    }
}
