package com.example.citiesapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class AppLauncher extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = loadScene();
        stage.setScene(scene);
        stage.setTitle("Гра \"Міста\"");
        stage.getIcons().add(loadIcon());
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public Scene loadScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppLauncher.class.getResource("/views/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(this.getClass().getResource("/css/style.css").toExternalForm());

        return scene;
    }

    public Image loadIcon() {
        return new Image(this.getClass().getResource("/images/city_icon.png").toExternalForm());
    }
}