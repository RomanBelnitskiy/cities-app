module com.example.citiesapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.jsoup;

    exports com.example.citiesApp;
    exports com.example.citiesApp.helloWindow;
    exports com.example.citiesApp.mainWindow;
    exports com.example.citiesApp.mainLogic;
    opens com.example.citiesApp to javafx.fxml;
    opens com.example.citiesApp.helloWindow to javafx.fxml;
    opens com.example.citiesApp.mainWindow to javafx.fxml;
}