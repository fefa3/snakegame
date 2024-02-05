package de.ostfalia.group4.client.model;

import java.util.LinkedList;

/**
 * Spielfigur managed Körper und ist Kopf der Schlange
 */
public class Spielfigur {
    /**
     * Position auf dem Spielfeld
     */
    public Position position;

    /**
     * Der Körper der Schlange
     */
    private LinkedList<Spielkoerperteil> koerper;

    /**
     * Die aktuelle Richtung
     */
    public int richtung; // 0: hoch, 1: rechts, 2: unten, 3: links

    /**
     * Ob ein Schild aktiv ist
     */
    public boolean hatSchild;

    /**
     * Das zuletzt entfernte Körperteil
     */
    public Spielkoerperteil letztesKoerperteil;

    /**
     * Konstruktor
     *
     * @param position Die Position auf dem Spielfeld
     */
    public Spielfigur(Position position) {
        this.position = position;
        koerper = new LinkedList<>();
        richtung = 1;
        hatSchild = false;
    }

    /**
     * In die aktuelle Richtung bewegen
     */
    public void bewegen() {
        Position altePosition = new Position(position.x, position.y);
        switch (richtung) { // Position entsprechend der Richtung anpassen
            case 0:
                position.y -= 1;
                break;
            case 1:
                position.x += 1;
                break;
            case 2:
                position.y += 1;
                break;
            case 3:
                position.x -= 1;
                break;
        }
        koerper.addFirst(new Spielkoerperteil(altePosition)); // neuer Teil wird vorne hinzufügt (neues Körperteil)
        letztesKoerperteilentfernen();
    }

    /**
     * Merken des letzten Körperteils was entfernt wurde, damit das beim redmushroom wieder hinzugefügt werden kann
     */
    public void letztesKoerperteilentfernen() {
        letztesKoerperteil = koerper.removeLast();
    }

    /**
     * Fügt das letzte Körperteil wieder hinzu
     */
    public void letztesKoerperteilhinzufuegen() {
        koerper.addLast(letztesKoerperteil);
        letztesKoerperteil = null; // Sicherstellung, dass dasselbe Körperteil nur einmal hinzugefügt wird
    }

    /**
     * Getter für Körper
     *
     * @return Den Körper
     */
    public LinkedList<Spielkoerperteil> getKoerper() {
        return koerper;
    }

    /**
     * Random Richtung ändern, um mit Scild Kollision Hindernis/ Körper oder Wand zu verhindern
     */
    public void ausweichen() {
        switch (richtung) {
            case 0:
                if (Math.random()<0.5) {
                    richtung = 1;
                }else {
                    richtung = 3;
                }
                break;
            case 1:
                if (Math.random()<0.5) {
                    richtung = 0;
                }else {
                    richtung = 2;
                }
                break;
            case 2:
                if (Math.random()<0.5) {
                    richtung = 3;
                }else {
                    richtung = 1;
                }
                break;
            case 3:
                if (Math.random()<0.5) {
                    richtung = 2;
                }else {
                    richtung =0;
                }
                break;
        }
    }

    /**
     * Überprüfen, ob sich ein Teil der Spielfigur sich an dieser Position befindet
     *
     * @param position Die Position auf dem Spielfeld
     * @return Ob die Schlange diese Position belegt
     */
    public boolean belegtPosition(Position position) {
        if (this.position == null) {
            return false;
        }
        if (this.position.equals(position)) {
            return true;
        }
        for (Spielkoerperteil spielkoerperteil : koerper) {
            if (spielkoerperteil.position.equals(position)) {
                return true;
            }
        }
        return false;
    }
}
