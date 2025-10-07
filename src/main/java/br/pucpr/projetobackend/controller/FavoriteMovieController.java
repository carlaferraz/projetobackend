package br.pucpr.projetobackend.controller;

import br.pucpr.projetobackend.dto.FavoriteMovieDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/favorite-movies")
@Tag(name = "Favorite Movies", description = "Favorite Movies API")
public class FavoriteMovieController {

    private List<FavoriteMovieDTO> favoriteMovies = new ArrayList<>();

    @PostMapping
    @Operation(summary = "Add a movie to favorites", description = "Add a movie to user's favorites list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Movie successfully added to favorites"),
            @ApiResponse(responseCode = "400", description = "Invalid favorite movie data"),
    })
    public ResponseEntity<FavoriteMovieDTO> addToFavorites(@Valid @RequestBody FavoriteMovieDTO favoriteMovieDTO) {
        favoriteMovies.add(favoriteMovieDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(favoriteMovieDTO);
    }

    @GetMapping
    @Operation(summary = "Get all favorite movies", description = "Get all favorite movies from all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all favorite movies"),
    })
    public List<FavoriteMovieDTO> getAllFavoriteMovies() {
        return favoriteMovies;
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get favorite movies by user ID", description = "Get all favorite movies for a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user's favorite movies"),
    })
    public List<FavoriteMovieDTO> getFavoriteMoviesByUser(@PathVariable("userId") Integer userId) {
        return favoriteMovies.stream()
                .filter(fav -> fav.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get favorite movie by ID", description = "Get a specific favorite movie by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved favorite movie"),
            @ApiResponse(responseCode = "404", description = "Favorite movie not found"),
    })
    public ResponseEntity<FavoriteMovieDTO> getFavoriteMovieById(@PathVariable("id") Integer id) {
        for (FavoriteMovieDTO favoriteMovie : favoriteMovies) {
            if (favoriteMovie.getId().equals(id)) {
                return ResponseEntity.ok(favoriteMovie);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update favorite movie", description = "Update a favorite movie by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Favorite movie successfully updated"),
            @ApiResponse(responseCode = "404", description = "Favorite movie not found"),
    })
    public ResponseEntity<FavoriteMovieDTO> updateFavoriteMovie(@PathVariable("id") Integer id, 
                                                               @RequestBody FavoriteMovieDTO favoriteMovieDTO) {
        for (FavoriteMovieDTO favoriteMovie : favoriteMovies) {
            if (favoriteMovie.getId().equals(id)) {
                favoriteMovie.setMovieId(favoriteMovieDTO.getMovieId());
                favoriteMovie.setUserId(favoriteMovieDTO.getUserId());
                favoriteMovie.setMovieTitle(favoriteMovieDTO.getMovieTitle());
                favoriteMovie.setNotes(favoriteMovieDTO.getNotes());
                return ResponseEntity.ok(favoriteMovie);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove favorite movie", description = "Remove a movie from favorites by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Favorite movie successfully removed"),
            @ApiResponse(responseCode = "404", description = "Favorite movie not found"),
    })
    public ResponseEntity<Void> removeFromFavorites(@PathVariable("id") Integer id) {
        boolean removed = favoriteMovies.removeIf(favoriteMovie -> id.equals(favoriteMovie.getId()));
        return removed ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/user/{userId}/movie/{movieId}")
    @Operation(summary = "Remove specific movie from user's favorites", description = "Remove a specific movie from a user's favorites list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Movie successfully removed from user's favorites"),
            @ApiResponse(responseCode = "404", description = "Favorite movie not found"),
    })
    public ResponseEntity<Void> removeMovieFromUserFavorites(@PathVariable("userId") Integer userId, 
                                                           @PathVariable("movieId") Integer movieId) {
        boolean removed = favoriteMovies.removeIf(favoriteMovie -> 
            favoriteMovie.getUserId().equals(userId) && favoriteMovie.getMovieId().equals(movieId));
        return removed ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
