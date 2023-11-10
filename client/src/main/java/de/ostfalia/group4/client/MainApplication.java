package de.ostfalia.group4.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    public static Stage mainstage;
    // Hauptmenü/ Login = gleiches Fenster (Stage = Fenster / Scene = Hauptmenü, Login)
    @Override
    public void start(Stage stage) throws IOException {
        mainstage = stage;
        mainstage.setResizable(false);
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("loginview.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Snake");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}