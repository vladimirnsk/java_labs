package com.example.laba2.core;

import com.example.laba2.enteties.CarsDTO;
import com.example.laba2.enteties.CarsInGeneral;

import java.io.Serializable;
import java.util.ArrayList;

public class Pair implements Serializable {
    public String name;
    public ArrayList<CarsDTO>sortedCars;

    public Pair(String name, ArrayList<CarsDTO>sortedCars){
        this.name = name;
        this.sortedCars = sortedCars;
    }
}
