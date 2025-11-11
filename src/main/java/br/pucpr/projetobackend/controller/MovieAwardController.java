package br.pucpr.projetobackend.controller;

import br.pucpr.projetobackend.dto.MovieAwardDTO;
import br.pucpr.projetobackend.service.MovieAwardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/awards")
@RequiredArgsConstructor
@Tag(name = "Movie Awards", description = "Movie Awards CRUD API")
public class MovieAwardController {

    private final MovieAwardService service;

    @GetMapping
    @Operation(summary = "List all movie awards")
    public List<MovieAwardDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find award by ID")
    public ResponseEntity<MovieAwardDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/movie/{movieId}")
    @Operation(summary = "List awards for a specific movie")
    public List<MovieAwardDTO> findByMovieId(@PathVariable Integer movieId) {
        return service.findByMovieId(movieId);
    }

    @PostMapping
    @Operation(summary = "Add new movie award")
    @ApiResponse(responseCode = "201", description = "Award created successfully")
    public ResponseEntity<MovieAwardDTO> save(@RequestBody MovieAwardDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update movie award")
    public ResponseEntity<MovieAwardDTO> update(@PathVariable Integer id, @RequestBody MovieAwardDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete movie award")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
