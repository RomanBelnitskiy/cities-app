package com.example.citiesapp.util;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class WindowUtils {
    public static Stage getStageFromEvent(ActionEvent event) {
        Node node = (Node) event.getSource();
        return (Stage) node.getScene().getWindow();
    }

    private WindowUtils() {}
}
