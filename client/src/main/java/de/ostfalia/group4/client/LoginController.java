package de.ostfalia.group4.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.net.URI;
import java.nio.charset.StandardCharsets;

public class LoginController {
// @fmxl, Referenz zur fxml Datei
    @FXML
    private TextField benutzernamefeld;

    @FXML
    private PasswordField passwortfeld;
    public static String jwt;
    @FXML
    // Action Event, wegen anklicken des Login Buttons
    private void loginbuttonanklicken(ActionEvent event) throws URISyntaxException, IOException, InterruptedException {
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
    private boolean logindatencheck(String username, String password) throws URISyntaxException, IOException, InterruptedException {
        // Encoder wird benutzt, um Sonderzeichen in Parametern abzufangen.
        String encodedUsername=URLEncoder.encode(username, StandardCharsets.UTF_8);
        String encodedPassword=URLEncoder.encode(password, StandardCharsets.UTF_8);
        String url = "http://localhost:8080/api/login?benutzername="+encodedUsername+"&passwort="+encodedPassword;

        // Erstellen einer GET-Anfrage
        HttpRequest request=HttpRequest.newBuilder(new URI(url)).GET().build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        // 200 ist der Http Code für OK und 403 steht für Forbidden
        if (response.statusCode()==200){
            jwt=response.body();
            return true;
        }
        return false;
    }

}