package hu.petrik.feketedaniel_javafxrestclientdolgozat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class CreatePersonController {

    @javafx.fxml.FXML
    private TextField nameField;
    @javafx.fxml.FXML
    private Spinner<Integer> ageSpinner;
    @javafx.fxml.FXML
    private Button buttonSubmit;
    @javafx.fxml.FXML
    private CheckBox employedCheckBox;

    @FXML
    private void initialize(){
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0,200,30);
        ageSpinner.setValueFactory(valueFactory);
    }

    @javafx.fxml.FXML
    public void submitClick(ActionEvent actionEvent) {
        String name = nameField.getText().trim();
        int age = ageSpinner.getValue();
        Boolean employed = employedCheckBox.isSelected();
        if (name.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Figyelmeztetés");
            alert.setHeaderText("Név megadása kötelező");
            alert.showAndWait();
            return;
        }
        Person newPerson = new Person(0, name, age, employed);
        Gson converter = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String json = converter.toJson(newPerson);
        try {
            Response response = RequestHandler.post(App.BASE_URL, json);
            if (response.getResponseCode() == 201){
                nameField.setText("");
                ageSpinner.getValueFactory().setValue(30);
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Hiba");
                alert.setHeaderText("Hiba történt a felvétel során");
                alert.setContentText(response.getContent());
                alert.showAndWait();
            }
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hiba");
            alert.setHeaderText("Nem sikerült kapcsolódni a szerverhez");
            alert.showAndWait();
        }

    }

}
