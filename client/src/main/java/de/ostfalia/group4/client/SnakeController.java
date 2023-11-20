package de.ostfalia.group4.client;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.LinkedList;

public class SnakeController {

    @FXML
    private Canvas gamesurface;

    @FXML
    private Label gameOverLabel;

    private static final int TILE_SIZE = 20;
    private static final int GRID_SIZE = 20;
    private static final int GAME_SPEED = 200; // in milliseconds

    private LinkedList<Point> snake;
    private Point redmushroom;
    private int direction; // 0: up, 1: right, 2: down, 3: left
    private boolean gameOver;

    @FXML
    private void initialize() {
        snake = new LinkedList<>();
        snake.add(new Point(GRID_SIZE / 2, GRID_SIZE / 2));
        redmushroom = generateRandomFruit();
        direction = 1; // initial direction is right
        gameOver = false;
        gamesurface.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (oldScene != null) {
                oldScene.removeEventHandler(KeyEvent.KEY_PRESSED, this::handleKeyPress);
            }
            if (newScene != null) {
                newScene.addEventHandler(KeyEvent.KEY_PRESSED, this::handleKeyPress);
            }
            // != -> nicht gleich
        });

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(GAME_SPEED), event -> {
            if (!gameOver) {
                move();
                checkCollision();
                checkFruit();
                draw();
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    @FXML
    private void handleKeyPress(javafx.scene.input.KeyEvent event) {
        KeyCode code = event.getCode();
        System.out.println(code);

        switch (code) {
            case UP:
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
        Point head = snake.getFirst();
        Point newHead;

        switch (direction) {
            case 0: // up
                newHead = new Point(head.x, (head.y - 1 + GRID_SIZE) % GRID_SIZE);
                break;
            case 1: // right
                newHead = new Point((head.x + 1) % GRID_SIZE, head.y);
                break;
            case 2: // down
                newHead = new Point(head.x, (head.y + 1) % GRID_SIZE);
                break;
            case 3: // left
                newHead = new Point((head.x - 1 + GRID_SIZE) % GRID_SIZE, head.y);
                break;
            default:
                newHead = head;
        }

        snake.addFirst(newHead);

        if (!newHead.equals(redmushroom)) {
            snake.removeLast();
        } else {
            redmushroom = generateRandomFruit();
        }
    }

    private void checkCollision() {
        Point head = snake.getFirst();

        // Kollision mit Wand
        if (head.x <= 0 || head.x >= GRID_SIZE -1 || head.y <= 0 || head.y >= GRID_SIZE -1) {
            gameOver = true;
        }

        // Kollision mit Körper
        for (int i = 1; i < snake.size(); i++) {
            if (head.equals(snake.get(i))) {
                gameOver = true;
                break;
            }
        }
    }

    private void checkFruit() {
        if (snake.getFirst().equals(redmushroom)) {
            redmushroom = generateRandomFruit();
        }
    }

    private Point generateRandomFruit() {
        int x = (int) (Math.random() * GRID_SIZE);
        int y = (int) (Math.random() * GRID_SIZE);

        // Ensure that the fruit is not generated on the snake
        while (snake.contains(new Point(x, y))) {
            x = (int) (Math.random() * GRID_SIZE);
            y = (int) (Math.random() * GRID_SIZE);
        }

        return new Point(x, y);
    }

    private void draw() {
        GraphicsContext gc = gamesurface.getGraphicsContext2D();
        gc.clearRect(0, 0, GRID_SIZE * TILE_SIZE, GRID_SIZE * TILE_SIZE);

        // Draw snake
        gc.setFill(Color.GREEN);
        for (Point point : snake) {
            gc.fillRect(point.x * TILE_SIZE, point.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        }

        // Draw fruit
        gc.setFill(Color.RED);
        gc.fillRect(redmushroom.x * TILE_SIZE, redmushroom.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);


        // Show game over message
        if (gameOver) {
            gameOverLabel.setVisible(true);
        }
    }

    private static class Point {
        int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Point point = (Point) obj;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return 31 * x + y;
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




