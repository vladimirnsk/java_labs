package com.example.laba2;

import com.example.laba2.core.CarsController;
import com.example.laba2.core.CarsRep;
import com.example.laba2.core.Habitat;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;

import javafx.stage.Stage;

import java.io.IOException;

public class CarsApplication extends Application
{

    @Override
    public void start(Stage stage) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader((getClass().getResource("/com/example/laba2/cars-view.fxml")));
        CarsController carsController = new CarsController();
        carsController.setStage(stage);
        fxmlLoader.setControllerFactory(controllerClass -> carsController);
        Scene scene = new Scene(fxmlLoader.load(), 1100, 575);

        stage.setOnCloseRequest(event -> {
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
        Image icon = new Image(getClass().getResourceAsStream("/com/example/laba2/icon.png"));
        stage.getIcons().add(icon);
        stage.setTitle("AVE-AVE");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch();
    }
}