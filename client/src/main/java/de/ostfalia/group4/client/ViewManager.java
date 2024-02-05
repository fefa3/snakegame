package de.ostfalia.group4.client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Singleton um die Views zu ändern
 */
public class ViewManager {
    /**
     * Die Instanz des View Managers
     */
    private static ViewManager instance;

    /**
     * Getter für die Instanz
     * @return Die Instanz des View Manager
     */
    public static ViewManager getInstance() {
        if (null == instance) {
            instance = new ViewManager();
        }
        return instance;
    }

    /**
     * Privater Konstruktor, damit nur ViewManager den ViewManager instanziieren kann
     */
    private ViewManager (){

    }

    /**
     * Funktion zur Anzeige eines Fensters
     *
     * @param view Der View der angezeigt werden soll als fxml Datei
     * @param titel Titel des Fensters
     */
    private void viewladen(String view, String titel) {
        try {
            Stage mainstage = MainApplication.mainstage;
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(view));
            Scene scene = new Scene(fxmlLoader.load());
            mainstage.setScene(scene);
            mainstage.setTitle(titel);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Hauptmenü-View anzeigen
     */
    public void hauptmenueladen(){
        viewladen("hauptmenueview.fxml", "Hauptmenü");
    }

    /**
     * Historie-View anzeigen
     */
    public void historieladen(){
        viewladen("historieview.fxml", "Historie");
    }

    /**
     * Login-View anzeigen

     */
    public void loginladen(){
        viewladen("loginview.fxml", "Login");
    }

    /**
     * Registrieren-View anzeigen
     */
    public void registrierenladen(){
        viewladen("registrierenview.fxml", "Registrieren");
    }

    /**
     * Spiel-View anzeigen
     */
    public void spielladen(){
        viewladen("snakeview.fxml", "Snake");
    }
}

