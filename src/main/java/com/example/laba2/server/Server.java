package com.example.laba2.server;

import com.example.laba2.core.Pair;
import com.example.laba2.core.Request;
import com.example.laba2.enteties.CarsDTO;
import com.example.laba2.enteties.CarsInGeneral;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.net.ServerSocket;
import java.util.Map;
import java.util.Objects;

public class Server {
    private final ArrayList<ClientHandler> clients = new ArrayList<>();
    private final int port = 1488;
    void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Сервер запущен на порту " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Новый клиент подключен");

                ClientHandler clientHandler = new ClientHandler(socket);
                clients.add(clientHandler);
                clientHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private ClientHandler getEventHandlerByName(String name){
        ClientHandler r = null;
        for(ClientHandler client : clients){
            if(Objects.equals(client.name, name)){
                r = client;
                break;
            }
        }
        return r;
    }

    public void broadcastMessage(String message) {
        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }

    private class ClientHandler extends Thread {
        private String name;
        private Socket socket;
        private ObjectInputStream inputStream;
        private ObjectOutputStream outputStream;
        public void sendTo(String name, String message) {
            for (ClientHandler client : clients) {
                if (client.name.equals(name)) {
                    client.sendMessage(message);
                    return;
                }
            }
        }
        public ClientHandler(Socket socket) {
            this.socket = socket;
        }
        public void sendArrayList(ArrayList<CarsDTO> sortedCars){
            try {
                outputStream.writeObject(sortedCars);
                outputStream.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        public void run() {
            try {
                outputStream = new ObjectOutputStream(socket.getOutputStream());
                inputStream = new ObjectInputStream(socket.getInputStream());

                String clientName = (String) inputStream.readObject();
                System.out.println("Клиент " + clientName + " подключен");
                name = clientName;
                broadcastMessage(clients.toString());

                while (true) {
                    Object object = inputStream.readObject();
                    if(object.getClass() == Pair.class){
                        Pair p = (Pair) object;
                        ClientHandler exchanged = getEventHandlerByName(p.name);
                        exchanged.sendArrayList(p.sortedCars);
                        System.out.println("send array list " + p.sortedCars.size() + " to "+ p.name);
                        //sendTo(p.name,"get");
                        //ArrayList<CarsInGeneral>c2=(ArrayList<CarsInGeneral>) inputStream.readObject();
                        //sendTo(p.name,);
                    }
                    System.out.println("Получено сообщение от " + clientName + ": " + object);
                    if(object.getClass() == Request.class){
                        Request c = (Request) object;
                        ClientHandler exchanged = getEventHandlerByName(c.toName);
                        System.out.println("send array list from "+ c.toName + " to " + c.fromName);
                        exchanged.sendTo(c.toName,"send arraylist to " +c.fromName);
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                //e.printStackTrace();
            } finally {
                try {
                    inputStream.close();
                    outputStream.close();
                    socket.close();
                    clients.remove(this);
                    System.out.println("Клиент " + name + " отключен");
                    broadcastMessage(clients.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void sendMessage(String message) {
            try {
                outputStream.writeObject(message);
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String toString() {
            return name;
        }
    }
}