package hu.petrik.feketedaniel_javafxrestclientdolgozat;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class EditPersonController {
    @javafx.fxml.FXML
    private TextField nameField;
    @javafx.fxml.FXML
    private Spinner<Integer> ageSpinner;
    @javafx.fxml.FXML
    private CheckBox employedCheckBox;
    @javafx.fxml.FXML
    private Button buttonSubmit;

    private Person person;

    public void setPerson(Person person) {
        this.person = person;
        nameField.setText(this.person.getName());
        ageSpinner.getValueFactory().setValue(this.person.getAge());
        if (this.person.isEmployed()){
            employedCheckBox.selectedProperty().setValue(true);
        }
        else{
            employedCheckBox.selectedProperty().setValue(false);
        }

    }

    @javafx.fxml.FXML
    public void submitClick(ActionEvent actionEvent) {
        String name = nameField.getText().trim();
        int age = ageSpinner.getValue();
        Boolean employed = employedCheckBox.selectedProperty().getValue();

        if (name.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Figyelmeztetés");
            alert.setHeaderText("Név megadása kötelező");
            alert.showAndWait();
            return;
        }

        this.person.setId(person.getId());
        this.person.setName(name);
        this.person.setAge(age);
        this.person.setEmployed(employed);
        Gson converter = new Gson();
        String json = converter.toJson(this.person);
        try {
            String url = App.BASE_URL + "/" + this.person.getId();
            Response response = RequestHandler.put(url, json);
            if (response.getResponseCode() == 200) {
                Stage stage = (Stage) this.buttonSubmit.getScene().getWindow();
                stage.close();
            } else {
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

    @FXML
    private void initialize(){
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0,200,0);
        ageSpinner.setValueFactory(valueFactory);

    }
}
