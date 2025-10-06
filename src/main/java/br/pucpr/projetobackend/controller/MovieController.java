package br.pucpr.projetobackend.controller;
import br.pucpr.projetobackend.dto.MovieDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/movies")
@Tag(name = "Movies Now", description = "Movies API")
public class MovieController {

    private List<MovieDTO> movies = new ArrayList<>();


    @PostMapping
    @Operation(summary = "Save a movie", description = "Save a movie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful Saving - POST!"),
            @ApiResponse(responseCode = "400", description = "The movie data are wrong."),
    })
    public ResponseEntity<MovieDTO> save(@Valid @RequestBody MovieDTO movieDTO) {
        movies.add(movieDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(movieDTO);
    }


    @GetMapping
    @Operation(summary = "Get ALL the movies list.", description = "Get ALL the movies list.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful GET all movies."),
    })
    public List<MovieDTO> findAll() {
        return movies;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get movie by ID.", description = "Get movie by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful GET movie."),
    })
    public MovieDTO findById(@PathVariable("id") Integer id) {
        for (MovieDTO tempMovieDTO : movies) {
            if (tempMovieDTO.getId().equals(id)) {
                return tempMovieDTO;
            }
        }

        return null;
    }


    @PutMapping("/{id}")
    @Operation(summary = "Update a movie by ID", description = "Update a movie by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful Updating - PUT!"),
    })
    public MovieDTO update(@PathVariable("id") Integer id, @RequestBody MovieDTO movieDTO) {
        for (MovieDTO tempMovieDTO : movies) {
            if (tempMovieDTO.getId().equals(id)) {
                tempMovieDTO.setId(movieDTO.getId());
                tempMovieDTO.setTitle(movieDTO.getTitle());
                return tempMovieDTO;
            }
        }

        return null;

    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a movie by ID", description = "Delete a movie by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful Deleting - DELETE!"),
    })
    public void delete(@PathVariable("id") Integer id) {
        movies.removeIf(movieDTO -> id.equals(movieDTO.getId()));
    }
}
