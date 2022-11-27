package hu.petrik.feketedaniel_javafxrestclientdolgozat;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Optional;

public class ListPeopleController {

    @FXML
    private Button createButton;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;
    @FXML
    private TableView<Person> peopletable;
    @FXML
    private TableColumn<Person, Integer> idCol;
    @FXML
    private TableColumn<Person, String > nameCol;
    @FXML
    private TableColumn<Person, Integer> ageCol;
    @FXML
    private TableColumn<Person, Boolean> employedCol;

    @FXML
    public void createClick(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("create-person-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 640, 480);
            Stage stage = new Stage();
            stage.setTitle("Create Person");
            stage.setScene(scene);
            stage.setOnCloseRequest(event -> {
                try {
                    loadPeopleFromServer();
                } catch (IOException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Hiba");
                    alert.setHeaderText("Nem sikerült kapcsolódni a szerverhez");
                    alert.showAndWait();
                }
            });
            stage.show();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hiba");
            alert.setHeaderText("Hiba történt az űrlap betöltése során");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    public void editClick(ActionEvent actionEvent) {
        Person selected = peopletable.getSelectionModel().getSelectedItem();
        if (selected == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Figyelmeztetés");
            alert.setHeaderText("Módosításhoz válasszon ki egy elemet");
            alert.showAndWait();
            return;
        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("edit-person-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 640, 480);
            Stage stage = new Stage();
            stage.setTitle("Edit Person");
            stage.setScene(scene);
            EditPersonController controller = fxmlLoader.getController();
            controller.setPerson(selected);
            stage.show();
            stage.setOnCloseRequest(event -> {
                try {
                    loadPeopleFromServer();
                } catch (IOException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Hiba");
                    alert.setHeaderText("Nem sikerült kapcsolódni a szerverhez");
                    alert.showAndWait();
                }

            });

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hiba");
            alert.setHeaderText("Hiba történt az űrlap betöltése során");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    public void deleteClick(ActionEvent actionEvent) {
        Person selected = peopletable.getSelectionModel().getSelectedItem();
        if (selected == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Figyelmeztetés");
            alert.setHeaderText("Törléshez válasszon ki egy elemet");
            alert.showAndWait();
            return;
        }
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Biztos?");
        confirmation.setHeaderText("Biztos, hogy tötölni szertné az alábbi rekordot: "+selected.getName());
        Optional<ButtonType> optionalButtonType = confirmation.showAndWait();
        if (((Optional<?>) optionalButtonType).isPresent() && optionalButtonType.get().equals(ButtonType.OK)){
            String url = App.BASE_URL + "/" + selected.getId();
            try{
                RequestHandler.delete(url);
                loadPeopleFromServer();
            }catch (IOException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Hiba");
                alert.setHeaderText("Nem sikerült kapcsolódni a szerverhez");
                alert.showAndWait();
            }

        }
    }

    @FXML
    private void initialize(){
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));
        employedCol.setCellValueFactory(new PropertyValueFactory<>("employed"));
        Platform.runLater(() ->{
            try {
                loadPeopleFromServer();
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Hiba");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
                Platform.exit();
            }
        });
    }
    private void loadPeopleFromServer() throws IOException {
        Response response =  RequestHandler.get(App.BASE_URL);
        String content = response.getContent();
        Gson converter = new Gson();
        Person[] people = converter.fromJson(content, Person[].class);
        peopletable.getItems().clear();
        for (Person person: people){
            peopletable.getItems().add(person);
        }
    }
}
