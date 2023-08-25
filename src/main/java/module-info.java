module com.example.citiesapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.example.citiesapp to javafx.fxml;
    exports com.example.citiesapp;
    exports com.example.citiesapp.controller;
    opens com.example.citiesapp.controller to javafx.fxml;
}