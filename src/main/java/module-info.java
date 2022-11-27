module hu.petrik.feketedaniel_javafxrestclientdolgozat {
    requires javafx.controls;
    requires javafx.fxml;


    opens hu.petrik.feketedaniel_javafxrestclientdolgozat to javafx.fxml;
    exports hu.petrik.feketedaniel_javafxrestclientdolgozat;
}