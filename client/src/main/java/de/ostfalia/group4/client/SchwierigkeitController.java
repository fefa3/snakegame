package de.ostfalia.group4.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class SchwierigkeitController {

    @FXML
    // Spiel startet auf leichter Schwierigkeit
    private void levelleicht(ActionEvent event) {
        showAlert("Spiel startet auf leichter Schwierigkeit!");
    }

    @FXML
    // Spiel startet auf mittlerer Schwierigkeit
    private void levelmittel(ActionEvent event) {
        showAlert("Spiel startet auf mittlerer Schwierigkeit!");
    }

    @FXML
    // Spiel startet auf schwerer Schwierigkeit
    private void levelschwer(ActionEvent event) {
        showAlert("Spiel startet auf schwerer Schwierigkeit!");
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Funktion zur Anzeige eines Fensters
    public static void loadWindow() {
        try {
            Stage mainstage = MainApplication.mainstage;
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("schwierigkeitview.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            mainstage.setScene(scene);
            mainstage.setTitle("Schwierigkeit");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}





