package hu.petrik.feketedaniel_javafxrestclientdolgozat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    public static final String BASE_URL = "https://retoolapi.dev/4cjcCP/people";
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("list-people-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 800);
        stage.setTitle("People!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}