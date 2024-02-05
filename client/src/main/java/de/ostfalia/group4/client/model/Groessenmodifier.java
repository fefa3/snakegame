package de.ostfalia.group4.client.model;

/**
 * Größe / kleiner Modifier
 */
public class Groessenmodifier extends Item {
    /**
     * Positiv / negativ-wert für Größenveränderung
     */
    public int groessenmodifier;

    /**
     * Konstruktor
     *
     * @param position Die Position auf dem Spielfeld
     * @param groessenmodifier Wert ob die Figur größer oder kleiner wird
     */
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
