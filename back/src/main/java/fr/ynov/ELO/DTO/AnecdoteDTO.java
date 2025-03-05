package fr.ynov.ELO.DTO;

import fr.ynov.ELO.ressource.TypeAnecdote;
import lombok.Builder;

@Builder
public record AnecdoteDTO(TypeAnecdote type , String content, String pays) {
}
