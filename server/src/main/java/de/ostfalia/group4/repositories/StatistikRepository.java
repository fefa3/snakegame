package de.ostfalia.group4.repositories;

import de.ostfalia.group4.models.Statistik;
import de.ostfalia.group4.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Datenbankanbindung der Statistiken
 */
public interface StatistikRepository extends JpaRepository<Statistik,Long> {
    /**
     * Alle Statistiken eines Nutzers zurückgeben
     * @param user Der Nutzer
     * @return Die Statistiken des Nutzers
     */
    List<Statistik> findAllByUser(User user);
}

