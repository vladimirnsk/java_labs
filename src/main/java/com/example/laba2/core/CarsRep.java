package com.example.laba2.core;


import com.example.laba2.enteties.Cars;
import com.example.laba2.enteties.CarsInGeneral;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

public class CarsRep {

    private CarsRep() {

    }

    private static com.example.laba2.core.CarsRep instance;

    public static synchronized com.example.laba2.core.CarsRep getInstance() {
        if (instance == null) {
            instance = new com.example.laba2.core.CarsRep();
            return instance;
        }
        return instance;
    }

    private final ArrayList<CarsInGeneral> carsInGenerals = new ArrayList<>();
    private final TreeSet<Integer> carsId = new TreeSet<>();
    private final HashMap<Integer, Integer> carsIdBirth = new HashMap<>();


    public void add(CarsInGeneral cvar) {
        carsInGenerals.add(cvar);
        carsId.add(cvar.getId());
        carsIdBirth.put(cvar.getId(), cvar.getBirthtime());
    }

    public void remove(CarsInGeneral cars) {
        carsInGenerals.remove(cars);
        carsId.remove(cars.getId());
        carsIdBirth.remove(cars.getId());
    }

    public void clear() {
        carsInGenerals.clear();
        carsId.clear();
        carsIdBirth.clear();
    }

    public int size() {
        return carsInGenerals.size();
    }

    public CarsInGeneral get(int index) {
        return carsInGenerals.get(index);
    }

    public ArrayList<CarsInGeneral> getList() {
        return carsInGenerals;
    }

    public void setCars(ArrayList<CarsInGeneral> loadedCars) {
        carsInGenerals.clear();
        carsId.clear();
        carsIdBirth.clear();
        carsInGenerals.addAll(loadedCars);
        for (CarsInGeneral r : loadedCars) {
            r.setBirthTime(0);
            carsId.add(r.getId());
            carsIdBirth.put(r.getId(), r.getBirthtime());
        }
    }

    public void setNewCars(ArrayList<Cars> cars) {
        carsInGenerals.addAll(cars);
    }

}

