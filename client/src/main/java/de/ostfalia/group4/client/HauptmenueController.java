package de.ostfalia.group4.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

/**
 * Controller für das Hauptmenü
 */
public class HauptmenueController {

    /**
     * Spiel starten Button
     */
    @FXML
    private void spielStarten() {
        ViewManager.getInstance().spielladen();
    }

    /**
     * Historie Button
     */
    @FXML
    private void historieAnzeigen() {
        ViewManager.getInstance().historieladen();
    }

    /**
     * Ausloggen Button
     */
    @FXML
    private void ausloggen() {
        showAlert("Ausgeloggt!");
        ViewManager.getInstance().loginladen();
    }

    /**
     * Eine Meldung anzeigen
     *
     * @param message Die Nachricht der Meldung
     */
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}





