module com.example.citiesapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.citiesapp to javafx.fxml;
    exports com.example.citiesapp;
}