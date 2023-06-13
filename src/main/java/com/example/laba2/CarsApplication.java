package com.example.laba2;

import com.example.laba2.core.CarsController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;

import javafx.stage.Stage;

import java.io.IOException;

public class CarsApplication extends Application
{
private static boolean serverTrue =true;

public static void setServerTry(boolean serverTry){
    serverTrue=serverTry;}

    @Override
    public void start(Stage stage) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader((getClass().getResource("/com/example/laba2/cars-view.fxml")));
        CarsController carsController = new CarsController();
        carsController.setStage(stage);
        fxmlLoader.setControllerFactory(controllerClass -> carsController);
        Scene scene = new Scene(fxmlLoader.load(), 1100, 575);

        stage.setOnCloseRequest(event -> {
            System.out.println("save");
            carsController.saveSimulationOptions();
            try {
                Platform.exit();
                System.exit(0);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        scene.getRoot().requestFocus();
        stage.setResizable(false);
        stage.setTitle("AVE-AVE");
        stage.setScene(scene);
        if(serverTrue){
            stage.show();

    }
        else {
        System.exit(0);
        }
    }

    public static void main(String[] args)
    {
        launch();
    }
}