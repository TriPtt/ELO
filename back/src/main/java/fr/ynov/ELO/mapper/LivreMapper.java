package fr.ynov.ELO.mapper;

import fr.ynov.ELO.DTO.LivreDTO;
import fr.ynov.ELO.DTO.AnecdoteDTO;
import fr.ynov.ELO.entity.Livre;
import fr.ynov.ELO.entity.Anecdote;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LivreMapper {
    private final AnecdoteMapper anecdoteMapper;

    public LivreMapper(AnecdoteMapper anecdoteMapper) {
        this.anecdoteMapper = anecdoteMapper;
    }

    public Livre dtoToEntity(LivreDTO livreDTO) {
        return Livre.builder()
                .anecdotes(livreDTO.anecdotes() != null ?
                        livreDTO.anecdotes().stream()
                                .map(anecdoteMapper::dtoToEntity)
                                .collect(Collectors.toList())
                        : null)
                .build();
    }

    public LivreDTO entityToDto(Livre livre) {
        return LivreDTO.builder()
                .anecdotes(livre.getAnecdotes() != null ?
                        livre.getAnecdotes().stream()
                                .map(anecdoteMapper::entityToDto)
                                .collect(Collectors.toList())
                        : null)
                .build();
    }
}