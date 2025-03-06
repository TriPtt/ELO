package fr.ynov.ELO.controller;

import fr.ynov.ELO.DTO.AnecdoteDTO;
import fr.ynov.ELO.entity.Anecdote;
import fr.ynov.ELO.service.AnecdoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/anecdotes")
@RequiredArgsConstructor
public class AnecdoteController {

    private final AnecdoteService anecdoteService;

    // Ajouter une nouvelle anecdote
    @PostMapping
    public ResponseEntity<?> createAnecdote(@RequestBody AnecdoteDTO anecdoteDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(anecdoteService.save(anecdoteDTO));
    }

    // Récupérer toutes les anecdotes
    @GetMapping
    public Iterable<Anecdote> getAnecdotes() {
        return anecdoteService.findAll();
    }

    // Récupérer une anecdote par son ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getAnecdoteById(@PathVariable int id) {
        Optional<Anecdote> optio = anecdoteService.findById(id);
        if(optio.isPresent()) {
            return ResponseEntity.ok(optio.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Anecdote non trouvée");
        }
    }

    // Supprimer une anecdote
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAnecdote(@PathVariable int id) {
        Optional<Anecdote> optio = anecdoteService.findById(id);
        if(optio.isPresent()) {
            anecdoteService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("Anecdote supprimée");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Anecdote non trouvée");
        }
    }

    // Mettre à jour une anecdote
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateAnecdote(@PathVariable int id, @RequestBody AnecdoteDTO anecdoteDTO) {
        try {
            return ResponseEntity.ok(anecdoteService.update(id, anecdoteDTO));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Anecdote non trouvée");
        }
    }
}
