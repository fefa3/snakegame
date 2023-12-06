package de.ostfalia.group4.client.model;

public class Geschwindigkeitsmodifier extends Item{
    public double geschwindigkeitsmodifier;

    public Geschwindigkeitsmodifier(Position position, double geschwindigkeitsmodifier) {
        this.position=position;
        this.geschwindigkeitsmodifier=geschwindigkeitsmodifier;
    }
}
