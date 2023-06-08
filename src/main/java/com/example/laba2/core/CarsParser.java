package com.example.laba2.core;

import com.example.laba2.Interface.ICarsController;
import com.example.laba2.Interface.ICarsModel;
import com.example.laba2.Interface.ICarsPresent;
import com.example.laba2.enteties.CarsInGeneral;
import com.example.laba2.enteties.Trucks;
import com.example.laba2.enteties.Cars;

import java.util.ArrayList;
import java.util.Vector;

public class CarsParser implements ICarsPresent
{
    private final ICarsController controller;
    private final ICarsModel model = new CarsModel();

    public CarsParser(ICarsController controller)
    {
        this.controller = controller;
    }

    @Override
    public boolean setParams(String CarsInterval, String TrucksInterval, double CarsProbability, double TrucksProbability, String carslifetime, String truckslifetime)
    {

        int CarsIntervalInt = 0;
        int TrucksIntervalInt = 0;
        int CarsProbabilityInt;
        int TrucksProbabilityInt;
        int intcarslifetime=0;
        int inttruckslifetime=0;

        if (isNumeric(CarsInterval))
        {
            CarsIntervalInt = Integer.parseInt(CarsInterval);
        } else
        {
            if (CarsInterval.isEmpty())
            {
                controller.Error("Пустой интервал для машин");
                return false;
            } else
            {
                controller.Error("Неверно введено: " + CarsInterval);
                return false;
            }
        }

        if (isNumeric(TrucksInterval))
        {
            TrucksIntervalInt = Integer.parseInt(TrucksInterval);
        } else
        {
            if (TrucksInterval.isEmpty())
            {
                controller.Error("Пустой интервал для грузовиков");
                return false;
            } else
            {
                controller.Error("Неверно введено: " + TrucksInterval);
                return false;
            }
        }
        if (isNumeric(carslifetime))
        {
            intcarslifetime = Integer.parseInt(carslifetime);
        } else
        {
            if (carslifetime.isEmpty())
            {
                controller.Error("Пустой интервал для машин");
                return false;
            } else
            {
                controller.Error("Неверно введено: " + carslifetime);
                return false;
            }
        }
        if (isNumeric(truckslifetime))
        {
            inttruckslifetime = Integer.parseInt(truckslifetime);
        } else
        {
            if (truckslifetime.isEmpty())
            {
                controller.Error("Пустой интервал для машин");
                return false;
            } else
            {
                controller.Error("Неверно введено: " + truckslifetime);
                return false;
            }
        }

        CarsProbabilityInt = (int) CarsProbability;
        TrucksProbabilityInt = (int) TrucksProbability;

        model.setParameters(CarsIntervalInt, TrucksIntervalInt, CarsProbabilityInt, TrucksProbabilityInt, intcarslifetime, inttruckslifetime);
        return true;
    }

    @Override
    public void showStatsDialog()
    {
        int simulationTime = model.getSimulationTime();
        int CarsCount = Cars.count;
        int TrucksCount = Trucks.count;

        controller.showStats(simulationTime, CarsCount, TrucksCount);
    }
    @Override
    public void update(double elapsed)
    {
        model.update(elapsed);

        ArrayList<CarsInGeneral> carsInGenerals = model.getCars();
        int simulationTime = model.getSimulationTime();

        controller.updateCars(carsInGenerals);
        controller.updateStats(simulationTime);
    }

    @Override
    public void resetAll()
    {
        model.resetAll();
    }

    @Override
    public int getSimulationTime()
    {
        return model.getSimulationTime();
    }

    @Override
    public void showProbki() {
        ArrayList <CarsInGeneral> allCars = model.getCars();
        controller.ShowAllCars(allCars);
    }

    @Override
    public void startTrucksAI() {
        model.startTrucksAI();
    }

    @Override
    public void stopTrucksAI() {
        model.stopTrucksAI();
    }

    @Override
    public void stopCarsAI() {
        model.stopCarsAI();
    }

    @Override
    public void startCarsAI() {
        model.startCarsAI();
    }

    @Override
    public void carsBoxPriority(int selectedPriority) {
        model.carsBoxPriority(selectedPriority);
    }

    @Override
    public void trucksBoxPriority(int selectedPriority) {
        model.trucksBoxPriority(selectedPriority);
    }
    @Override
    public void setCars(ArrayList<CarsInGeneral> loadedCars) {
        model.setCars(loadedCars);
    }
    @Override
    public ArrayList<CarsInGeneral> getCars() {
        return model.getCars();
    }
    @Override
    public boolean checkInput(String input) {
        return isNumeric(input);
    }

    private static boolean isNumeric(String str)
    {
        return str != null && str.matches("[0-9]+");
    }


}
