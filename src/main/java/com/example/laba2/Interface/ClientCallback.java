package com.example.laba2.Interface;

import com.example.laba2.enteties.CarsDTO;
import com.example.laba2.enteties.CarsInGeneral;

import java.util.ArrayList;

public interface ClientCallback {
    ArrayList<CarsInGeneral> getCars();

    void setClients(String[] clientsNames);

    void setCars(ArrayList<CarsDTO> sendOverCars);
}
