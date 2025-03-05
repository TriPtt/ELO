package fr.ynov.ELO.repository;

import fr.ynov.ELO.entity.Anecdote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnecdoteRepository extends JpaRepository<Anecdote, Integer> {
}
