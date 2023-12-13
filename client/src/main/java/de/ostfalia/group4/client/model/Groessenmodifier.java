package de.ostfalia.group4.client.model;

public class Groessenmodifier extends Item {
    public int groessenmodifier;

    // Konstruktor
    public Groessenmodifier(Position position, int groessenmodifier) {
        this.position=position;
        this.groessenmodifier=groessenmodifier;
    }

    @Override
    public void auswirkung(Spielfigur spielfigur) { // wenn größer werden soll wird das letzte Körperteil hinzufügt, sonst wird das letzte Körperteil entfernt
        if (groessenmodifier < 0) {
            if (spielfigur.getKoerper().isEmpty()){ //damit der Kopf als letztes Körperteil entfernt wird
                spielfigur.position = null;
            } else {
                spielfigur.letztesKoerperteilentfernen();
            }
        } else {
            spielfigur.letztesKoerperteilhinzufuegen();
        }
    }
}
