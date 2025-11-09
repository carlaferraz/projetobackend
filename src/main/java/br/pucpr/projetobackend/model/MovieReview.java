package br.pucpr.projetobackend.model;

import br.pucpr.projetobackend.dto.MovieDTO;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "TAB_REVIEW_MOVIES")
public class MovieReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer rating;
    private String comment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;
}
