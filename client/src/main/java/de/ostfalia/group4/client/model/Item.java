package de.ostfalia.group4.client.model;

import javafx.animation.Timeline;

/**
 * Item, das Modifier zusammenfasst
 */
public class Item implements Element{
    /**
     * Position des Items
     */
    public Position position;

    @Override
    public void auswirkung(Spielfigur spielfigur) {
    }

    /**
     * Die Auswirkung auf das Spiel
     *
     * @param timeline Timeline der Gameloop
     */
    public void auswirkung(Timeline timeline) {
    }
}
