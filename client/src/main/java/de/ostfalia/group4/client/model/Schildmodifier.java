package de.ostfalia.group4.client.model;

/**
 * Schild
 */
public class Schildmodifier extends Item {
    /**
     * Konstruktor
     *
     * @param position Die Position auf dem Spielfeld
     */
    public Schildmodifier(Position position) {
        this.position=position;
    }

    @Override
    public void auswirkung(Spielfigur spielfigur) {
        spielfigur.hatSchild = true;
    }
}
