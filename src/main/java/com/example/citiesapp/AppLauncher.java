package com.example.citiesapp;

import com.example.citiesapp.helloWindow.HelloController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

import static com.example.citiesapp.util.FXMLUtils.*;

public class AppLauncher extends Application {
    @Override
    public void start(Stage stage) {
        Scene scene = createHelloWindowScene();
        stage.setScene(scene);
        ResourceBundle bundle = loadDefaultLanguageResources();
        stage.setTitle(bundle.getString("window-title"));
        stage.getIcons().add(loadAppIcon());
        stage.setResizable(false);
        stage.show();
    }

    public Scene createHelloWindowScene() {
        Locale locale = getDefaultLocale();
        FXMLLoader fxmlLoader = getLocalizedFXMLLoader("/views/hello-view.fxml", locale);
        Parent root = loadRoot(fxmlLoader);

        HelloController controller = fxmlLoader.getController();
        controller.setLocale(locale);

        return createScene(root);
    }

    public static void main(String[] args) {
        launch();
    }
}