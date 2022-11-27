module hu.petrik.feketedaniel_javafxrestclientdolgozat {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    opens hu.petrik.feketedaniel_javafxrestclientdolgozat to javafx.fxml, com.google.gson;
    exports hu.petrik.feketedaniel_javafxrestclientdolgozat;
}