package de.ostfalia.group4.repositories;

import de.ostfalia.group4.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Datenbankanbindung der Nutzer
 */
public interface UserRepository extends JpaRepository<User,Long>{
    /**
     * Einen Nutzer anhand des Nutzernamens finden
     * @param name Der Nutzername
     * @return Der Nutzer oder null
     */
    User findByName (String name);

    /**
     * Einen Nutzer gangland des Nutzernamens und Passworts finden
     *
     * @param name Der Nutzername
     * @param password Das Passwort
     * @return Der Nutzer oder null
     */
    User findByNameAndPassword (String name, String password);
}

// Repository wird benötigt um Daten aus der Datenbank auszulesen und einzufügen.
// JpaRepository gibt an, dass die Spring Implementierung genutzt werden soll und dass UserRepository die Userklasse benutzen soll.