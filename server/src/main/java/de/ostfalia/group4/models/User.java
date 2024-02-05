package de.ostfalia.group4.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Repräsentation eines Nutzers
 */
@Entity
@Table(name="Userdaten")
public class User {
    /**
     * Nutzername
     */
    @Id
    private String name;

    /**
     * Passwort des Nutzers
     */
    private String password;

    /**
     * Konstruktor
     *
     * @param name Nutzername
     * @param password Passwort des Nutzers
     */
    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    /**
     * Leerer Konstruktor für Datenbankanbindung
     */
    public User() {

    }

    /**
     * Getter für Nutzername
     * @return der Nutzername
     */
    public String getName() {
        return name;
    }

    /**
     * Setter für Nutzername
     * @param name Der Nutzername
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter für das Passwort
     * @return Das Passwort
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter für das Passwort
     * @param password Das Passwort
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
// Id beschreibt Primärschlüssel der Tabelle
// Entity beschreibt diese Klasse als Datenbanktabelle
// Table Table enthält Tabelleneigenschaften. Der Name der Tabelle wurde geändert, da er in H2 nicht USer heißen kann
