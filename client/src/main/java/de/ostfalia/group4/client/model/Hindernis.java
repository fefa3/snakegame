package de.ostfalia.group4.client.model;

/**
 * Hindernis auf dem Spielfeld
 */
public class Hindernis implements Element{
    /**
     * Position des Hindernisses
     */
    public Position position;

    /**
     * Konstruktor
     *
     * @param position Position des Hindernisses
     */
    public Hindernis(Position position) {
        this.position = position;
    }

    @Override
    public void auswirkung(Spielfigur spielfigur) {

    }
}
