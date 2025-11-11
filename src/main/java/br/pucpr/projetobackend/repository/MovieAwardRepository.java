package br.pucpr.projetobackend.repository;

import br.pucpr.projetobackend.model.MovieAward;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieAwardRepository extends JpaRepository<MovieAward, Integer> {
    List<MovieAward> findByMovieId(Integer movieId);
}
