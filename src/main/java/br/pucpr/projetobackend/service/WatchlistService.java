package br.pucpr.projetobackend.service;

import br.pucpr.projetobackend.model.Movie;
import br.pucpr.projetobackend.model.User;
import br.pucpr.projetobackend.model.Watchlist;
import br.pucpr.projetobackend.repository.MovieRepository;
import br.pucpr.projetobackend.repository.UserRepository;
import br.pucpr.projetobackend.repository.WatchlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WatchlistService {

    @Autowired
    private WatchlistRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    public Watchlist save(Watchlist watchlist) {
        return repository.save(watchlist);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }

    public Watchlist getById(Integer id) {
        Optional<Watchlist> watchlist = repository.findById(id);
        return watchlist.orElse(null);
    }

    public List<Watchlist> getAll() {
        return repository.findAll();
    }

    public List<Watchlist> getByUserId(Integer userId) {
        return repository.findByUserId(userId);
    }

    public List<Watchlist> getByMovieId(Integer movieId) {
        return repository.findByMovieId(movieId);
    }

    public Watchlist updateStatus(Integer id, Watchlist.Status newStatus) {
        Watchlist watchlist = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item não encontrado na Watchlist"));
        watchlist.setStatus(newStatus);
        return repository.save(watchlist);
    }
}
