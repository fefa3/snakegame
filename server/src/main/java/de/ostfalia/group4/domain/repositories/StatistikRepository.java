package de.ostfalia.group4.domain.repositories;

import de.ostfalia.group4.models.Statistik;
import de.ostfalia.group4.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatistikRepository extends JpaRepository<Statistik,Long> {
}
