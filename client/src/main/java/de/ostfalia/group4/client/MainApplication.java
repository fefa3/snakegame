package de.ostfalia.group4.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    public static Stage mainstage;
    // Jeder View/ Scene = gleiches Fenster (Stage = Fenster / Scene = Hauptmenü, Login)
    @Override
    public void start(Stage stage) throws IOException {
        mainstage = stage;
        mainstage.setResizable(false);
        ViewManager.getInstance().loginladen();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}