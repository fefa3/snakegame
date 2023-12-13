package de.ostfalia.group4.client.model;

public class Schildmodifier extends Item {
    public Schildmodifier(Position position) {
        this.position=position;
    }

    @Override
    public void auswirkung(Spielfigur spielfigur) {
        spielfigur.hatSchild = true;
    }
}
