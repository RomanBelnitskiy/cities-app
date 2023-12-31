package com.example.citiesApp.mainWindow;

import com.example.citiesApp.mainLogic.Game;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Optional;
import java.util.ResourceBundle;

import static com.example.citiesApp.util.AlertUtils.*;

public class MainController {
    @FXML
    private TextField cityNameField;
    @FXML
    private Button moveBtn;
    @FXML
    private Button surrenderBtn;
    @FXML
    private Button newGameBtn;
    @FXML
    private ListView<String> movesList;

    private final Game game;
    private final ResourceBundle bundle;
    private final Stage stage;

    public MainController(Game game, ResourceBundle bundle, Stage stage) {
        this.game = game;
        this.bundle = bundle;
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        movesList.setItems(game.getMoves());

        moveBtn.setOnAction(moveEventHandler());
        surrenderBtn.setOnAction(surrenderEventHandler());
        newGameBtn.setOnAction(newGameEventHandler());

        cityNameField.setOnAction(moveEventHandler());
        cityNameField.requestFocus();

        game.initNewGame(getLocal("moves-list"));
    }

    private EventHandler<ActionEvent> moveEventHandler() {
        return event -> {
            String cityName = cityNameField.getText();
            if (!game.isCityNamePresent(cityName)) {
                showErrorAlert(getLocal("city-does-not-exist"));
                return;
            }
            if (game.isCityNameUsed(cityName)) {
                showInformationAlert(String.format(getLocal("city-was-used"), cityName), stage);
            } else {
                if (!game.checkCityNameForRules(cityName)) {
                    showInformationAlert(
                            String.format(getLocal("city-name-not-valid"), cityName),
                            getLocal("city-name-rule"),
                            stage
                    );
                    return;
                }
                game.move(cityName);

                if (game.computerCanMove()) {
                    game.computerMove();
                } else {
                    showCongratulationsDialog();
                }
            }
            cityNameField.setText("");
        };
    }

    private void showCongratulationsDialog() {
        Optional<ButtonType> buttonPressed = showConfirmationAlert(
                game.getPlayerName() + getLocal("congratulation-header"),
                getLocal("congratulation-content"),
                stage, game.getLocale());
        if (buttonPressed.isPresent()) {
            if (buttonPressed.get() == ButtonType.OK) {
                game.initNewGame(getLocal("moves-list"));
            } else {
                Platform.exit();
            }
        }
    }

    private EventHandler<ActionEvent> surrenderEventHandler() {
        return event -> {
            Optional<ButtonType> buttonPressed = showConfirmationAlert(
                    getLocal("surrender-header"),
                    getLocal("surrender-content"),
                    stage, game.getLocale());
            if (buttonPressed.isPresent()) {
                if (buttonPressed.get() == ButtonType.OK) {
                    Platform.exit();
                }
            }
        };
    }

    private EventHandler<ActionEvent> newGameEventHandler() {
        return event -> {
            Optional<ButtonType> buttonPressed = showConfirmationAlert(
                    game.getPlayerName() + getLocal("new-game-header"),
                    getLocal("new-game-content"),
                    stage, game.getLocale());
            if (buttonPressed.isPresent()) {
                if (buttonPressed.get() == ButtonType.OK) {
                    game.initNewGame(getLocal("moves-list"));
                }
            }
        };
    }

    private String getLocal(String key) {
        return bundle.getString(key);
    }
}
