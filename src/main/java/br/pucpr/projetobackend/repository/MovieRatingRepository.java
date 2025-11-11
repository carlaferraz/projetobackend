package br.pucpr.projetobackend.repository;

import br.pucpr.projetobackend.model.MovieRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRatingRepository extends JpaRepository<MovieRating, Integer> {
    List<MovieRating> findByMovieId(Integer movieId);
    List<MovieRating> findByUserId(Integer userId);
}