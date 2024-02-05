package de.ostfalia.group4.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Controller für Registrieren-View
 */
public class RegistrierenController {
    /**
     * Eingabefeld für Benutzername
     */
    // @fmxl, Referenz zur fxml Datei
    @FXML
    private TextField benutzernamefeld;

    /**
     * Eingabefeld für Passwort
     */
    @FXML
    private PasswordField passwortfeld;

    /**
     * Eingabefeld für Passwort-Wiederholung
     */
    @FXML
    private PasswordField passwortfeld2;

    /**
     * Auf Registrieren-Button klicken
     *
     * @throws IOException Fehler beim Lesen der Antwort vom Server
     * @throws InterruptedException Fehler bei der Anfrage an den Server
     */
    @FXML
    public void registrierbuttonanklicken() throws IOException, InterruptedException {
        String benutzername = benutzernamefeld.getText();
        String passwort = passwortfeld.getText();
        String passwort2 = passwortfeld2.getText();
        if (passwort.equals(passwort2)){ //!= Verneinung
            String url = "http://localhost:8080/api/register";
            // Es wird ein Objekt für den JSON String erstellt.
            ObjectNode jsonNode = JsonNodeFactory.instance.objectNode();
            jsonNode.put("name", benutzername);
            jsonNode.put("password", passwort);

            // JSON Objekt wird zum JSON String konvertiert
            ObjectMapper objectMapper = new ObjectMapper();
            String requestBody = objectMapper.writeValueAsString(jsonNode);

            // Erstellen einer POST-Anfrage
            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(HttpRequest.newBuilder(URI.create(url))
                            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                            .header("Content-Type", "application/json") // Set the content type
                            .build(), HttpResponse.BodyHandlers.ofString());

            //Weiteres Verfahren bei verschiedenen Http Status Codes definiert
            //201 steht für CREATED, 400 für BAD REQUEST, wenn kein Passwort eingegeben wird, 409 steht für CONFLICT, wenn der Benutzername vergeben ist
            if (response.statusCode()==201){
                LoginController.jwt = response.body(); // jwt wird gespeichert
                ViewManager.getInstance().hauptmenueladen();
            } else if (response.statusCode()==400) {
                new Alert(Alert.AlertType.ERROR, "Passwort darf nicht leer bleiben").show();
            } else if (response.statusCode()==409) {
                new Alert(Alert.AlertType.ERROR, "Benutzername bereits vergeben").show();
            }

        } else {
            new Alert(Alert.AlertType.ERROR, "Passwörter stimmmen nicht überein!").show();
            // ansonsten kommt ein Pop-up Fenster (Alert)
        }
    }

}
