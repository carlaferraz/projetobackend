package br.pucpr.projetobackend.repository;

import br.pucpr.projetobackend.model.Movie;
import br.pucpr.projetobackend.model.MovieReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieReviewRepository extends JpaRepository<MovieReview, Integer> {
}