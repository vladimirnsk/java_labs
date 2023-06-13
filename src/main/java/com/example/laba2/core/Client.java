package com.example.laba2.core;

import com.example.laba2.CarsApplication;
import com.example.laba2.Interface.ClientCallback;
import com.example.laba2.enteties.Cars;
import com.example.laba2.enteties.CarsDTO;
import com.example.laba2.enteties.CarsInGeneral;
import com.example.laba2.server.Main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {
    private String clientName = "generalGavZ";
    private static String serverAddress = "127.0.0.1";
    private static int serverPort = 8081;
    private static Socket socket;
    private final ClientCallback clientCallback;

    private ObjectOutputStream outputStream;

    private ArrayList<CarsInGeneral> carsy;

    public Client(ClientCallback clientCallback) {
        this.clientCallback = clientCallback;
        this.clientName = getAlphaNumericString(10);
    }
    static String getAlphaNumericString(int n)
    {

        // choose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }
    public String getClientName() {
        return clientName;
    }
    public void setCarsy(ArrayList<CarsInGeneral> carsy){
        this.carsy = carsy;
    }

    public void start() {
        try {
            Scanner in = new Scanner(System.in);
            System.out.println("Введите PORT: ");
            int port = in.nextInt();
            System.out.println("PORT: " + port);
            serverPort=port;

            System.out.println("Введите IP-address: ");
            String address = in.next();
            serverAddress=address;
            System.out.println("IP-address: " + serverAddress);
            in.close();

            Socket socket = new Socket(serverAddress, serverPort);
            System.out.println("Подключено к серверу");

            outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

            // Отправляем сигнал серверу с именем клиента
            outputStream.writeObject(clientName);
            outputStream.flush();

            // Запускаем поток для чтения сообщений от сервера
            Thread readThread = new Thread(() -> {
                try {
                    while (true) {
                        //String objects = (String) inputStream.readObject();
                        Object objects = inputStream.readObject();
                        if(objects.getClass() == ArrayList.class){
                            ArrayList<CarsDTO> sendOverCars = (ArrayList<CarsDTO>) objects;
                            clientCallback.setCars(sendOverCars);
                            System.out.println("array list from server " + sendOverCars.size());
                        }
                        if (objects.getClass() == String.class) {
                            String string = (String) objects;
                            if(string.contains("send arraylist to ")){
                                String name = string.split(" ")[3];
                                carsExchangeReversed(name);
                            } else if (objects.equals("get")) {
                                ArrayList<CarsInGeneral> cars = clientCallback.getCars();
                                outputStream.writeObject(cars);
                                outputStream.flush();
                            } else {
                                String[] clients = string.
                                        replace('[', ' ').
                                        replace(']', ' ').
                                        trim().
                                        split(", ");

                                clientCallback.setClients(clients);
                            }
                        }
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });
            readThread.start();

        } catch (IOException e) {
            CarsApplication.setServerTry(false);
            System.out.println("Сервер недоступен");
        }
    }

    public void carsExchangeReversed(String name){
        ArrayList<CarsInGeneral> allCars = carsy;
        ArrayList<CarsDTO> sortedCars = new ArrayList<>();
        for (CarsInGeneral c : allCars) {
            if(c instanceof Cars){
                sortedCars.add(((Cars) c).toCarsDTO());
            }
        }
        Pair pair = new Pair(name,sortedCars);
        try {
            outputStream.writeObject(pair);
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("send array list "+ sortedCars.size()+" to "+ name);
    }
    public void carsExchange(String name, ArrayList<CarsDTO> sortedCars) {
        try {
                Pair pair = new Pair(name,sortedCars);
                Request request = new Request(name,clientName);
                outputStream.writeObject(pair);
                outputStream.flush();
                outputStream.writeObject(request);
                outputStream.flush();
                System.out.println("send array list from " + name +" to " + sortedCars);
                System.out.println("send array list "+ sortedCars.size() + " to " + name);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }
}