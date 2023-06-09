package com.example.laba2.console;

import com.example.laba2.Interface.ConsoleCallback;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

public class ConsoleController {
    @FXML
    private TextArea consoleTextArea;
    @FXML
    private TextField consoleTextField;

    private ConsoleCallback commandCallback;

    public ConsoleController(ConsoleCallback commandCallback) {
        this.commandCallback = commandCallback;
    }

    @FXML
    private void initialize() {
        setListeners();
    }

    private void setListeners() {
        consoleTextField.setOnKeyPressed(keyEvent ->  {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                sendCommand();
            }
        });
    }

    private void sendCommand() {
        String command = consoleTextField.getText();
        consoleTextField.clear();

        Platform.runLater(() -> {
            commandCallback.commandCallback(command);
        });
    }

    public void addMessage(String message) {
        consoleTextArea.appendText(message);
        consoleTextArea.positionCaret(consoleTextArea.getText().length());
    }
    public void clearMessage(){
        consoleTextArea.clear();
    }
}