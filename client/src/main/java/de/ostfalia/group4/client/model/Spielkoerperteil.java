package de.ostfalia.group4.client.model;


/**
 * Ein Körperteil der Schlange
 */
public class Spielkoerperteil {
    /**
     * Position auf dem Spielfeld
     */
    public Position position;

    /**
     * Konstruktor
     *
     * @param position Position auf dem Spielfeld
     */
    public Spielkoerperteil(Position position) {
        this.position = position;
    }
}
