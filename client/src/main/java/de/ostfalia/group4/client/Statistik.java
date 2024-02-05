package de.ostfalia.group4.client;

import java.util.Date;

/**
 * Repräsentation eines Spielergebnisses
 */
public class Statistik {
    /**
     * Die Zeit in Sekunden
     */
    public int zeit;

    /**
     * Getter für die Zeit
     * @return Die Zeit
     */
    public int getZeit() {
        return zeit;
    }

    /**
     * Getter für das Datum
     * @return Das Datum
     */
    public Date getDatum() {
        return datum;
    }

    /**
     * Getter für den Score
     * @return Der Score
     */
    public int getScore() {
        return score;
    }

    /**
     * Das Datum
     */
    public Date datum;

    /**
     * Der Score
     */
    public int score;

    /**
     * Konstruktor
     *
     * @param zeit Die Zeit in Sekunden
     * @param datum Das Datum des Spiels
     * @param score Der score
     */
    public Statistik(int zeit, Date datum, int score) {
        this.zeit = zeit;
        this.datum = datum;
        this.score = score;
    }

    /**
     * Leerer Konstruktor für JSON
     */
    public Statistik() {
    }

    /**
     * Setter für die Zeit
     * @param zeit Die Zeit in Sekunden
     */
    public void setZeit(int zeit) {
        this.zeit = zeit;
    }

    /**
     * Setter für das Datum
     * @param datum Das Datum
     */
    public void setDatum(Date datum) {
        this.datum = datum;
    }

    /**
     * Setter für den Score
     * @param score Der Score
     */
    public void setScore(int score) {
        this.score = score;
    }
}
