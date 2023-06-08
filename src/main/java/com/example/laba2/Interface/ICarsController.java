package com.example.laba2.Interface;

import com.example.laba2.enteties.CarsInGeneral;

import java.util.ArrayList;

public interface ICarsController
{

    void updateCars(ArrayList<CarsInGeneral> carsInGenerals);

    void updateStats(int simulationTime);

    void showStats(int simulationTime, int CarsCount, int TrucksCount);

    void Error(String errorMessage);

    void update(double elapsed, double frameTime);

    void ShowAllCars(ArrayList<CarsInGeneral> allCars);

    void setCarsLifeTimeTextField(String s);

    void setTrucksLifeTimeTextField(String s);

    void setCarsIntervalTextField(String s);

    void setTrucksIntervalTextField(String s);

}
