package br.pucpr.projetobackend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "TAB_ACTOR")
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(length = 100)
    private String nationality;

    @Column(length = 2000)
    private String biography;

    @Column(length = 500)
    private String photoUrl;

    @ManyToMany(mappedBy = "actors")
    private List<Movie> movies;
}

