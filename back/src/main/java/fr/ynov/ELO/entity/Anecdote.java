package fr.ynov.ELO.entity;

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

    @Enumerated(EnumType.STRING)
    private TypeAnecdote type;

    private String content;

    private String pays;

    @ManyToOne()
    @JoinColumn(name = "livre_id")
    private Livre livre;
}
