package de.ostfalia.group4.client;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Start des Spiels
 */
public class MainApplication extends Application {
    /**
     * Haupt-Stage der JavaFX Anwendung
     */
    public static Stage mainstage;

    /**
     * Start-Funktion für JavaFX
     *
     * @param stage die Anfangs-Stage von JavaFX
     */
    // Jeder View/ Scene = gleiches Fenster (Stage = Fenster / Scene = Hauptmenü, Login)
    @Override
    public void start(Stage stage) {
        mainstage = stage;
        mainstage.setResizable(false);
        ViewManager.getInstance().loginladen();
        stage.show();
    }

    /**
     * Main-Methode von Java
     *
     * @param args Start-Argumente
     */
    public static void main(String[] args) {
        launch();
    }
}