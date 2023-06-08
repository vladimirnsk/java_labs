package com.example.laba2.enteties;

import com.example.laba2.core.Habitat;
import javafx.scene.image.Image;

import java.util.Random;

public class Cars extends CarsInGeneral
{
    private static final Image image = new Image(Cars.class.getResourceAsStream("/com/example/laba2/car.png"));
    private final Random random = new Random();
    public static int count = 0;
    private double x;
    private double y;
    private final int size = 50;
    private int carslifetime = 0;
    private int carsbirthtime = 0;
    private int id = 0;
    private double speed = 4;
    double targetX=0;
    double targetY=0;
    public Cars(int carslifetime,int carsbirthtime, int id)
    {
        count++;
        x = random.nextInt(Habitat.getWidth() - size);
        y = random.nextInt(Habitat.getHeight() - size);
        this.carslifetime=carslifetime;
        this.carsbirthtime=carsbirthtime;
        this.id=id;
        if (x < Habitat.getInstance().width / 2 || y < Habitat.getInstance().height / 2) {
            targetX = random.nextInt(Habitat.getWidth()/2,Habitat.getWidth()-getSize());
            targetY = random.nextInt(Habitat.getHeight()/2,Habitat.getHeight()-getSize());
        } else {
            targetX = x;
            targetY = y;
        }

    }
    public Cars(CarsDTO obj) {
        this.x = obj.getX();
        this.y = obj.getY();
        this.carslifetime = 50;
        this.id -= obj.getId();
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
        return carslifetime;
    }

    @Override
    public int getBirthtime() {
        return carsbirthtime;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void crashed() {
        count --;
    }

    @Override
    public String toString()
    {
        return id + " Cars";
    }

    @Override
    public void setX(double x) {
        this.x = x;
    }

    @Override
    public void setY(double y) {
        this.y = y;
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
    public void setBirthTime(int carsbirthtime) {
        this.carsbirthtime = carsbirthtime;
    }

    public CarsDTO toCarsDTO() {
        return new CarsDTO(x, y, id);
    }


}
