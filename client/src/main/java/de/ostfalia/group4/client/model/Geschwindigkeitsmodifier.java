package de.ostfalia.group4.client.model;

public class Geschwindigkeitsmodifier extends Item{
    public int geschwindigkeitsmodifier;

    public Geschwindigkeitsmodifier(Position position, int geschwindigkeitsmodifier) {
        this.position=position;
        this.geschwindigkeitsmodifier=geschwindigkeitsmodifier;
    }
}
