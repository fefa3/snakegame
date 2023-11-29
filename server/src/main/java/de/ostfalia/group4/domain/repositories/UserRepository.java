package de.ostfalia.group4.domain.repositories;

import de.ostfalia.group4.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long>{
    User findByName (String name);
}

// Repository wird benötigt um Daten aus der Datenbank auszulesen und einzufügen.
// JpaRepository gibt an, dass die Spring Implementierung genutzt werden soll und dass UserRepository die Userklasse benutzen soll.