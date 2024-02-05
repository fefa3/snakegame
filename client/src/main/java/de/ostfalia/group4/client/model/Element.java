package de.ostfalia.group4.client.model;

/**
 * Interface für Spieleelement
 */
public interface Element {
    /**
     * Auswirkung auf die Spielfigur
     *
     * @param spielfigur Die Spielfigur
     */
    void auswirkung(Spielfigur spielfigur);
}
