package com.example.citiesapp.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Optional;

public class AlertUtils {
    public static void showInformationAlert(String content, Stage owner) {
        showInformationAlert("", content, owner);
    }

    public static void showInformationAlert(String header, String content, Stage owner) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.initOwner(owner);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
        alert.showAndWait();
    }

    public static Optional<ButtonType> showConfirmationAlert(String header, String content, Stage owner) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initStyle(StageStyle.UNDECORATED);
        ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Так");
        ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Ні");
        alert.initOwner(owner);
        alert.setHeaderText(header);
        alert.setContentText(content);

        return alert.showAndWait();
    }
}
