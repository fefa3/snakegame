package de.ostfalia.group4.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Userdaten")
public class User {
    @Id
    private String name;
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
// Id beschreibt Primärschlüssel der Tabelle
// Entity beschreibt diese Klasse als Datenbanktabelle
// Table Table enthält Tabelleneigenschaften. Der Name der Tabelle wurde geändert, da er in H2 nicht USer heißen kann
