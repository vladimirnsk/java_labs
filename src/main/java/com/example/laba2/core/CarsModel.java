package com.example.laba2.core;

import com.example.laba2.Interface.ICarsModel;
import com.example.laba2.enteties.CarsInGeneral;

import java.util.ArrayList;
import java.util.Vector;

public class CarsModel implements ICarsModel
{

    @Override
    public int getSimulationTime()
    {
        return Habitat.getInstance().getSimulationTime();
    }

    @Override
    public ArrayList<CarsInGeneral> getCars()
    {
        return Habitat.getInstance().getCars();
    }

    @Override
    public void setParameters(int CarsInterval, int TrucksInterval, int CarsProbability, int TrucksProbability, int carsLifeTime, int trucksLifeTime) {
        Habitat.getInstance().setParams(CarsInterval, TrucksInterval, CarsProbability, TrucksProbability, carsLifeTime, trucksLifeTime);
    }

    @Override
    public void update(double elapsed)
    {
        if ((int) elapsed != Habitat.getInstance().getSimulationTime()) {
            Habitat.getInstance().update(elapsed);
        }
    }

    @Override
    public void resetAll()
    {
        Habitat.getInstance().resetAll();
    }

    @Override
    public void startCarsAI() {
        Habitat.getInstance().startCarsAI();
    }

    @Override
    public void stopCarsAI() {
        Habitat.getInstance().stopCarsAI();
    }

    @Override
    public void stopTrucksAI() {
        Habitat.getInstance().stopTrucksAI();
    }

    @Override
    public void startTrucksAI() {
        Habitat.getInstance().startTrucksAI();
    }

    @Override
    public void carsBoxPriority(int selectedPriority) {
        Habitat.getInstance().carsBoxPriority(selectedPriority);
    }

    @Override
    public void trucksBoxPriority(int selectedPriority) {
        Habitat.getInstance().trucksBoxPriority(selectedPriority);
    }
    @Override
    public void setCars(ArrayList<CarsInGeneral> loadedCars) {
        CarsRep.getInstance().setCars(loadedCars);
    }
}