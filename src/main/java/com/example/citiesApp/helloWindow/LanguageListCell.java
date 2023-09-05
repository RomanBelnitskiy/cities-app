package com.example.citiesApp.helloWindow;

import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class LanguageListCell extends ListCell<String> {
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        setGraphic(null);
        setText(null);
        if (item != null) {
            if (item.equals("UA")) {
                ImageView imageView = new ImageView(Objects.requireNonNull(this.getClass().getResource("/images/ukraine.png")).toExternalForm());
                imageView.setFitWidth(16);
                imageView.setFitHeight(16);
                setGraphic(imageView);
                setText("UA");
            } else if (item.equals("US")) {
                ImageView imageView = new ImageView(Objects.requireNonNull(this.getClass().getResource("/images/usa.png")).toExternalForm());
                imageView.setFitWidth(16);
                imageView.setFitHeight(16);
                setGraphic(imageView);
                setText("US");
            }
        }
    }
}
