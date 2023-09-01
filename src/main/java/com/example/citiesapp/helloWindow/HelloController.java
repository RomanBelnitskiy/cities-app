package com.example.citiesapp.helloWindow;

import com.example.citiesapp.mainWindow.MainController;
import com.example.citiesapp.util.AlertUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.citiesapp.util.WindowUtils.getStageFromEvent;

public class HelloController {
    @FXML
    private ComboBox<String> cbLanguage;
    @FXML
    private TextField playerName;
    @FXML
    private Button startBtn;
    private ObservableList<String> languages;

    public HelloController() {
        languages = FXCollections.observableArrayList("UA", "USA");
    }

    @FXML
    public void initialize(){
        cbLanguage.setItems(languages);
        cbLanguage.setCellFactory(c -> new LanguageListCell());
        cbLanguage.setButtonCell(new LanguageListCell());
        cbLanguage.getSelectionModel().selectFirst();
        cbLanguage.setOnAction(event -> System.out.println(cbLanguage.getValue()));

        playerName.setOnAction(this::startButtonHandler);
        playerName.requestFocus();

        startBtn.setOnAction(this::startButtonHandler);
    }

    public void startButtonHandler(ActionEvent event) {
        if (playerName.getText().isBlank()) {
            AlertUtils.showInformationAlert("Ім'я не може бути пустим");
        } else {
            Stage stage = getStageFromEvent(event);
            stage.setScene(createMainWindowScene());
            stage.setTitle(stage.getTitle() + " - " + playerName.getText());
            stage.show();
        }
    }

    private Scene createMainWindowScene() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/views/main-view.fxml"));
            Parent root = fxmlLoader.load();
            MainController controller = fxmlLoader.getController();
            controller.setPlayerName(playerName.getText());
            Scene scene = new Scene(root);
            scene.getStylesheets().add(this.getClass().getResource("/css/style.css").toExternalForm());
            return scene;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
