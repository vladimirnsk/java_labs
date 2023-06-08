package com.example.laba2.enteties;

import com.example.laba2.Interface.IBehaviour;
import javafx.scene.image.Image;

import java.io.Serializable;

public abstract class CarsInGeneral implements IBehaviour, Serializable
{
    public abstract Image getImage();
    public abstract int getSize();
    public abstract int getLifetime();
    public abstract int getBirthtime();
    public abstract int getId();

    public abstract void crashed();

    public abstract double getTargetX();

    public abstract void setBirthTime(int carsbirthtime);
}
