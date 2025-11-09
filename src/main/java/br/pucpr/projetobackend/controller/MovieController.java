package br.pucpr.projetobackend.controller;
import br.pucpr.projetobackend.dto.MovieDTO;
import br.pucpr.projetobackend.service.MovieService;
import br.pucpr.projetobackend.service.AuthorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/movies")
@Tag(name = "Movies Now", description = "Movies API")
public class MovieController {

    private final MovieService movieService;
    private final AuthorService authorService;
    private final org.modelmapper.ModelMapper mapper = new org.modelmapper.ModelMapper();

    public MovieController(MovieService movieService, AuthorService authorService) {
        this.movieService = movieService;
        this.authorService = authorService;
    }


    @PostMapping
    @Operation(summary = "Save a movie", description = "Save a movie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful Saving - POST!"),
            @ApiResponse(responseCode = "400", description = "The movie data are wrong."),
    })
    public ResponseEntity<MovieDTO> save(@Valid @RequestBody MovieDTO movieDTO) {
        var movie = mapper.map(movieDTO, br.pucpr.projetobackend.model.Movie.class);
        if (movieDTO.getAuthorId() != null) {
            var author = authorService.getId(movieDTO.getAuthorId());
            movie.setAuthor(author);
        }
        movieService.save(movie);
        movieDTO.setId(movie.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(movieDTO);
    }


    @GetMapping
    @Operation(summary = "Get ALL the movies list.", description = "Get ALL the movies list.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful GET all movies."),
    })
    public List<MovieDTO> findAll() {
        return movieService.getAll()
                .stream()
                .map(m -> {
                    var dto = mapper.map(m, MovieDTO.class);
                    if (m.getAuthor() != null) dto.setAuthorId(m.getAuthor().getId());
                    if (m.getActors() != null && !m.getActors().isEmpty()) {
                        dto.setActorIds(m.getActors().stream().map(a -> a.getId()).toList());
                    }
                    return dto;
                })
                .toList();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get movie by ID.", description = "Get movie by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful GET movie."),
    })
    public MovieDTO findById(@PathVariable("id") Integer id) {
        var movie = movieService.getId(id);
        var dto = mapper.map(movie, MovieDTO.class);
        if (movie.getAuthor() != null) dto.setAuthorId(movie.getAuthor().getId());
        if (movie.getActors() != null && !movie.getActors().isEmpty()) {
            dto.setActorIds(movie.getActors().stream().map(a -> a.getId()).toList());
        }
        return dto;
    }


    @PutMapping("/{id}")
    @Operation(summary = "Update a movie by ID", description = "Update a movie by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful Updating - PUT!"),
    })
    public MovieDTO update(@PathVariable("id") Integer id, @RequestBody MovieDTO movieDTO) {
        var movie = mapper.map(movieDTO, br.pucpr.projetobackend.model.Movie.class);
        movie.setId(id);
        if (movieDTO.getAuthorId() != null) {
            var author = authorService.getId(movieDTO.getAuthorId());
            movie.setAuthor(author);
        }
        var updated = movieService.update(movie);
        var dto = mapper.map(updated, MovieDTO.class);
        if (updated.getAuthor() != null) dto.setAuthorId(updated.getAuthor().getId());
        if (updated.getActors() != null && !updated.getActors().isEmpty()) {
            dto.setActorIds(updated.getActors().stream().map(a -> a.getId()).toList());
        }
        return dto;
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a movie by ID", description = "Delete a movie by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful Deleting - DELETE!"),
    })
    public void delete(@PathVariable("id") Integer id) {
        movieService.delete(id);
    }
}
