package com.example.citiesApp.helloWindow;

import com.example.citiesApp.mainLogic.Game;
import com.example.citiesApp.mainWindow.MainController;
import com.example.citiesApp.util.AlertUtils;
import com.example.citiesApp.util.FXMLUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

import static com.example.citiesApp.util.AlertUtils.showInformationAlert;
import static com.example.citiesApp.util.FXMLUtils.*;
import static com.example.citiesApp.util.WindowUtils.getStageFromEvent;

public class HelloController {
    @FXML
    private ComboBox<String> cbLanguage;
    @FXML
    private TextField playerName;
    @FXML
    private Button startBtn;
    @FXML
    private Button rulesBtn;

    private final ObservableList<String> languages;
    private Locale locale;

    public HelloController() {
        languages = FXCollections.observableArrayList("UA", "US");
    }

    @FXML
    public void initialize(){
        cbLanguage.setItems(languages);
        cbLanguage.setCellFactory(c -> new LanguageListCell());
        cbLanguage.setOnAction(this::switchLanguage);

        playerName.setOnAction(this::startButtonHandler);
        playerName.requestFocus();

        rulesBtn.setOnAction(this::rulesButtonHandler);
        startBtn.setOnAction(this::startButtonHandler);
    }

    private void rulesButtonHandler(ActionEvent event) {
        Stage stage = getStageFromEvent(event);
        ResourceBundle bundle = FXMLUtils.loadLanguageResources(locale);
        String rulesMessage = bundle.getString("game-rules-message");
        AlertUtils.showInformationAlert(rulesMessage, stage);
    }

    private void switchLanguage(ActionEvent event) {
        locale = cbLanguage.getValue().equals("UA") ? getDefaultLocale() : Locale.US;

        FXMLLoader fxmlLoader = getLocalizedFXMLLoader("/views/hello-view.fxml", locale);
        Parent root = loadRoot(fxmlLoader);

        HelloController controller = fxmlLoader.getController();
        controller.setLocale(locale);

        Scene scene = createScene(root);
        Stage stage = getStageFromEvent(event);
        ResourceBundle bundle = FXMLUtils.loadLanguageResources(locale);
        stage.setTitle(bundle.getString("window-title"));
        stage.setScene(scene);
        stage.show();
    }

    public void startButtonHandler(ActionEvent event) {
        Stage stage = getStageFromEvent(event);
        if (playerName.getText().isBlank()) {
            ResourceBundle bundle = FXMLUtils.loadLanguageResources(locale);
            showInformationAlert(bundle.getString("player-name-empty"), stage);
        } else {
            stage.setScene(createMainWindowScene(stage));
            stage.setTitle(stage.getTitle() + " - " + playerName.getText());
            stage.show();
        }
    }

    private Scene createMainWindowScene(Stage stage) {
        FXMLLoader fxmlLoader = getLocalizedFXMLLoader("/views/main-view.fxml", locale);
        ResourceBundle bundle = FXMLUtils.loadLanguageResources(locale);
        Game game = new Game(locale, playerName.getText(), bundle.getString("ai-name"));
        MainController controller = new MainController(game, bundle, stage);
        fxmlLoader.setController(controller);
        Parent root = loadRoot(fxmlLoader);

        return createScene(root);
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
        cbLanguage.getSelectionModel().select(locale.getCountry());
        cbLanguage.setButtonCell(new LanguageListCell());
    }
}
