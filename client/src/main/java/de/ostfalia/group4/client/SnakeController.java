package de.ostfalia.group4.client;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;

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
    private static final int GAME_SPEED = 200; // in Millisekunden

    // jedes Körperteil hat eine Referenz auf das nächste
    private LinkedList<Position> snake;
    private Position redmushroom;
    private int direction; // 0: up, 1: right, 2: down, 3: left
    private boolean gameOver;

    @FXML
    private void initialize() {
        snake = new LinkedList<>(); // leere Liste wird erstellt
        snake.add(new Position(GRID_SIZE / 2, GRID_SIZE / 2)); // Schlagenkopf wird in der Mitte des Grids erstellt
        redmushroom = generateRandomFruit();
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
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(GAME_SPEED), event -> {
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
    private void restart(ActionEvent event) {
        snake = new LinkedList<>(); // leere Liste wird erstellt
        snake.add(new Position(GRID_SIZE / 2, GRID_SIZE / 2)); // Schlagenkopf wird in der Mitte des Grids erstellt
        redmushroom = generateRandomFruit();
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

        snake.addFirst(newHead); // neuer Kopf wird vorne hinzufügt (neues Körperteil)
//!: Verneinung
        if (!newHead.equals(redmushroom)) { // wenn man kein Vergrößerungsitem eingesammelt hat, bleibt man 1 groß und der letzte Pixel vom Feld von dem man kommt, verschwindet wieder
            snake.removeLast();
        } else {
            redmushroom = generateRandomFruit(); // ansonsten neues Item generieren und letzter Step lassen, damit Schlange größer wird
        }
    }

    private void checkCollision() {
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

        // Die Items dürfen nicht auf der Schlange spawnen
        while (snake.contains(new Position(x, y))) {
            x = (int) (Math.random() * GRID_SIZE);
            y = (int) (Math.random() * GRID_SIZE);
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
        gc.fillRect(redmushroom.x * TILE_SIZE, redmushroom.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);


        // "game over" Mitteilung
        if (gameOver) {
            gameOverLabel.setVisible(true);
            tryagain.setVisible(true);
        }
    }

    // Koordinaten Repräsentation
    private static class Position {
        int x, y;

        Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override // Überschreiben
        public boolean equals(Object obj) { // Vergleicht Objekte
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Position position = (Position) obj;
            return x == position.x && y == position.y; // Vergleich der Koordinaten (Positionen/ Points), ob sich auf dieser etwas befindet
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
    // Funktion zur Anzeige eines Fensters
    public static void loadWindow() {
        try {
            Stage mainstage = MainApplication.mainstage;
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("snakeview.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            mainstage.setScene(scene);
            mainstage.setTitle("Snake");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}




