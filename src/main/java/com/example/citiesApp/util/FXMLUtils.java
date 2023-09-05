package com.example.citiesapp.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class FXMLUtils {
    public static FXMLLoader getLocalizedFXMLLoader(String viewPath, Locale locale) {
        FXMLLoader fxmlLoader = new FXMLLoader(FXMLUtils.class.getResource(viewPath),
                loadLanguageResources(locale));
        return fxmlLoader;
    }

    public static Parent loadRoot(FXMLLoader fxmlLoader) {
        try {
            return fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Scene createScene(Parent root) {
        Scene scene = new Scene(root);
        scene.getStylesheets().add(FXMLUtils.class.getResource("/css/style.css").toExternalForm());

        return scene;
    }

    public static ResourceBundle loadLanguageResources(Locale locale) {
        return ResourceBundle.getBundle("languages.lang", locale);
    }

    public static ResourceBundle loadDefaultLanguageResources() {
        return loadLanguageResources(getDefaultLocale());
    }

    public static Locale getDefaultLocale() {
        return new Locale("uk", "UA");
    }

    public static Image loadAppIcon() {
        return new Image(FXMLUtils.class.getResource("/images/city_icon.png").toExternalForm());
    }

    private FXMLUtils() {}
}
