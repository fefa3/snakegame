package de.ostfalia.group4.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class HauptmenüController {

    @FXML
    // Spiel starten Button
    private void spielStarten(ActionEvent event) {
        showAlert("Spiel gestartet!");
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
        LoginController.loadWindow();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Verbindung zum Login (Fenster öffnet sich bei richtiger Eingabe)
    public static void loadWindow() {
        try {
            Stage mainstage = MainApplication.mainstage;
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("hauptmenüview.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            mainstage.setScene(scene);
            mainstage.setTitle("Hauptmenü");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}





