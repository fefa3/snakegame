package de.ostfalia.group4.client.model;

import javafx.animation.Timeline;

public class Geschwindigkeitsmodifier extends Item{
    public double geschwindigkeitsmodifier;

    public Geschwindigkeitsmodifier(Position position, double geschwindigkeitsmodifier) {
        this.position=position;
        this.geschwindigkeitsmodifier=geschwindigkeitsmodifier;
    }

    @Override
    public void auswirkung(Timeline timeline) {
        timeline.setRate(timeline.getRate()+geschwindigkeitsmodifier);
    }
}
