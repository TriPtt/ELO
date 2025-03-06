package fr.ynov.ELO.controller;

import fr.ynov.ELO.entity.Anecdote;
import fr.ynov.ELO.entity.Livre;
import fr.ynov.ELO.mapper.LivreMapper;
import fr.ynov.ELO.repository.AnecdoteRepository;
import fr.ynov.ELO.repository.LivreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/livres")
@RequiredArgsConstructor
public class LivreController {

    private final LivreRepository livreRepository;
    private final LivreMapper livreMapper;

    private final AnecdoteRepository anecdoteRepository;

    // Ajouter un nouveau livre
    @PostMapping
    public ResponseEntity<?> createLivre(@RequestBody Livre livre) {
        // Créer une nouvelle liste pour contenir les anecdotes traitées
        List<Anecdote> anecdotesTraitees = new ArrayList<>();

        // Itérer sur la liste d’anecdotes donnée dans le livre
        for (Anecdote anecdote : livre.getAnecdotes()) {
            if (anecdote.getId() != 0) {
                Anecdote existingAnecdote =
                        anecdoteRepository.findById(anecdote.getId()).orElse(null);
                if (existingAnecdote != null) {
                    existingAnecdote.setLivre(livre);
                    anecdotesTraitees.add(existingAnecdote);
                }
            } else {
                anecdote.setLivre(livre);
                anecdotesTraitees.add(anecdote);
            }
        }

        // On remplace la liste d'anecdotes du livre par la nouvelle liste traitée
        livre.setAnecdotes(anecdotesTraitees);

        // Sauvegarder le livre et convertir vers DTO
        Livre savedLivre = livreRepository.save(livre);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(livreMapper.entityToDto(savedLivre));
    }


    // Récupérer tous les livres
    @GetMapping
    public ResponseEntity<List<?>> getAllLivres() {
        List<Livre> livres = (List<Livre>) livreRepository.findAll();
        if (livres.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(List.of("Aucun livre trouvé"));
        }
        return ResponseEntity.ok(livres.stream()
                .map(livreMapper::entityToDto)
                .toList());
    }

    // Supprimer un livre
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLivre(@PathVariable int id) {
        return livreRepository.findById(id)
                .map(livre -> {
                    livreRepository.delete(livre);
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Livre supprimé avec succès");
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livre non trouvé"));
    }

    // Récupérer un livre par son ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getLivreById(@PathVariable int id) {
        Optional<Livre> optio = livreRepository.findById(id) ;
        if(optio.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(livreMapper.entityToDto(optio.get())) ;
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livre non trouvé") ;
        }
    }

    // Modifier un livre
    @PutMapping("/{id}")
    public ResponseEntity<?> updateLivre(@PathVariable int id, @RequestBody Livre livreDetails) {
        Optional<Livre> optio = livreRepository.findById(id);

        if (optio.isPresent()) {
            Livre livre = optio.get();

            livre.getAnecdotes().clear();
            for (Anecdote anecdote : livreDetails.getAnecdotes()) {
                if (anecdote.getId() != 0) {
                    Anecdote existingAnecdote = anecdoteRepository.findById(anecdote.getId()).orElse(null);
                    if (existingAnecdote != null) {
                        livre.getAnecdotes().add(existingAnecdote);
                        existingAnecdote.setLivre(livre);
                    }
                } else {
                    anecdote.setLivre(livre);
                    livre.getAnecdotes().add(anecdote);
                }
            }
            Livre updatedLivre = livreRepository.save(livre);
            return ResponseEntity.ok(livreMapper.entityToDto(updatedLivre));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livre non trouvé");
        }
    }
}
