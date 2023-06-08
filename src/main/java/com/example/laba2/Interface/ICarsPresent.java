package com.example.laba2.Interface;

import com.example.laba2.enteties.CarsInGeneral;

import java.util.ArrayList;

public interface ICarsPresent
{
    boolean setParams(String CarsInterval, String TrucksInterval, double CarsProbability, double TrucksProbability, String carslifetime, String truckslifetime);

    void showStatsDialog();

    void update(double elapsed);

    void resetAll();

    int getSimulationTime();

    void showProbki();

    void startTrucksAI();

    void stopTrucksAI();

    void stopCarsAI();

    void startCarsAI();

    void carsBoxPriority(int selectedPriority);

    void trucksBoxPriority(int selectedPriority);

    void setCars(ArrayList<CarsInGeneral> loadedCars);
    boolean checkInput(String input);

    ArrayList<CarsInGeneral> getCars();
}
