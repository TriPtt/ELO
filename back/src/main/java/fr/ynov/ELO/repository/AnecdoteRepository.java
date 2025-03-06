package fr.ynov.ELO.repository;

import fr.ynov.ELO.entity.Anecdote;
import org.springframework.data.repository.CrudRepository;

public interface AnecdoteRepository extends CrudRepository<Anecdote, Integer> {
}
