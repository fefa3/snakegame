package de.ostfalia.group4.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
// @fmxl, Referenz zur fxml Datei
    @FXML
    private TextField usernamefeld;

    @FXML
    private PasswordField passwordfeld;

    @FXML
    // Action Event, wegen anklicken des Login Buttons
    private void loginbuttonanklicken(ActionEvent event) {
        String username = usernamefeld.getText();
        String password = passwordfeld.getText();

        if (logindatencheck(username, password)) {
            HauptmenüController.loadWindow();
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
    }

    // Abfrage, ob Daten korrekt und beispielhaft was definiert
    private boolean logindatencheck(String username, String password) {
        if (username.equals("fenya") && password.equals("1234")){
            return true;
        }
        return false;
    }
}