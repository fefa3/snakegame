package de.ostfalia.group4.client;

import de.ostfalia.group4.client.model.Geschwindigkeitsmodifier;
import de.ostfalia.group4.client.model.Groessenmodifier;
import de.ostfalia.group4.client.model.Item;
import de.ostfalia.group4.client.model.Position;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Optional;

public class SnakeController {

    @FXML
    private Canvas gamesurface; // Grid fängt oben links bei 0/0 an

    @FXML
    private Label gameOverLabel;
    @FXML
    private Button tryagain;
    // final= Variablen sind fest und werden nicht verändert
    private static final int TILE_SIZE = 20; // Tile size: wie groß die Fläche ist, auf der sich die Items, Hindernisse etc. befinden
    private static final int GRID_SIZE = 20; // Grid Side: Netzgröße (Spielfeld x*x)
    private static int GAME_SPEED = 200; // in Millisekunden

    // jedes Körperteil hat eine Referenz auf das nächste
    private LinkedList<Position> snake;
    private Item redmushroom;
    private Item bluemushroom;

    private Geschwindigkeitsmodifier yellowflash;
    private Geschwindigkeitsmodifier greenslime;
    private int direction; // 0: up, 1: right, 2: down, 3: left
    private boolean gameOver;
    private Timeline timeline;

