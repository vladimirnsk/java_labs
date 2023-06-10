package com.example.laba2.core;

import com.example.laba2.CarsApplication;
import com.example.laba2.Interface.ClientCallback;
import com.example.laba2.Interface.ConsoleCallback;
import com.example.laba2.Interface.ICarsController;
import com.example.laba2.Interface.ICarsPresent;
import com.example.laba2.console.ConsoleController;
import com.example.laba2.enteties.Cars;
import com.example.laba2.enteties.CarsDTO;
import com.example.laba2.enteties.CarsInGeneral;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class CarsController implements ICarsController, ConsoleCallback, ClientCallback {
    private final ICarsPresent presenter = new CarsParser(this);
    private Timer timer;
    private Stage stage;
    private Client client = new Client(this);
    @FXML
    private Pane pane;
    @FXML
    private Button startButton;
    @FXML
    private Button stopButton;
    @FXML
    private Button probkiEst;
    @FXML
    private CheckBox showInfoCheckBox;
    @FXML
    private RadioButton showTimeRadio;
    @FXML
    private RadioButton hideTimeRadio;
    @FXML
    private TextField CarsIntervalTextField;
    @FXML
    private TextField TrucksIntervalTextField;
    @FXML
    private Slider CarsProbabilitySlider;
    @FXML
    private Slider TrucksProbabilitySlider;
    @FXML
    private MenuItem startMenuItem;
    @FXML
    private MenuItem stopMenuItem;
    @FXML
    private TextField carsLifeTimeTextField;
    @FXML
    private TextField trucksLifeTimeTextField;
    @FXML
    private CheckBox carsStart;
    @FXML
    private CheckBox trucksStart;
    @FXML
    private Button consoleButton;
    @FXML
    private Button save;
    @FXML
    private Button load;

    @FXML
    private ComboBox<Integer> carsBox;
    @FXML
    private ComboBox<Integer> trucksBox;
    @FXML
    private ListView clients;
    private ConsoleController consoleController = new ConsoleController(this);
    private final ObservableList<Integer> prikols = FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    private final ToggleGroup radioGroup = new ToggleGroup();
    private final Text simulationTimeText = new Text();

    @FXML
    private void initialize() {
        client.setCarsy(presenter.getCars());
        client.start();
        loadSimulationOptions();
        setupViews();
        setListeners();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void setupViews() {
        simulationTimeText.setFont(Font.font("arial black", 24));
        simulationTimeText.setFill(Color.MEDIUMPURPLE);
        simulationTimeText.setX(50);
        simulationTimeText.setY(50);

        stopButton.setDisable(true);

        showTimeRadio.setToggleGroup(radioGroup);
        hideTimeRadio.setToggleGroup(radioGroup);
        showTimeRadio.fire();

        carsBox.setItems(prikols);
        trucksBox.setItems(prikols);
    }


    private void setListeners() {

        startButton.setOnMouseClicked(mouseEvent -> startSimulation());

        stopButton.setOnMouseClicked(mouseEvent -> stopSimulation());

        radioGroup.selectedToggleProperty().addListener((changed, oldValue, newValue) ->
        {
            RadioButton selectedRadio = (RadioButton) newValue;
            simulationTimeText.setVisible(selectedRadio.getId().equals("showTimeRadio"));
        });

        startMenuItem.setOnAction(actionEvent -> startSimulation());

        stopMenuItem.setOnAction(actionEvent -> stopSimulation());

        consoleButton.setOnAction(actionEvent -> openConsole());

        probkiEst.setOnAction(actionEvent -> showProbki());

        load.setOnAction(actionEvent -> loadObjects());

        save.setOnAction(actionEvent -> saveObjects());

        CarsIntervalTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!presenter.checkInput(newValue)) {
                CarsIntervalTextField.setText(oldValue);
            }
        });

        TrucksIntervalTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!presenter.checkInput(newValue)) {
                TrucksIntervalTextField.setText(oldValue);
            }
        });

        carsLifeTimeTextField.textProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (!presenter.checkInput(newValue)) {
                carsLifeTimeTextField.setText(oldValue);
            }
        }));

        trucksLifeTimeTextField.textProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (!presenter.checkInput(newValue)) {
                trucksLifeTimeTextField.setText(oldValue);
            }
        }));

        carsStart.selectedProperty().addListener((o, oldValue, newValue) -> {
            if (newValue) {
                presenter.startCarsAI();
            } else {
                presenter.stopCarsAI();
            }
        });
        trucksStart.selectedProperty().addListener((o, oldValue, newValue) -> {
            if (newValue) {
                presenter.startTrucksAI();
            } else {
                presenter.stopTrucksAI();
            }
        });
        carsBox.setOnAction(actionEvent -> {
            int selectedPriority = carsBox.getSelectionModel().getSelectedItem();
            presenter.carsBoxPriority(selectedPriority);
        });
        trucksBox.setOnAction(actionEvent -> {
            int selectedPriority = trucksBox.getSelectionModel().getSelectedItem();
            presenter.trucksBoxPriority(selectedPriority);
        });

    }

    private void showProbki() {
        if (!canStopTimer()) {
            return;
        }
        stopTimer();
        presenter.showProbki();
    }

    @FXML
    private void onKeyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case B -> startSimulation();
            case E -> stopSimulation();
            case T -> {
                if (simulationTimeText.isVisible()) {
                    hideTimeRadio.fire();
                } else {
                    showTimeRadio.fire();
                }
            }
        }
    }

    private void openConsole() {
        try {
            FXMLLoader loader = new FXMLLoader(CarsApplication.class.getResource("console.fxml"));
            loader.setController(consoleController);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage dialog = new Stage();
            dialog.setScene(scene);
            dialog.initModality(Modality.NONE);
            dialog.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void commandCallback(String command) {
        if (command.contains("stop_ai")) {
            presenter.stopCarsAI();
            presenter.stopTrucksAI();
            consoleController.addMessage("Остановлено интеллектуальное поведение объектов" + "\n");
        } else if (command.contains("start_ai")) {
            presenter.startCarsAI();
            presenter.startTrucksAI();
            consoleController.addMessage("Продолжено интеллектуальное поведение объектов" + "\n");
        } else if (command.contains("clear")) {
            consoleController.clearMessage();
        } else {
            consoleController.addMessage("Неизвестная команда: " + command + "\n");
            consoleController.addMessage("Список команд:" + "\n" + "stop_ai - Остановить интеллектуальное поведение объектов" + "\n" + "start_ai - Продолжить интеллектуальное поведение объектов" + "\n");
        }
    }

    public void loadSimulationOptions() {
        try {
            Properties simulationProps = new Properties();
            simulationProps.load(getClass().getResourceAsStream("/com/example/laba2/config.properties"));
            if (simulationProps.isEmpty()) return;
            System.out.println(simulationProps);
            String carsIntervalTextField = simulationProps.getProperty("carsIntervalTextField");
            String trucksIntervalTextField = simulationProps.getProperty("trucksIntervalTextField");
            String carsProbabilitySlider = simulationProps.getProperty("carsProbabilitySlider");
            String trucksProbabilitySlider = simulationProps.getProperty("trucksProbabilitySlider");
            String CarsLifeTimeTextField = simulationProps.getProperty("CarsLifeTimeTextField");
            String TrucksLifeTimeTextField = simulationProps.getProperty("TrucksLifeTimeTextField");

            CarsIntervalTextField.setText(carsIntervalTextField);
            TrucksIntervalTextField.setText(trucksIntervalTextField);
            CarsProbabilitySlider.setValue(Double.parseDouble(carsProbabilitySlider));
            TrucksProbabilitySlider.setValue(Double.parseDouble(trucksProbabilitySlider));
            carsLifeTimeTextField.setText(CarsLifeTimeTextField);
            trucksLifeTimeTextField.setText(TrucksLifeTimeTextField);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveSimulationOptions() {
        try {
            Properties simulationProps = new Properties();
            simulationProps.load(getClass().getResourceAsStream("/com/example/laba2/config.properties"));
            System.out.println(CarsIntervalTextField);
            System.out.println(TrucksIntervalTextField);
            System.out.println(CarsProbabilitySlider);
            System.out.println(TrucksProbabilitySlider);
            System.out.println(carsLifeTimeTextField);
            simulationProps.setProperty("carsIntervalTextField", CarsIntervalTextField.getText());
            simulationProps.setProperty("trucksIntervalTextField", TrucksIntervalTextField.getText());
            simulationProps.setProperty("carsProbabilitySlider", String.valueOf(CarsProbabilitySlider.getValue()));
            simulationProps.setProperty("trucksProbabilitySlider", String.valueOf(TrucksProbabilitySlider.getValue()));
            simulationProps.setProperty("CarsLifeTimeTextField", String.valueOf(carsLifeTimeTextField.getText()));
            simulationProps.setProperty("TrucksLifeTimeTextField", String.valueOf(trucksLifeTimeTextField.getText()));
            FileWriter out = new FileWriter(getClass().getResource("/com/example/laba2/config.properties").getFile());
            simulationProps.store(out, "SimulationOptions");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadObjects() {
        presenter.stopCarsAI();
        presenter.stopTrucksAI();
        stopTimer();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Загрузить");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files", "*.*"));
        File file = fileChooser.showOpenDialog(stage);
        ArrayList<CarsInGeneral> loadedCars = new ArrayList<>();
        if (file != null) {
            try {
                ObjectInputStream objInpStr = new ObjectInputStream(new FileInputStream(file));
                CarsInGeneral readCars;
                while ((readCars = (CarsInGeneral) objInpStr.readObject()) != null) {
                    loadedCars.add(readCars);
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Exception");
            }
            onStopSimulation();
            presenter.setCars(loadedCars);
            update(0, 0);
        }
        System.out.println(loadedCars);

    }

    private void saveObjects() {
        stopTimer();
        presenter.stopCarsAI();
        presenter.stopTrucksAI();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Сохраняшка");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files", "*.*"));
        File file = fileChooser.showSaveDialog(stage);
        ArrayList<CarsInGeneral> cars = presenter.getCars();
        if (file != null) {
            try {
                ObjectOutputStream objOutpStream = new ObjectOutputStream(new FileOutputStream(file));
                for (CarsInGeneral car : cars) {
                    objOutpStream.writeObject(car);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        continueTimer();
    }

    private void startSimulation() {
        if (!canStartTimer()) return;
        boolean hui = presenter.setParams(
                CarsIntervalTextField.getText(),
                TrucksIntervalTextField.getText(),
                CarsProbabilitySlider.getValue(),
                TrucksProbabilitySlider.getValue(),
                carsLifeTimeTextField.getText(),
                trucksLifeTimeTextField.getText());

        if (hui) {
            startButton.setDisable(true);
            stopButton.setDisable(false);
            startTimer();

        }

    }

    public void stopSimulation() {
        if (!canStopTimer()) return;
        stopTimer();
        presenter.showStatsDialog();
        pane.getChildren().clear();
    }

    private void onStopSimulation() {
        startButton.setDisable(false);
        stopButton.setDisable(true);

        showTimeRadio.fire();
        presenter.resetAll();
    }

    @Override
    public void updateCars(ArrayList<CarsInGeneral> carsInGenerals) {
        pane.getChildren().clear();
        for (CarsInGeneral r : carsInGenerals) {
            ImageView image = new ImageView(r.getImage());
            image.setX(r.getX());
            image.setY(r.getY());
            image.setFitWidth(r.getSize());
            image.setFitHeight(r.getSize());
            pane.getChildren().add(image);
        }
    }

    @Override
    public void updateStats(int simulationTime) {
        simulationTimeText.setText("Время симуляции: " + simulationTime);
        pane.getChildren().add(simulationTimeText);
    }

    @Override
    public void showStats(int simulationTime, int CarsCount, int TrucksCount) {
        if (
                showInfoCheckBox.isSelected()) {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Информация");
            dialog.setHeaderText("Статистика");
            dialog.setContentText("Время симуляции: " + simulationTime + "\n" + "Кол-во машин: " + CarsCount + "\n" + "Кол-во грузовиков: " + TrucksCount);
            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);
            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == okButton) {
                onStopSimulation();
            } else {
                continueTimer();
            }
        } else {
            onStopSimulation();
        }
    }

    @Override
    public void Error(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Cars");
        alert.setHeaderText("Внимание!");
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }

    @Override
    public void update(double elapsed, double frameTime) {
        presenter.update(elapsed);
    }

    @Override
    public void ShowAllCars(ArrayList<CarsInGeneral> allCars) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Список");
        dialog.setHeaderText("Список всех объектов");
        ListView<CarsInGeneral> listView = new ListView<>(FXCollections.observableArrayList(allCars));
        dialog.getDialogPane().setContent(listView);

        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton);

        dialog.showAndWait();

        continueTimer();
    }

    @Override
    public void setCarsLifeTimeTextField(String s) {
        carsLifeTimeTextField.setText(s);
        saveSimulationOptions();
    }

    @Override
    public void setTrucksLifeTimeTextField(String s) {
        trucksLifeTimeTextField.setText(s);
    }

    @Override
    public void setCarsIntervalTextField(String s) {
        CarsIntervalTextField.setText(s);
    }

    @Override
    public void setTrucksIntervalTextField(String s) {
        TrucksIntervalTextField.setText(s);
    }

    private void startTimer() {
        if (timer != null)
            return;
        timer = new Timer();
        timer.schedule(new TimerTask(){
            private boolean firstRun = true;
            private long startTime;
            private long lastTime = 0;
            private final int delta = 1;

            @Override
            public void run()
            {
                if (firstRun){
                    startTime = System.currentTimeMillis();
                    lastTime = startTime;
                    firstRun = false;
                }
                long currentTime = System.currentTimeMillis();
                double elapsed = (currentTime - startTime) / 1000.0 + delta;
                double frameTime = (lastTime - startTime) / 1000.0;
                Platform.runLater(() ->update(elapsed, frameTime));
                lastTime = currentTime;
        }
        },0 , 1000/120);
    }

    private void continueTimer() {
        if (timer != null) return;
        timer = new Timer();
        timer.schedule(new Update(this, presenter.getSimulationTime()), 0, 1000 / 120);
    }

    private void stopTimer() {
        if (timer == null) return;
        timer.cancel();
        timer = null;
    }

    private boolean canStartTimer() {
        return timer == null;
    }

    private boolean canStopTimer() {
        return timer != null;
    }

    @Override
    public ArrayList<CarsInGeneral> getCars() {
        ArrayList<CarsInGeneral> allCars = presenter.getCars();
        ArrayList<CarsInGeneral> sortedCars = new ArrayList<>();
        for (CarsInGeneral c : allCars) {
            if (c instanceof Cars) {
                sortedCars.add(c);
            }
        }
        return sortedCars;
    }

    @Override
    public void setClients(String[] clientsNames) {
        Platform.runLater(() -> {
            clients.setItems(FXCollections.observableArrayList(clientsNames));
            clients.setOnMouseClicked(e -> {
                String name = (String) clients.getSelectionModel().getSelectedItem();
                System.out.println("Отправлено к : " + name);
                ArrayList<CarsInGeneral> allCars = presenter.getCars();
                ArrayList<CarsDTO> sortedCars = (ArrayList<CarsDTO>) allCars.stream().filter(x -> x instanceof Cars).map(x -> ((Cars) x).toCarsDTO()).collect(Collectors.toList());
/*                allCars.stream().filter(x -> x instanceof Cars).toList();
                for (CarsInGeneral c : allCars) {
                    if(c instanceof Cars){
                        sortedCars.add(c);
                    }
                }*/
                client.carsExchange(name, sortedCars);
            });
        });
    }

    @Override
    public void setCars(ArrayList<CarsDTO> sendOverCars) {
        CarsRep.getInstance().setNewCars((ArrayList<Cars>) sendOverCars.stream().map(x -> new Cars(x)).collect(Collectors.toList()));
    }
}