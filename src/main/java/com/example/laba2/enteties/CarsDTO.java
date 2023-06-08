package com.example.laba2.enteties;

import java.io.Serializable;

public class CarsDTO implements Serializable {
    private double x;
    private double y;
    private int id;

    public CarsDTO(double x, double y, int id) {
        this.x=  x;
        this.y = y;
        this.id = id;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getId() {
        return id;
    }
}
