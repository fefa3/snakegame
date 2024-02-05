package de.ostfalia.group4.models;

import jakarta.persistence.*;

import java.sql.Date;

/**
 * Repräsentation der Statistik
 */
@Entity
public class Statistik {
    /**
     * Zeit in Sekunden
     */
    int zeit;

    /**
     * Der Nutzer, zu dem die Statistik gehört
     */
    @ManyToOne
    @JoinColumn(name="name")
    User user;

    /**
     * Das Datum der Statistik
     */
    @Basic
    Date datum;

    /**
     * Der Score
     */
    int score;

    /**
     * Getter für die Zeit
     * @return Zeit in Sekunden
     */
    public int getZeit() {
        return zeit;
    }

    /**
     * Setter für die Zeit
     * @param zeit Die Zeit in Sekunden
     */
    public void setZeit(int zeit) {
        this.zeit = zeit;
    }

    /**
     * Leerer Konstruktor für Datenbankanbindung
     */
    public Statistik() {
    }

    /**
     * Konstruktor mit parametern
     *
     * @param zeit Die Zeit in Sekunden
     * @param user Der Nutzer der Statistik
     * @param datum Das Datum
     * @param score Der Score
     */
    public Statistik(int zeit, User user, Date datum, int score) {
        this.zeit = zeit;
        this.user = user;
        this.datum = datum;
        this.score = score;
    }


    /**
     * Setter für den Nutzer
     * @param user Der Nutzer
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Getter für das Datum
     * @return Das Datum
     */
    public Date getDatum() {
        return datum;
    }

    /**
     * Setter für das Datum
     * @param datum Das Datum
     */
    public void setDatum(Date datum) {
        this.datum = datum;
    }

    /**
     * Getter für den Score
     * @return Der Score
     */
    public int getScore() {
        return score;
    }

    /**
     * Setter für den Score
     * @param score Der Score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Interne Datenbank-ID
     */
    @Id
    @GeneratedValue
    int id;
}
