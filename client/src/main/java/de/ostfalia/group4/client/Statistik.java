package de.ostfalia.group4.client;

import java.util.Date;

public class Statistik {
    public int zeit;

    public int getZeit() {
        return zeit;
    }

    public Date getDatum() {
        return datum;
    }

    public int getScore() {
        return score;
    }

    public Date datum;
    public int score;

    // Konstruktor instanziiert die Statistik
    public Statistik(int zeit, Date datum, int score) {
        this.zeit = zeit;
        this.datum = datum;
        this.score = score;
    }
}
