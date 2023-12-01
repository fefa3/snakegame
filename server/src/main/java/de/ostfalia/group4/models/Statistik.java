package de.ostfalia.group4.models;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
public class Statistik {
    int zeit;
    @ManyToOne
    @JoinColumn(name="name")
    User user;
    @Basic
    Date datum;
    int score;

    public int getZeit() {
        return zeit;
    }

    public void setZeit(int zeit) {
        this.zeit = zeit;
    }

    public Statistik() {
    }

    public Statistik(int zeit, User user, Date datum, int score) {
        this.zeit = zeit;
        this.user = user;
        this.datum = datum;
        this.score = score;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Id
    @GeneratedValue
    int id;
}
