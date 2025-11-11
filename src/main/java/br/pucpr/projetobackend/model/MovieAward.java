package br.pucpr.projetobackend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "TAB_MOVIE_AWARDS")
public class MovieAward {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name; // Ex: "Oscar", "Globo de Ouro"

    private String category; // Ex: "Melhor Filme", "Melhor Diretor"

    private LocalDate awardDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;
}
