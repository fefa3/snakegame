package de.ostfalia.group4.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
// @fmxl, Referenz zur fxml Datei
    @FXML
    private TextField benutzernamefeld;

    @FXML
    private PasswordField passwortfeld;

    @FXML
    // Action Event, wegen anklicken des Login Buttons
    private void loginbuttonanklicken(ActionEvent event) {
        String benutzername = benutzernamefeld.getText();
        String passwort = passwortfeld.getText();

        if (logindatencheck(benutzername, passwort)) {
            ViewManager.getInstance().hauptmenueladen();
            // wenn login erfolgreich
        } else {
            new Alert(Alert.AlertType.ERROR, "Password or Username invalid").show();
            // ansonsten kommt ein Pop-up Fenster (Alert)
        }
    }

    @FXML
    // Action Event, wegen anklicken auf Registrier-Button
    private void registrierbuttonanklicken(ActionEvent event) {
        System.out.println("Registration button clicked");
        ViewManager.getInstance().registrierenladen();
    }

    // Abfrage, ob Daten korrekt und beispielhaft was definiert
    private boolean logindatencheck(String username, String password) {
        if (username.equals("fenya") && password.equals("1234")){
            return true;
        }
        return false;
    }
}