package br.pucpr.projetobackend.controller;

import br.pucpr.projetobackend.dto.RatingDTO;
import br.pucpr.projetobackend.model.MovieRating;
import br.pucpr.projetobackend.service.MovieRatingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ratings")
@RequiredArgsConstructor
@Tag(name = "Movies Rating", description = "Movies Rating API")
public class MovieRatingController {

    private final MovieRatingService service;

    @GetMapping
    @Operation(summary = "Get all ratings", description = "Returns all movie ratings")
    public List<RatingDTO> findAll() {
        return service.findAll().stream().map(RatingDTO::fromEntity).toList();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get rating by ID")
    public ResponseEntity<RatingDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(RatingDTO.fromEntity(service.findById(id)));
    }

    @GetMapping("/movie/{movieId}")
    @Operation(summary = "Get all ratings for a movie")
    public List<RatingDTO> findByMovieId(@PathVariable Integer movieId) {
        return service.findByMovieId(movieId).stream().map(RatingDTO::fromEntity).toList();
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get all ratings made by a user")
    public List<RatingDTO> findByUserId(@PathVariable Integer userId) {
        return service.findByUserId(userId).stream().map(RatingDTO::fromEntity).toList();
    }

    @PostMapping
    @Operation(summary = "Add a new rating")
    @ApiResponse(responseCode = "201", description = "Rating created successfully")
    public ResponseEntity<RatingDTO> save(@Valid @RequestBody RatingDTO dto) {
        var saved = service.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(RatingDTO.fromEntity(saved));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a rating by ID")
    public ResponseEntity<RatingDTO> update(@PathVariable Integer id, @RequestBody RatingDTO dto) {
        var updated = service.update(id, dto);
        return ResponseEntity.ok(RatingDTO.fromEntity(updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a rating by ID")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}