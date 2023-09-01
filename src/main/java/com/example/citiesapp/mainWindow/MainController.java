package com.example.citiesapp.mainWindow;

import com.example.citiesapp.loadNames.CitiesNamesLoader;
import com.example.citiesapp.loadNames.UkrainianCitiesNamesLoader;
import com.example.citiesapp.util.AlertUtils;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.example.citiesapp.util.AlertUtils.*;
import static com.example.citiesapp.util.WindowUtils.*;

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

    private final ObservableList<String> moves = FXCollections.observableArrayList();
    private String playerName;
    private String currentCity;
    private CitiesNamesLoader cityLoader;
    private int movesCounter;
    private boolean firstMove;
    private Map<String, Boolean> usedCities;
    private ResourceBundle bundle;
    private Stage stage;


    public MainController() {
        cityLoader = new UkrainianCitiesNamesLoader();
    }

    public void initNewGame() {
        firstMove = true;
        currentCity = "";

        setupCitiesNames(cityLoader);

        moves.clear();
        moves.add(getLocal("moves-list"));
    }

    private void setupCitiesNames(CitiesNamesLoader loader) {
        usedCities = loader.getCitiesNames()
                .stream()
                .collect(Collectors.toMap(Function.identity(), el -> Boolean.valueOf(false)));
    }

    @FXML
    public void initialize() {
        movesList.setItems(moves);

        moveBtn.setOnAction(moveEventHandler());
        surrenderBtn.setOnAction(surrenderEventHandler());
        newGameBtn.setOnAction(newGameEventHandler());

        cityNameField.setOnAction(moveEventHandler());
        cityNameField.requestFocus();
    }

    private EventHandler<ActionEvent> moveEventHandler() {
        return event -> {
            String cityName = cityNameField.getText();
            Boolean result = usedCities.get(cityName);
            if (result == null) {
                showErrorAlert(getLocal("city-doesnt-exist"));
                return;
            }
            if (result) {
                showInformationAlert(String.format(getLocal("city-was-used"), cityName), stage);
            } else {
                if (!firstMove) {
                    Character lastChar = getLastValidCharacter(currentCity);
                    if (!cityName.startsWith(lastChar.toString().toUpperCase())) {
                        showInformationAlert(
                                String.format(getLocal("city-name-not-valid"), cityName),
                                getLocal("city-name-rule"),
                                stage
                        );
                        return;
                    }
                } else {
                    firstMove = false;
                }
                usedCities.put(cityName, true);
                movesCounter++;
                moves.add(1, movesCounter + ". " + playerName + ": " + cityName);

                cityNameField.setText("");
                currentCity = cityName;
                moveAI();
            }
        };
    }

    private void moveAI() {
        Character c = getLastValidCharacter(currentCity);

        boolean computerCanMove = computerCanMove(c);

        if (computerCanMove){
            Optional<String> optName = usedCities.entrySet()
                    .stream()
                    .filter(entry -> entry.getKey().startsWith(c.toString().toUpperCase()) && !entry.getValue())
                    .map(Map.Entry::getKey)
                    .findFirst();

            if (optName.isPresent()) {
                String name = optName.get();
                movesCounter++;
                moves.add(1, movesCounter + getLocal("ai-name") + name);
                currentCity = name;
                usedCities.put(name, true);
        }
        } else {
            showCongratulationsDialog();
        }
    }
    private boolean computerCanMove (Character lastChar){
        for (Map.Entry<String, Boolean> entry : usedCities.entrySet()){
            String cityValue = entry.getKey();
            Boolean isCityUsed = entry.getValue();

            if (!isCityUsed && cityValue.startsWith(lastChar.toString().toUpperCase())){
                return true;
            }
        }
        return false;
    }
    private void showCongratulationsDialog() {
        Optional<ButtonType> buttonPressed = showConfirmationAlert(
                playerName + getLocal("congratulation-header"),
                getLocal("congratulation-content"),
                stage);

        if (buttonPressed.get() == ButtonType.OK) {
            initNewGame();
        } else {
            Platform.exit();
        }
    }
    private EventHandler<ActionEvent> surrenderEventHandler(){
        return event -> {
            Optional<ButtonType> buttonPressed = showConfirmationAlert(
                    getLocal("surrender-header"),
                    getLocal("surrender-content"),
                    stage);
            if (buttonPressed.get() == ButtonType.OK){
                Platform.exit();
            }
        };
    }

    private EventHandler<ActionEvent> newGameEventHandler() {
        return event -> {
            Optional<ButtonType> buttonPressed = showConfirmationAlert(
                    playerName + getLocal("new-game-header"),
                    getLocal("new-game-content"),
                    stage);
            if (buttonPressed.get() == ButtonType.OK){
                initNewGame();
            }
        };
    }

    private Character getLastValidCharacter(String cityName) {
        for (int i = cityName.length() - 1; i > 0; i--) {
            if (isCharacterValid(cityName.charAt(i))) {
                return cityName.charAt(i);
            }
        }

        return null;
    }

    private boolean isCharacterValid(char c) {
        return c != 'и' && c != 'ь' && c != 'й' && c != 'ї' && c != 'ц' && c != '\'';
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    private String getLocal(String key) {
        return bundle.getString(key);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
