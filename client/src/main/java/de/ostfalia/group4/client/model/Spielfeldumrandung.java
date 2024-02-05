package de.ostfalia.group4.client.model;

/**
 * Repräsentation des Spielfeldrandes
 */
public class Spielfeldumrandung {
    /**
     * Höhe des Spielfelds
     */
    public int hoehe;
    /**
     * Breite des Spielfelds
     */
    public int breite;

    /**
     * Konstruktor
     *
     * @param hoehe Höhe des Spielfelds
     * @param breite Breite des Spielfelds
     */
    public Spielfeldumrandung(int hoehe, int breite) {
        this.hoehe = hoehe;
        this.breite = breite;
    }

    /**
     * Gibt die Mitte des Spielfeldes zurück
     *
     * @return Mitte des Spielfelds
     */
    public Position mitte() {
        return new Position(breite / 2, hoehe / 2);
    }

    /**
     * Überprüfung, ob Position ausserhalb der Spielumrandung
     *
     * @param position Die Position auf dem Spielfeld
     * @return true, wenn innerhalb, false wenn außerhalb des Spielfeldes
     */
    public boolean kollision(Position position) {
        return position.x < 0 || position.x >= breite || position.y < 0 || position.y >= hoehe;
    }

    /**
     * Zufällige Position generieren
     *
     * @return Die Zufallsposition
     */
    public Position zufallsPosition() {
        int x = (int) (Math.random() * breite);
        int y = (int) (Math.random() * hoehe);
        return new Position(x, y);
    }
}
