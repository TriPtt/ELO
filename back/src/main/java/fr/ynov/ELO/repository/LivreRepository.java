package fr.ynov.ELO.repository;

import fr.ynov.ELO.entity.Livre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivreRepository extends JpaRepository<Livre, Integer> {
}
