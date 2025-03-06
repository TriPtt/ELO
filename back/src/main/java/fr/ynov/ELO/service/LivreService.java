package fr.ynov.ELO.service;

import fr.ynov.ELO.DTO.LivreDTO;
import fr.ynov.ELO.entity.Anecdote;
import fr.ynov.ELO.entity.Livre;
import fr.ynov.ELO.mapper.LivreMapper;
import fr.ynov.ELO.repository.LivreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class LivreService {

    private final LivreMapper livreMapper;
    private final LivreRepository livreRepository;
    private final AnecdoteService anecdoteService;

    public ResponseEntity<?> save(LivreDTO livreDTO){
        return ResponseEntity.ok(livreRepository.save(livreMapper.dtoToEntity(livreDTO))) ;
    }

    public Optional<Livre> findById(Integer id) {
        return livreRepository.findById(id);
    }

    public List<?> getAll() {
        Iterable<Livre> iterableLivres = livreRepository.findAll();
        List<Livre> livres = StreamSupport.stream(iterableLivres.spliterator(), false)
                .toList();
        if (livres.isEmpty()) {
            return Collections.emptyList();
        }
        livres.forEach(livre -> {
            if (livre.getAnecdotes() != null) {
                livre.getAnecdotes().forEach(anecdote -> anecdote.setLivre(null));
            }
        });
        return livres.stream()
                .map(livreMapper::entityToDto)
                .collect(Collectors.toList());
    }

    public void delete(Integer id){
        livreRepository.deleteById(id);
    }

    public Optional<?> update(int id, Livre livreDetails) {
        // On recherche le livre existant avec son id
        Optional<Livre> optionalLivre = livreRepository.findById(id);
        if (optionalLivre.isEmpty()) {
            return Optional.empty();
        }
        Livre livre = optionalLivre.get();
        // Mise à jour des anecdotes :
        // On vide la liste existante pour éviter des doublons ou les anciennes données
        livre.getAnecdotes().clear();
        for (Anecdote anecdote : livreDetails.getAnecdotes()) {
            if (anecdote.getId() != 0) {
                // Si l'anecdote existe déjà, on utilise celle-ci
                Anecdote existingAnecdote = anecdoteService.findById(anecdote.getId()).orElse(null);
                if (existingAnecdote != null) {
                    livre.getAnecdotes().add(existingAnecdote);
                    existingAnecdote.setLivre(livre);
                }
            } else {
                // Sinon, on crée une nouvelle anecdote et on l'associe au livre
                anecdote.setLivre(livre);
                livre.getAnecdotes().add(anecdote);
            }
        }
        // Vous pouvez aussi effectuer ici d'autres mises à jour sur d'autres champs du livre si besoin

        // Enregistrement en base et mapping en DTO
        Livre updatedLivre = livreRepository.save(livre);
        return Optional.of(livreMapper.entityToDto(updatedLivre));
    }
}
