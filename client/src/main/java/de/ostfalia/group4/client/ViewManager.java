package de.ostfalia.group4.client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

// Singleton
public class ViewManager {
    private static ViewManager instance;
    public static ViewManager getInstance() {
        if (null == instance) {
            instance = new ViewManager();
        }
        return instance;
    }
    // Konstruktor: Nur ViewManager kann ViewManager instanziieren.
    private ViewManager (){

    }
    // Funktion zur Anzeige eines Fensters
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
    public void hauptmenueladen(){
        viewladen("hauptmenueview.fxml", "Hauptmenü");
    }
    public void historieladen(){
        viewladen("historieview.fxml", "Historie");
    }
    public void loginladen(){
        viewladen("loginview.fxml", "Login");
    }
    public void registrierenladen(){
        viewladen("registrierenview.fxml", "Registrieren");
    }
    public void spielladen(){
        viewladen("snakeview.fxml", "Snake");
    }
}

