package com.example.citiesapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class StartupController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}