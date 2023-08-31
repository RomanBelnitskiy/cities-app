package com.example.citiesapp.mainWindow;

import com.example.citiesapp.loadNames.CitiesNamesLoader;
import com.example.citiesapp.loadNames.UkrainianCitiesNamesLoader;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.example.citiesapp.util.AlertUtils.showErrorAlert;
import static com.example.citiesapp.util.AlertUtils.showInformationAlert;

public class MainController {
    private String playerName;
    private String currentCity;
    private final ObservableList<String> moves = FXCollections.observableArrayList();
    private int movesCounter;
    private boolean firstMove;

    private Map<String, Boolean> usedCities;

    @FXML
    private TextField cityNameField;

    @FXML
    private Button moveBtn;

    @FXML
    private ListView<String> movesList;

    public MainController() {
        firstMove = true;

        CitiesNamesLoader loader = new UkrainianCitiesNamesLoader();
        setupCitiesNames(loader);
    }

    private void setupCitiesNames(CitiesNamesLoader loader) {
        usedCities = loader.getCitiesNames()
                .stream()
                .collect(Collectors.toMap(Function.identity(), el -> Boolean.valueOf(false)));
    }

    @FXML
    public void initialize() {
        moves.add("Список ходів:");
        movesList.setItems(moves);

        moveBtn.setOnAction(moveEventHandler());

        cityNameField.setOnAction(moveEventHandler());
        cityNameField.requestFocus();
    }

    private EventHandler<ActionEvent> moveEventHandler() {
        return event -> {
            String cityName = cityNameField.getText();
            Boolean result = usedCities.get(cityName);
            if (result == null) {
                showErrorAlert("Такого міста в Україні не існує");
                return;
            }
            if (result) {
                showInformationAlert(String.format("Місто \'%s\' вже було використане", cityName));
            } else {
                if (!firstMove) {
                    Character lastChar = getLastValidCharacter(currentCity);
                    if (!cityName.startsWith(lastChar.toString().toUpperCase())) {
                        showInformationAlert(
                                String.format("Назва міста \'%s\' не відповідає правилам.\n" +
                                        "Назва міста має починатися з останньої літери міста, названого попереднім гравцем", cityName)
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

        Optional<String> optName = usedCities.entrySet()
                .stream()
                .filter(entry -> entry.getKey().startsWith(c.toString().toUpperCase()) && !entry.getValue())
                .map(Map.Entry::getKey)
                .findFirst();

        if (optName.isPresent()) {
            String name = optName.get();
            movesCounter++;
            moves.add(1, movesCounter + ". Комп'ютер: " + name);
            currentCity = name;
            usedCities.put(name, true);
        }
    }
    private EventHandler<ActionEvent> surrenderEventHandler(){
        return event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Здатися");
            alert.setHeaderText("Попередження");
            alert.setContentText("Ви дійсно хочете здатися? Гру буде закінчено.");
            if (alert.showAndWait().get() == ButtonType.OK){
                System.out.println("Ви здалися, гру закінчено.");
                Platform.exit();
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
}
