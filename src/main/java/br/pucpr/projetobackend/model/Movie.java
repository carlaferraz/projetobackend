package br.pucpr.projetobackend.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "TAB_MOVIES")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String title;
}
