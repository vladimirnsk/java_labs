package com.example.laba2.core;

import com.example.laba2.enteties.*;

import java.util.ArrayList;
import java.util.Random;

//Singleton
public class Habitat
{
    private static Habitat instance;

    public static synchronized Habitat getInstance()
    {
        if (instance == null)
        {
            instance = new Habitat();
        }
        return instance;
    }
    public static int width = 550;
    public static int height = 500;
    private final Random random = new Random();
    private int simulationTime = 0;
    private int CarsInterval;
    private double CarsProbability;
    private int TrucksInterval;
    private double TrucksProbability;
    private int carsLifeTime;
    private int trucksLifeTime;

    private CarsAI carsAI=new CarsAI();
    private TrucksAI trucksAI = new TrucksAI();
    private Habitat()
    {
    carsAI.start();
    trucksAI.start();
    }
    public void setParams(int CarsInterval, int TrucksInterval, int CarsProbability, int TrucksProbability, int carsLifeTime, int trucksLifeTime)
    {
        this.CarsInterval = CarsInterval == 0 ? 2 : CarsInterval;
        this.TrucksInterval = TrucksInterval == 0 ? 5 : TrucksInterval;
        this.CarsProbability = CarsProbability == 0 ? 0.7 : CarsProbability / 100.0;
        this.TrucksProbability = TrucksProbability == 0 ? 0.3 : TrucksProbability / 100.0;
        this.carsLifeTime = carsLifeTime == 0 ? 22:carsLifeTime;
        this.trucksLifeTime = trucksLifeTime == 0 ? 88:trucksLifeTime;
    }
    private void isCarCrashed()
    {
        for(int i = 0;i<CarsRep.getInstance().size();i++){
            CarsInGeneral baba = CarsRep.getInstance().get(i);
            if (baba.getBirthtime()+baba.getLifetime() < simulationTime){
                baba.crashed();
                CarsRep.getInstance().remove(baba);
            }
        }
    }
    private void spawnCars()
    {
        if (simulationTime % CarsInterval == 0 && random.nextDouble(1) < CarsProbability)
        {
            int id=random.nextInt(1000);
            CarsRep.getInstance().add(new Cars(carsLifeTime, simulationTime, id));
        }

        if (simulationTime % TrucksInterval == 0 && (double) Trucks.count / CarsRep.getInstance().size() < TrucksProbability)
        {
            int id=random.nextInt(1000);
            CarsRep.getInstance().add(new Trucks(trucksLifeTime, simulationTime, id));
        }
    }

    public void update(double elapsed)
    {
        simulationTime = (int) elapsed;
        isCarCrashed();
        spawnCars();
    }

    public void resetAll()
    {
        simulationTime = 0;
        Cars.count = 0;
        Trucks.count = 0;
        CarsRep.getInstance().clear();
    }

    public int getSimulationTime()
    {
        return simulationTime;
    }

    public ArrayList<CarsInGeneral> getCars()
    {
        return CarsRep.getInstance().getList();
    }

    public static int getWidth()
    {
        return width;
    }

    public static int getHeight()
    {
        return height;
    }
    public void startTrucksAI() {
        if(!trucksAI.isActive()){
            trucksAI.startAI();
        }
    }

    public void stopTrucksAI() {
        if(trucksAI.isActive()){
            trucksAI.stopAI();
        }
    }

    public void stopCarsAI() {
        if(carsAI.isActive()){
            carsAI.stopAI();
        }
    }

    public void startCarsAI() {
        if(!carsAI.isActive()){
            carsAI.startAI();
        }
    }

    public void carsBoxPriority(int selectedPriority) {
        carsAI.setAIPriority(selectedPriority);
    }

    public void trucksBoxPriority(int selectedPriority) {
        trucksAI.setAIPriority(selectedPriority);
    }
}
