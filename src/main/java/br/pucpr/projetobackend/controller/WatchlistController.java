package br.pucpr.projetobackend.controller;

import br.pucpr.projetobackend.model.Watchlist;
import br.pucpr.projetobackend.service.WatchlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/watchlist")
public class WatchlistController {

    @Autowired
    private WatchlistService service;

    @PostMapping
    public ResponseEntity<Watchlist> create(@RequestBody Watchlist watchlist) {
        return ResponseEntity.ok(service.save(watchlist));
    }

    @GetMapping
    public ResponseEntity<List<Watchlist>> listAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Watchlist> getById(@PathVariable Integer id) {
        Watchlist watchlist = service.getById(id);
        return watchlist != null ? ResponseEntity.ok(watchlist) : ResponseEntity.notFound().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Watchlist>> getByUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(service.getByUserId(userId));
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<Watchlist>> getByMovie(@PathVariable Integer movieId) {
        return ResponseEntity.ok(service.getByMovieId(movieId));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Watchlist> updateStatus(@PathVariable Integer id, @RequestBody Watchlist watchlist) {
        return ResponseEntity.ok(service.updateStatus(id, watchlist.getStatus()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
