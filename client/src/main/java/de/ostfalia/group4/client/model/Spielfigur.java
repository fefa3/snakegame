package de.ostfalia.group4.client.model;

import java.util.LinkedList;

// Spielfigur managed Körper und ist Kopf der Schlange
public class Spielfigur {
    public Position position;
    private LinkedList<Spielkoerperteil> koerper;
    public int richtung; // 0: hoch, 1: rechts, 2: unten, 3: links
    public boolean hatSchild;
    public Spielkoerperteil letztesKoerperteil;

    // Konstruktor (für einen definierten Anfangszustand)
    public Spielfigur(Position position) {
        this.position = position;
        koerper = new LinkedList<>();
        richtung = 1;
        hatSchild = false;
    }
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
    public void letztesKoerperteilentfernen() { // Merken des letzten Körperteil was entfernt wurde, damit das beim redmushroom wieder hinzugefügt werden kann
        letztesKoerperteil = koerper.removeLast();
    }

    public void letztesKoerperteilhinzufuegen() {
        koerper.addLast(letztesKoerperteil);
        letztesKoerperteil = null; // Sicherstellung, dass dasselbe Körperteil nur einmal hinzugefügt wird
    }

    public LinkedList<Spielkoerperteil> getKoerper() {
        return koerper;
    }
    public void ausweichen() { //Random Richtung ändern, um mit Scild Kollision Hindernis/ Körper oder Wand zu verhindern
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
    public boolean belegtPosition(Position position) { //Überprüfen, ob sich ein Teil der Spielfigur sich an dieser Position befindet
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
