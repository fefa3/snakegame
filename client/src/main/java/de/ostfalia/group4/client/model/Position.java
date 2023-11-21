package de.ostfalia.group4.client.model;

import de.ostfalia.group4.client.SnakeController;

import java.util.Objects;

// Koordinaten Repräsentation
public class Position {
    public int x, y;

    public Position(int x, int y) {
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
