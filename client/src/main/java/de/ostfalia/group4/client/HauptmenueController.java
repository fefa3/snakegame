package de.ostfalia.group4.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class HauptmenueController {

    @FXML
    // Spiel starten Button
    private void spielStarten(ActionEvent event) {
        ViewManager.getInstance().spielladen();
    }

    @FXML
    // Historie Button
    private void historieAnzeigen(ActionEvent event) {
        showAlert("Historie anzeigen!");
    }

    @FXML
    // Ausloggen Button
    private void ausloggen(ActionEvent event) {
        showAlert("Ausgeloggt!");
        ViewManager.getInstance().loginladen();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}





