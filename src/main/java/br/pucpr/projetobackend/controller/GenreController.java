package br.pucpr.projetobackend.controller;

import br.pucpr.projetobackend.dto.GenreDTO;
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

@RestController
@RequestMapping("/api/v1/genres")
@Tag(name = "Genres", description = "Genres API")
public class GenreController {

    private List<GenreDTO> genres = new ArrayList<>();


    @PostMapping
    @Operation(summary = "Save a genre", description = "Save a new genre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful Saving - POST!"),
            @ApiResponse(responseCode = "400", description = "The genre data are wrong."),
    })
    public ResponseEntity<GenreDTO> save(@Valid @RequestBody GenreDTO genreDTO) {
        genres.add(genreDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(genreDTO);
    }


    @GetMapping
    @Operation(summary = "Get ALL genres", description = "Get ALL genres")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful GET all genres."),
    })
    public List<GenreDTO> findAll() {
        return genres;
    }


    @GetMapping("/{id}")
    @Operation(summary = "Get genre by ID", description = "Get genre by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful GET genre."),
            @ApiResponse(responseCode = "404", description = "Genre not found."),
    })
    public ResponseEntity<GenreDTO> findById(@PathVariable("id") Integer id) {
        for (GenreDTO genre : genres) {
            if (genre.getId().equals(id)) {
                return ResponseEntity.ok(genre);
            }
        }
        return ResponseEntity.notFound().build();
    }


    @PutMapping("/{id}")
    @Operation(summary = "Update a genre by ID", description = "Update a genre by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Updating - PUT!"),
            @ApiResponse(responseCode = "404", description = "Genre not found."),
    })
    public ResponseEntity<GenreDTO> update(@PathVariable("id") Integer id, @Valid @RequestBody GenreDTO genreDTO) {
        for (GenreDTO genre : genres) {
            if (genre.getId().equals(id)) {
                genre.setName(genreDTO.getName());
                return ResponseEntity.ok(genre);
            }
        }
        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a genre by ID", description = "Delete a genre by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful Deleting - DELETE!"),
            @ApiResponse(responseCode = "404", description = "Genre not found."),
    })
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        boolean removed = genres.removeIf(g -> id.equals(g.getId()));
        return removed ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
