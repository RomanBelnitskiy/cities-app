module com.example.citiesapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.jsoup;

    exports com.example.citiesapp;
    exports com.example.citiesapp.helloWindow;
    exports com.example.citiesapp.mainWindow;
    opens com.example.citiesapp to javafx.fxml;
    opens com.example.citiesapp.helloWindow to javafx.fxml;
    opens com.example.citiesapp.mainWindow to javafx.fxml;
}