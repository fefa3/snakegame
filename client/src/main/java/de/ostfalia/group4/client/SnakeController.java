package de.ostfalia.group4.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.time.StopWatch;
import de.ostfalia.group4.client.model.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;
import javax.sound.sampled.*;
import java.io.File;

public class SnakeController {

    public Button soundbutton;
    @FXML
    private Canvas gamesurface; // Grid fängt oben links bei 0/0 an

    @FXML
    private Label gameOverLabel;
    @FXML
    private Label scorelabel;
    @FXML
    private Label timelabel;
    @FXML
    private Button tryagain;
    // final= Variablen sind fest und werden nicht verändert
    private static final int TILE_SIZE = 20; // Tile size: wie groß die Fläche ist, auf der sich die Items, Hindernisse etc. befinden
    private Spielfeldumrandung spielfeldumrandung;
    private Item redmushroom;
    private Item bluemushroom;

    private Geschwindigkeitsmodifier yellowflash;
    private Geschwindigkeitsmodifier greenslime;
    private Schildmodifier orangeschild;
    private Hindernis[] hindernisse;
    private boolean gameOver;
    private Timeline timeline;
    private Spielfigur spielfigur;

    private int score;
    private StopWatch zeit;
    private boolean soundAbspielen = true; //Standartmaßig true

