package br.pucpr.projetobackend.repository;

import br.pucpr.projetobackend.model.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WatchlistRepository extends JpaRepository<Watchlist, Integer> {
    List<Watchlist> findByUserId(Integer userId);
    List<Watchlist> findByMovieId(Integer movieId);
}
