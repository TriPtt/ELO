package fr.ynov.ELO.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import fr.ynov.ELO.ressource.TypeAnecdote;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Anecdote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TypeAnecdote type;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String pays;

    @ManyToOne()
    @JoinColumn(name = "livre_id")
    @JsonBackReference
    private Livre livre;
}
