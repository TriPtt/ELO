package fr.ynov.ELO.service;
import fr.ynov.ELO.DTO.AnecdoteDTO;
import fr.ynov.ELO.entity.Anecdote;
import fr.ynov.ELO.mapper.AnecdoteMapper;
import fr.ynov.ELO.repository.AnecdoteRepository;
import fr.ynov.ELO.ressource.TypeAnecdote;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnecdoteService {

    private final AnecdoteRepository anecdoteRepository;
    private final AnecdoteMapper anecdoteMapper ;

    public Iterable<Anecdote> findAll(){
        return anecdoteRepository.findAll();
    }

    public Optional<Anecdote> findById(Integer id) {
        return anecdoteRepository.findById(id);
    }

    public ResponseEntity<?> save(AnecdoteDTO anecdoteDTO){
        if(anecdoteDTO.content() == null || anecdoteDTO.pays() == null || anecdoteDTO.type() == null){
            return ResponseEntity.badRequest().body("Un des champs n'est pas valide");
        }
        return ResponseEntity.ok(anecdoteRepository.save(anecdoteMapper.dtoToEntity(anecdoteDTO))) ;
    }

    public void delete(Integer id){
        anecdoteRepository.deleteById(id);
    }

    public Anecdote update(int id, AnecdoteDTO anecdoteDTO) {
        Anecdote anecdote = anecdoteRepository.findById(id).orElseThrow();
        if (anecdoteDTO.content() != null) {
            anecdote.setContent(anecdoteDTO.content());
        }
        if (anecdoteDTO.type() != null) {
            anecdote.setType(anecdoteDTO.type());
        }
        if (anecdoteDTO.pays() != null) {
            anecdote.setPays(anecdoteDTO.pays());
        }
        return anecdoteRepository.save(anecdote);
    }

}
