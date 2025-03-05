package fr.ynov.ELO.mapper;

import fr.ynov.ELO.DTO.AnecdoteDTO;
import fr.ynov.ELO.entity.Anecdote;
import org.springframework.stereotype.Component;

@Component
public class AnecdoteMapper {
    public Anecdote dtoToEntity(AnecdoteDTO anecdoteDTO){
        return Anecdote.builder()
                .content(anecdoteDTO.content())
                .pays(anecdoteDTO.pays())
                .type(anecdoteDTO.type())
                .build();
    }
    public AnecdoteDTO entityToDto(Anecdote anecdote){
        return AnecdoteDTO.builder()
                .content(anecdote.getContent())
                .pays(anecdote.getPays())
                .type(anecdote.getType())
                .build();
    }
}
