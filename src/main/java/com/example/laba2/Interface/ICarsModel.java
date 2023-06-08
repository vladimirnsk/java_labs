package com.example.laba2.Interface;

import com.example.laba2.enteties.CarsInGeneral;

import java.util.ArrayList;

public interface ICarsModel
{
    ArrayList<CarsInGeneral> getCars();
    void setParameters(int CarsInterval, int TrucksInterval, int CarsProbability, int TrucksProbability, int carsLifeTime, int trucksLifeTime);

    int getSimulationTime();

    void update(double elapsed);

    void resetAll();

    void startCarsAI();

    void stopCarsAI();

    void stopTrucksAI();

    void startTrucksAI();

    void carsBoxPriority(int selectedPriority);

    void trucksBoxPriority(int selectedPriority);

    void setCars(ArrayList<CarsInGeneral> loadedCars);
}