    @FXML
    private void initialize() {
        spielfeldumrandung = new Spielfeldumrandung(20,20);
        zeit=new StopWatch();
        spielladen();
        gamesurface.sceneProperty().addListener((obs, oldScene, newScene) -> { // Tastatureingaben werden abgefangen, damit Spieler Schlange bewegen kann
            if (oldScene != null) { // != -> nicht gleich
                oldScene.removeEventHandler(KeyEvent.KEY_PRESSED, this::handleKeyPress);
            }
            if (newScene != null) {
                newScene.addEventHandler(KeyEvent.KEY_PRESSED, this::handleKeyPress);
            }
        });

        // erstellt Game Loop (Ticks (ein Tick = 1 Durchlauf der Loop)
        // in Millisekunden
        timeline = new Timeline(new KeyFrame(Duration.millis(200), event -> {
            if (!gameOver) {
                spielfigur.bewegen();
                auswirkungen();
                checkCollision();
                draw();
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void spielladen() {
        spielfigur = new Spielfigur(spielfeldumrandung.mitte()); // Schlagenkopf wird in der Mitte des Grids erstellt
        redmushroom = new Groessenmodifier(generateRandomFruit(), 1);
        bluemushroom = new Groessenmodifier(generateRandomFruit(),-1);
        yellowflash = new Geschwindigkeitsmodifier(generateRandomFruit(), 0.1);
        greenslime = new Geschwindigkeitsmodifier(generateRandomFruit(),-0.1);
        orangeschild = new Schildmodifier(generateRandomFruit());
        hindernisse = new Hindernis[]{new Hindernis(generateRandomFruit()), new Hindernis(generateRandomFruit()), new Hindernis(generateRandomFruit())};
        gameOver = false;
        score = 0;
        zeit.reset();
        zeit.start();
        scorelabel.setText("Score: "+score);
    }

    @FXML
    // bei restart wieder alles initialisieren
    private void restart(ActionEvent event) {
        spielladen();
        gameOverLabel.setVisible(false);
        tryagain.setVisible(false);
        timeline.setRate(1);
    }
    // Definieren der Tastatureingaben, was diese bewirken
    @FXML
    private void handleKeyPress(javafx.scene.input.KeyEvent event) {
        KeyCode code = event.getCode();

        switch (code) {
            case UP:
                // die Richtungen schließen sich gegenseitig aus, also wenn oben geht nicht unten
                if (spielfigur.richtung != 2) {
                    spielfigur.richtung = 0;
                }
                break;
            case RIGHT:
                if (spielfigur.richtung != 3) {
                    spielfigur.richtung = 1;
                }
                break;
            case DOWN:
                if (spielfigur.richtung != 0) {
                    spielfigur.richtung = 2;
                }
                break;
            case LEFT:
                if (spielfigur.richtung != 1) {
                    spielfigur.richtung = 3;
                }
                break;
        }
    }

// Item Auswirkungen
    private void auswirkungen() {
        if (spielfigur.hatSchild && isCollision()) { //Figur kann vorher ausweichen, bevor Kopf in der Wand ist
            spielfigur.letztesKoerperteilhinzufuegen();
            spielfigur.position = spielfigur.getKoerper().pop().position; //pop: entfernt das erste Element und gibt  es zurück
            spielfigur.hatSchild = false;
            spielfigur.ausweichen();
            sound("client\\SchildOffSound.wav");
            return;
        }
//!: Verneinung
        if (spielfigur.position.equals(bluemushroom.position)) {
            bluemushroom.auswirkung(spielfigur);
            bluemushroom = new Groessenmodifier(generateRandomFruit(), -1);
            sound("client\\BluemushroomSound.wav");
        } else if (spielfigur.position.equals(redmushroom.position)) {
            redmushroom.auswirkung(spielfigur);
            redmushroom = new Groessenmodifier(generateRandomFruit(), 1);
            score +=1; // für einen roten Pilz bekommt man einen Punkt
            scorelabel.setText("Score: "+score); //score wird passend angezeigt (+1)
            sound("client\\RedmushroomSound.wav");
        } else if (spielfigur.position.equals(yellowflash.position)) {
            yellowflash.auswirkung(timeline);
            yellowflash = new Geschwindigkeitsmodifier(generateRandomFruit(), 0.1);
            sound("client\\YellowflashSound.wav");
        } else if (spielfigur.position.equals(greenslime.position)) {
            greenslime.auswirkung(timeline);
            greenslime = new Geschwindigkeitsmodifier(generateRandomFruit(), -0.1);
            sound("client\\GreenslimeSound.wav");
        } else if (spielfigur.position.equals(orangeschild.position)) {
            orangeschild.auswirkung(spielfigur);
            orangeschild = new Schildmodifier(generateRandomFruit());
            sound("client\\SchildOnSound.wav");
        }
    }
    private boolean isCollision() { //Check, ob Spielfigur kollidiert
        Position head = spielfigur.position;

        // Kollision mit Wand
        if (spielfeldumrandung.kollision(head)) {
            return true;
        }

        // Kollision mit Körper
        for (int i = 0; i < spielfigur.getKoerper().size(); i++) { // i++ = i+1, ganz am Anfang ist i=0, für jedes Körperteil der Schlange wird der Loop einmal durchgegangen
            if (head.equals(spielfigur.getKoerper().get(i).position)) { // man überprüft, ob der Kopf die gleiche Position wie ein Körperteil hat
                return true;
            }
        }
        // Kollision mit Hindernissen
        for (Hindernis hindernis : hindernisse) {
            if (head.equals(hindernis.position)){
                return true;
            }
        }
        return false;
    }
    private void checkCollision() {
        // Schlange vorhanden?
        if (spielfigur.position == null) {
            gameOver = true;
            sound("client\\CollisionSound.wav");
            return;
        }
        if (isCollision()) {
            gameOver = true;
            sound("client\\CollisionSound.wav");
        }
    }

    private Position generateRandomFruit() {

        Position neuePosition;
        boolean isredmushroom, isbluemushroom, isyellowflash, isgreenslime, isorangeschild, ishindernis;
        // Die Items dürfen nicht auf der Schlange und aufeinander spawnen
        do {
            //Überprüfen ob bereits Item oder Hindernis auf der Position
            neuePosition = spielfeldumrandung.zufallsPosition();
            isredmushroom = redmushroom != null && redmushroom.position.equals(neuePosition);
            isbluemushroom = bluemushroom != null && bluemushroom.position.equals(neuePosition);
            isyellowflash = yellowflash != null && yellowflash.position.equals(neuePosition);
            isgreenslime = greenslime != null && greenslime.position.equals(neuePosition);
            isorangeschild = orangeschild != null && orangeschild.position.equals(neuePosition);
            ishindernis = false;
            if (hindernisse != null) {
                for (Hindernis hindernis : hindernisse) {
                    if (hindernis.position.equals(neuePosition)) {
                        ishindernis = true;
                        break;
                    }
                }
            }
        } while (spielfigur.belegtPosition(neuePosition) || isredmushroom || isbluemushroom || isyellowflash || isgreenslime || isorangeschild || ishindernis);

        return neuePosition;
    }

    // Spielfeld mit Figur und Item wird "gemalt"
    private void draw() {
        GraphicsContext gc = gamesurface.getGraphicsContext2D();
        gc.clearRect(0, 0, spielfeldumrandung.breite * TILE_SIZE, spielfeldumrandung.hoehe * TILE_SIZE);

        // Draw Spielkoerper
        gc.setFill(Color.GREEN);
        for (Spielkoerperteil koerperteil : spielfigur.getKoerper()) {
            gc.fillRect(koerperteil.position.x * TILE_SIZE, koerperteil.position.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        }
        if (spielfigur.hatSchild) { //Darstellung des Schilds
            gc.setFill(Color.BLUE);
        }
        if (spielfigur.position != null) {
            gc.fillRect(spielfigur.position.x * TILE_SIZE, spielfigur.position.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        }

        // Draw Item
        gc.setFill(Color.RED);
        gc.fillRect(redmushroom.position.x * TILE_SIZE, redmushroom.position.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);

        // Draw Item
        gc.setFill(Color.BLUE);
        gc.fillRect(bluemushroom.position.x * TILE_SIZE, bluemushroom.position.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);

        // Draw Item
        gc.setFill(Color.YELLOW);
        gc.fillRect(yellowflash.position.x * TILE_SIZE, yellowflash.position.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);

        // Draw Item
        gc.setFill(Color.LIGHTGREEN);
        gc.fillRect(greenslime.position.x * TILE_SIZE, greenslime.position.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);

        // Draw Item
        gc.setFill(Color.ORANGE);
        gc.fillRect(orangeschild.position.x * TILE_SIZE, orangeschild.position.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);

        // Draw Hindernis
        gc.setFill(Color.GREY);
        for (Hindernis hindernis : hindernisse) {
            gc.fillRect (hindernis.position.x * TILE_SIZE, hindernis.position.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        }

        // "game over" Mitteilung
        if (gameOver) {
            gameOverLabel.setVisible(true);
            tryagain.setVisible(true);
            zeit.stop();
            statsSpeichern();
        }

        // Gespielte Zeit im Level anzeigen
        timelabel.setText("Zeit: "+zeit.getTime()/1000+" sekunden");
    }

    public void hauptmenueladen(ActionEvent actionEvent) {
        timeline.pause();
        if (!zeit.isStopped()){ //nur pausieren wenn Timer nicht gestoppt ist
            zeit.suspend(); // Zeit pausiert mit Pausierung des Spiels
        }
        // Abfrage, ob spiel wirklich beendet werden soll
        Alert alert = new Alert(Alert.AlertType.WARNING, "Spiel wirklich beenden?", ButtonType.OK, ButtonType.CANCEL);
        alert.setTitle("Spiel beenden");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            ViewManager.getInstance().hauptmenueladen();
            return;
        }
        timeline.play();
        zeit.resume();
    }
    private void statsSpeichern(){
        try { //Fehlerhandling bei Jason und HTTP Request
            Statistik statistik = new Statistik((int) (zeit.getTime() / 1000), new Date(), score);
            String url = "http://localhost:8080/api/stats/add";
            String jwt = LoginController.jwt;
            ObjectMapper objectmapper = new ObjectMapper();
            String body = objectmapper.writeValueAsString(statistik); //Statistik in Json umwandeln
            // Erstellen einer GET-Anfrage
            HttpRequest request = HttpRequest.newBuilder(new URI(url)).POST(HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8)).header("Authorization", "Bearer " + jwt).header("Content-Type", "application/json").build(); // HTTP Request wird aufgebaut
            HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    private void sound (String filePath) {
        if (!soundAbspielen) {
            return;
        }

        // Thread = verarbeitungsprozess
        //parallele Verarbeitung (Musik wird im neuen Thread abgespielt)
        new Thread(() -> {
            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile()); // Lade die Audio-Datei
                Clip clip = AudioSystem.getClip(); // Erzeugt einen Clip
                clip.open(audioInputStream); // Öffnet den Clip mit der Audio-Datei
                clip.start(); // Startet die Wiedergabe
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public void musikToggle(ActionEvent actionEvent) { //Musik geht an oder aus
        soundAbspielen=!soundAbspielen;
        if (soundAbspielen) {
            soundbutton.setText("Musik aus");
        } else {
            soundbutton.setText("Musik an");
        }
    }
}




