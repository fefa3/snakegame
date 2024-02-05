package de.ostfalia.group4.client.model;

import javafx.animation.Timeline;

/**
 * Schnell / langsam Modifier
 */
public class Geschwindigkeitsmodifier extends Item{
    /**
     * Wert des Modifiers
     */
    public double geschwindigkeitsmodifier;

    /**
     * Konstruktor
     *
     * @param position Position auf dem Spielfeld
     * @param geschwindigkeitsmodifier Wert des Modifiers
     */
    public Geschwindigkeitsmodifier(Position position, double geschwindigkeitsmodifier) {
        this.position=position;
        this.geschwindigkeitsmodifier=geschwindigkeitsmodifier;
    }

    @Override
    public void auswirkung(Timeline timeline) {
        timeline.setRate(timeline.getRate()+geschwindigkeitsmodifier);
    }
}
