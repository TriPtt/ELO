package fr.ynov.ELO.repository;

import fr.ynov.ELO.entity.Livre;
import org.springframework.data.repository.CrudRepository;

public interface LivreRepository extends CrudRepository<Livre, Integer> {
}
