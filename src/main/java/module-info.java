module com.example.lab1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.laba2 to javafx.fxml;
    exports com.example.laba2;
    exports com.example.laba2.core;
    opens com.example.laba2.core to javafx.fxml;
    exports com.example.laba2.Interface;
    opens com.example.laba2.Interface to javafx.fxml;
    exports com.example.laba2.enteties;
    opens com.example.laba2.enteties to javafx.fxml;

    exports com.example.laba2.console;
    opens com.example.laba2.console to javafx.fxml;
}