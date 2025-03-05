package fr.ynov.ELO.DTO;

import lombok.Builder;

import java.util.List;

@Builder
public record LivreDTO(List<AnecdoteDTO> anecdotes) {
}
