package de.ostfalia.group4.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.time.Instant;
import java.util.Date;

public class HistorieController {

    @FXML
    TableView<Statistik> historieTabelle;

    // initialize = wenn der view lädt
    @FXML
    public void initialize() {
        // spalten alle gleich breit machen
        for (TableColumn<Statistik, ?> spalte : historieTabelle.getColumns()) {
            spalte.prefWidthProperty().bind(historieTabelle.widthProperty().divide(3));
        }

        // daten hinzufügen
        historieTabelle.getItems().add(new Statistik(10, Date.from(Instant.now()), 20));
        historieTabelle.getItems().add(new Statistik(215, Date.from(Instant.now()), 850));
    }

    public void hauptmenueladen(ActionEvent actionEvent) {
        ViewManager.getInstance().hauptmenueladen();
    }
}
