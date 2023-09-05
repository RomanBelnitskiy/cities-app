package com.example.citiesApp.mainLogic;

import com.example.citiesApp.loadNames.CitiesNamesLoader;
import com.example.citiesApp.loadNames.UkrainianCitiesEnglishNamesLoader;
import com.example.citiesApp.loadNames.UkrainianCitiesNamesLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Game {
    private final Locale locale;
    private final CitiesNamesLoader citiesLoader;
    private final String playerName;
    private final String computerName;
    private final ObservableList<String> moves;

    private String currentCity;
    private int movesCounter;
    private boolean firstMove;
    private Map<String, Boolean> usedCities;

    public Game(Locale locale, String playerName, String computerName) {
        this.locale = locale;
        this.playerName = playerName;
        this.computerName = computerName;

        citiesLoader = locale.equals(Locale.US)
                ? new UkrainianCitiesEnglishNamesLoader()
                : new UkrainianCitiesNamesLoader();

        moves = FXCollections.observableArrayList();
    }

    public void initNewGame(String startMessage) {
        firstMove = true;
        currentCity = "";

        setupCitiesNames();

        moves.clear();
        moves.add(startMessage);
    }

    private void setupCitiesNames() {
        usedCities = citiesLoader.getCitiesNames()
                .stream()
                .collect(Collectors.toMap(Function.identity(), el -> Boolean.FALSE));
    }

    public void move(String cityName) {
        usedCities.put(cityName, true);
        movesCounter++;
        moves.add(1, movesCounter + ". " + playerName + ": " + cityName);
        currentCity = cityName;
    }

    public boolean isCityNamePresent(String cityName) {
        return usedCities.containsKey(cityName);
    }

    public boolean isCityNameUsed(String cityName) {
        return usedCities.get(cityName);
    }

    public boolean checkCityNameForRules(String cityName) {
        if (firstMove) {
            firstMove = false;
            return true;
        }

        Optional<Character> optLastChar = getLastValidCharacter(currentCity);
        if (optLastChar.isEmpty()) {
            return false;
        }
        Character lastChar = optLastChar.get();
        return cityName.startsWith(lastChar.toString().toUpperCase());
    }

    public void computerMove() {
        Character lastChar = getLastValidCharacter(currentCity).orElseThrow();
        String cityName = usedCities.entrySet()
                .stream()
                .filter(entry -> entry.getKey().startsWith(lastChar.toString().toUpperCase()) && !entry.getValue())
                .map(Map.Entry::getKey)
                .findFirst().orElseThrow();

        movesCounter++;
        moves.add(1, movesCounter + computerName + cityName);
        currentCity = cityName;
        usedCities.put(cityName, true);
    }

    public boolean computerCanMove (){
        Optional<Character> optLastChar = getLastValidCharacter(currentCity);
        if (optLastChar.isEmpty()) {
            return false;
        }
        Character lastChar = optLastChar.get();

        for (Map.Entry<String, Boolean> entry : usedCities.entrySet()){
            String cityValue = entry.getKey();
            Boolean isCityUsed = entry.getValue();

            if (!isCityUsed && cityValue.startsWith(lastChar.toString().toUpperCase())){
                return true;
            }
        }

        return false;
    }

    private Optional<Character> getLastValidCharacter(String cityName) {
        for (int i = cityName.length() - 1; i > 0; i--) {
            if (isCharacterValid(cityName.charAt(i))) {
                return Optional.of(cityName.charAt(i));
            }
        }

        return Optional.empty();
    }

    private boolean isCharacterValid(char c) {
        return c != 'и' && c != 'ь' && c != 'й' && c != 'ї' && c != 'ц' && c != '\'';
    }

    public String getPlayerName() {
        return playerName;
    }

    public ObservableList<String> getMoves() {
        return moves;
    }

    public Locale getLocale() {
        return locale;
    }
}
