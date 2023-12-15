package de.ostfalia.group4.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.Date;
import java.util.List;

public class HistorieController {

    @FXML
    TableView<Statistik> historieTabelle;

    // initialize = wenn der view lädt
    @FXML
    public void initialize() throws URISyntaxException, IOException, InterruptedException {
        String url = "http://localhost:8080/api/stats";
        String jwt=LoginController.jwt;

        // Erstellen einer GET-Anfrage
        HttpRequest request=HttpRequest.newBuilder(new URI(url)).GET().header("Authorization","Bearer "+jwt).build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        // spalten alle gleich breit machen
        for (TableColumn<Statistik, ?> spalte : historieTabelle.getColumns()) {
            spalte.prefWidthProperty().bind(historieTabelle.widthProperty().divide(3));
        }
        ObjectMapper objectmapper=new ObjectMapper();
        List<Statistik> statistiken=objectmapper.readValue(response.body(), new TypeReference<List<Statistik>>() {});
        historieTabelle.getItems().addAll(statistiken);
    }

    public void hauptmenueladen(ActionEvent actionEvent) {
        ViewManager.getInstance().hauptmenueladen();
    }
}
