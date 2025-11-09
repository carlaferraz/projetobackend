package br.pucpr.projetobackend.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "TAB_AUTHOR")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(length = 2000)
    private String bio;
}
