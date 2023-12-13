package de.ostfalia.group4.client.model;

public class Spielfeldumrandung {
    public int hoehe;
    public int breite;

    public Spielfeldumrandung(int hoehe, int breite) {
        this.hoehe = hoehe;
        this.breite = breite;
    }
    // Definition der Mitte des Spielfelds
    public Position mitte() {
        return new Position(breite / 2, hoehe / 2);
    }
    // Überprüfung, ob Position ausserhalb der Spielumrandung
    public boolean kollision(Position position) {
        return position.x < 0 || position.x >= breite || position.y < 0 || position.y >= hoehe;
    }
    // Zufällige Position generieren
    public Position zufallsPosition() {
        int x = (int) (Math.random() * breite);
        int y = (int) (Math.random() * hoehe);
        return new Position(x, y);
    }
}
