package fr.ynov.ELO.controller;

import fr.ynov.ELO.DTO.LivreDTO;
import fr.ynov.ELO.entity.Anecdote;
import fr.ynov.ELO.entity.Livre;
import fr.ynov.ELO.mapper.LivreMapper;
import fr.ynov.ELO.repository.AnecdoteRepository;
import fr.ynov.ELO.repository.LivreRepository;
import fr.ynov.ELO.service.LivreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/livres")
@RequiredArgsConstructor
public class LivreController {

    private final LivreRepository livreRepository;
    private final LivreMapper livreMapper;

    private final AnecdoteRepository anecdoteRepository;

    private final LivreService livreService;

    // Ajouter un nouveau livre
    @PostMapping
    public ResponseEntity<?> createLivre(@RequestBody LivreDTO livreDTO) {
        return ResponseEntity.ok(livreService.save(livreDTO));
    }

    // Récupérer tous les livres
    @GetMapping
    public ResponseEntity<List<?>> getAllLivres() {
        List<?> livresDto = livreService.getAll();
        return ResponseEntity.ok(livresDto);
    }

    // Supprimer un livre
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLivre(@PathVariable int id) {
        Optional<Livre> optio = livreService.findById(id);
        if(optio.isPresent()) {
            livreService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("Livre supprimé");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livre non trouvé");
        }
    }

    // Récupérer un livre par son ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getLivreById(@PathVariable int id) {
        Optional<Livre> optio = livreService.findById(id);
        if(optio.isPresent()) {
            return ResponseEntity.ok(optio.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livre non trouvé");
        }
    }

    // Modifier un livre
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateLivre(@PathVariable int id, @RequestBody Livre livreDetails) {
        Optional<?> updatedLivreDto = livreService.update(id, livreDetails);
        if (updatedLivreDto.isPresent()) {
            return ResponseEntity.ok(updatedLivreDto.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livre non trouvé");
        }
    }
}
