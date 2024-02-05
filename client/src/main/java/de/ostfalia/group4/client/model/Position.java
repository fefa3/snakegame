package de.ostfalia.group4.client.model;

import java.util.Objects;

/**
 * Eine Position auf dem Spielfeld
 */
public class Position {
    /**
     * x- und y- Koordinate
     */
    public int x, y;

    /**
     * Konstruktor
     *
     * @param x x-Koordinate
     * @param y y-Koordinate
     */
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