    @FXML
    private void initialize() {
        snake = new LinkedList<>(); // leere Liste wird erstellt
        snake.add(new Position(GRID_SIZE / 2, GRID_SIZE / 2)); // Schlagenkopf wird in der Mitte des Grids erstellt
        redmushroom = new Groessenmodifier(generateRandomFruit(), 1);
        bluemushroom = new Groessenmodifier(generateRandomFruit(),-1);
        yellowflash = new Geschwindigkeitsmodifier(generateRandomFruit(), -10);
        greenslime = new Geschwindigkeitsmodifier(generateRandomFruit(),10);
        direction = 1; // initial direction ist rechts
        gameOver = false;
        gamesurface.sceneProperty().addListener((obs, oldScene, newScene) -> { // Tastatureingaben werden abgefangen, damit Spieler Schlange bewegen kann
            if (oldScene != null) {
                oldScene.removeEventHandler(KeyEvent.KEY_PRESSED, this::handleKeyPress);
            }
            if (newScene != null) {
                newScene.addEventHandler(KeyEvent.KEY_PRESSED, this::handleKeyPress);
            }
            // != -> nicht gleich
        });

        // erstellt Game Loop (Ticks (ein Tick = 1 Durchlauf der Loop)
        timeline = new Timeline(new KeyFrame(Duration.millis(GAME_SPEED), event -> {
            if (!gameOver) {
                move();
                checkCollision();
                draw();
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    @FXML
    // bei restart wieder alles initialisieren
    private void restart(ActionEvent event) {
        snake = new LinkedList<>(); // leere Liste wird erstellt
        snake.add(new Position(GRID_SIZE / 2, GRID_SIZE / 2)); // Schlagenkopf wird in der Mitte des Grids erstellt
        redmushroom = new Groessenmodifier(generateRandomFruit(), 1);
        bluemushroom = new Groessenmodifier(generateRandomFruit(), -1);
        yellowflash = new Geschwindigkeitsmodifier(generateRandomFruit(), -10);
        greenslime = new Geschwindigkeitsmodifier(generateRandomFruit(),10);
        direction = 1; // initial direction ist rechts
        gameOver = false;
        gameOverLabel.setVisible(false);
        tryagain.setVisible(false);
    }
    // Definieren der Tastatureingaben, was diese bewirken
    @FXML
    private void handleKeyPress(javafx.scene.input.KeyEvent event) {
        KeyCode code = event.getCode();
        System.out.println(code);

        switch (code) {
            case UP:
                // die Richtungen schließen sich gegenseitig aus, also wenn oben geht nicht unten
                if (direction != 2) {
                    direction = 0;
                }
                break;
            case RIGHT:
                if (direction != 3) {
                    direction = 1;
                }
                break;
            case DOWN:
                if (direction != 0) {
                    direction = 2;
                }
                break;
            case LEFT:
                if (direction != 1) {
                    direction = 3;
                }
                break;
        }
    }

    private void move() {
        Position head = snake.getFirst();
        Position newHead;

        switch (direction) {
            case 0: // up
                newHead = new Position(head.x, head.y - 1 );
                break;
            case 1: // right
                newHead = new Position(head.x + 1, head.y);
                break;
            case 2: // down
                newHead = new Position(head.x, head.y + 1);
                break;
            case 3: // left
                newHead = new Position(head.x - 1, head.y);
                break;
            default: // damit newHead einen Wert hat (default: wenn die Richtung weder 0,1,2,3 ist)
                newHead = head;
        }

        snake.addFirst(newHead); // neuer Kopf wird vorne hinzufügt (neues Körperteil)/ Item Auswirkungen
//!: Verneinung
        if (newHead.equals(bluemushroom.position)) { //wenn man ein Verkleinerungsitem einsammelt, wird ein Körperteil entfernt
            snake.removeLast();
            bluemushroom = new Groessenmodifier(generateRandomFruit(), -1);;
        }
        if (!newHead.equals(redmushroom.position)) { // wenn man kein Vergrößerungsitem eingesammelt hat, bleibt man 1 groß und der letzte Pixel vom Feld von dem man kommt, verschwindet wieder
            snake.removeLast();
        } else {
            redmushroom = new Groessenmodifier(generateRandomFruit(), 1);; // ansonsten neues Item generieren und letzter Step lassen, damit Schlange größer wird
        }
        if (newHead.equals(yellowflash.position)) {
            GAME_SPEED += yellowflash.geschwindigkeitsmodifier; //+= Shorthand für GameSpeed = GameSpeed +
            timeline.setDelay(Duration.millis(GAME_SPEED));
            yellowflash = new Geschwindigkeitsmodifier(generateRandomFruit(), -10);
        } else if (greenslime.position.equals(newHead)) {
            GAME_SPEED += greenslime.geschwindigkeitsmodifier;
            timeline.setDelay(Duration.millis(GAME_SPEED));
            greenslime = new Geschwindigkeitsmodifier(generateRandomFruit(), 10);
        }
    }

    private void checkCollision() {
        // Schlange vorhanden?
        if (snake.isEmpty()) {
            gameOver = true;
            return;
        }
        Position head = snake.getFirst();

        // Kollision mit Wand
        if (head.x < 0 || head.x >= GRID_SIZE || head.y < 0 || head.y >= GRID_SIZE) {
            gameOver = true;
        }

        // Kollision mit Körper
        for (int i = 1; i < snake.size(); i++) { // i++ = i+1, ganz am Anfang ist i=1, für jedes Körperteil der Schlange wird der Loop einmal durchgegangen
            if (head.equals(snake.get(i))) { // man überprüft, ob der Kopf die gleiche Position wie ein Körperteil hat
                gameOver = true;
                break;
            }
        }
    }

    private Position generateRandomFruit() {
        int x = (int) (Math.random() * GRID_SIZE);
        int y = (int) (Math.random() * GRID_SIZE);

        //Überprüfen ob Red oder Blue Mushroom
        Position neuePosition = new Position(x,y);
        boolean isredmushroom = redmushroom != null && redmushroom.position.equals(neuePosition);
        boolean isbluemushroom = bluemushroom != null && bluemushroom.position.equals(neuePosition);
        boolean isyellowflash = yellowflash != null && yellowflash.position.equals(neuePosition);
        boolean isgreenslime = greenslime != null && greenslime.position.equals(neuePosition);

        // Die Items dürfen nicht auf der Schlange und aufeinander spawnen
        while (snake.contains(neuePosition) && isredmushroom && isbluemushroom && isyellowflash && isgreenslime) {
            x = (int) (Math.random() * GRID_SIZE);
            y = (int) (Math.random() * GRID_SIZE);
            neuePosition = new Position(x,y);
            isredmushroom = redmushroom.position.equals(neuePosition);
            isbluemushroom = bluemushroom.position.equals(neuePosition);
            isyellowflash = yellowflash.position.equals(neuePosition);
            isgreenslime = greenslime.position.equals(neuePosition);
        }

        return new Position(x, y);
    }

    // Spielfeld mit Figur und Item wird "gemalt"
    private void draw() {
        GraphicsContext gc = gamesurface.getGraphicsContext2D();
        gc.clearRect(0, 0, GRID_SIZE * TILE_SIZE, GRID_SIZE * TILE_SIZE);

        // Draw Schlange
        gc.setFill(Color.GREEN);
        for (Position position : snake) {
            gc.fillRect(position.x * TILE_SIZE, position.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
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

        // "game over" Mitteilung
        if (gameOver) {
            gameOverLabel.setVisible(true);
            tryagain.setVisible(true);
        }
    }

    public void hauptmenueladen(ActionEvent actionEvent) {
        timeline.pause();
        // Abfrage, ob spiel wirklich beendet werden soll
        Alert alert = new Alert(Alert.AlertType.WARNING, "Spiel wirklich beenden?", ButtonType.OK, ButtonType.CANCEL);
        alert.setTitle("Spiel beenden");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            ViewManager.getInstance().hauptmenueladen();
        }
        timeline.play();
    }
}




