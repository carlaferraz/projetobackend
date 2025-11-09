package br.pucpr.projetobackend.controller;

import br.pucpr.projetobackend.dto.ActorDTO;
import br.pucpr.projetobackend.service.ActorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/actors")
@AllArgsConstructor
@Tag(name = "Actors", description = "Actor management endpoints")
public class ActorController {

    private final ActorService actorService;

    @PostMapping
    @Operation(summary = "Create a new actor")
    public ResponseEntity<ActorDTO> create(@Valid @RequestBody ActorDTO actorDTO) {
        ActorDTO created = actorService.create(actorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    @Operation(summary = "Get all actors")
    public ResponseEntity<List<ActorDTO>> findAll() {
        return ResponseEntity.ok(actorService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get actor by ID")
    public ResponseEntity<ActorDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(actorService.findById(id));
    }

    @GetMapping("/search")
    @Operation(summary = "Search actors by name")
    public ResponseEntity<List<ActorDTO>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(actorService.searchByName(name));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an actor")
    public ResponseEntity<ActorDTO> update(@PathVariable Integer id, @Valid @RequestBody ActorDTO actorDTO) {
        return ResponseEntity.ok(actorService.update(id, actorDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an actor")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        actorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{actorId}/movies/{movieId}")
    @Operation(summary = "Add actor to movie")
    public ResponseEntity<Void> addActorToMovie(@PathVariable Integer actorId, @PathVariable Integer movieId) {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{actorId}/movies/{movieId}")
    @Operation(summary = "Remove actor from movie")
    public ResponseEntity<Void> removeActorFromMovie(@PathVariable Integer actorId, @PathVariable Integer movieId) {
        return ResponseEntity.noContent().build();
    }
}

