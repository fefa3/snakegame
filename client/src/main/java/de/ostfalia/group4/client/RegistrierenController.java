package de.ostfalia.group4.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegistrierenController {
    // @fmxl, Referenz zur fxml Datei
    @FXML
    private TextField benutzernamefeld;

    @FXML
    private PasswordField passwortfeld;

    @FXML
    private PasswordField passwortfeld2;

    @FXML
    public void registrierbuttonanklicken(ActionEvent actionEvent) {
        String benutzername = benutzernamefeld.getText();
        String passwort = passwortfeld.getText();
        String passwort2 = passwortfeld2.getText();
        if (!passwort.isEmpty()&&passwort.equals(passwort2)){ //!= Verneinung
            ViewManager.getInstance().hauptmenueladen();
        } else {
            new Alert(Alert.AlertType.ERROR, "Passwörter stimmmen nicht überein!").show();
            // ansonsten kommt ein Pop-up Fenster (Alert)
        }
    }

}
