package br.pucpr.projetobackend.controller;

import br.pucpr.projetobackend.dto.AuthorDTO;
import br.pucpr.projetobackend.model.Author;
import br.pucpr.projetobackend.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/authors")
@Tag(name = "Authors", description = "Authors API")
@AllArgsConstructor
public class AuthorController {

    private final AuthorService authorService;
    private final ModelMapper mapper = new ModelMapper();

    @PostMapping
    @Operation(summary = "Save an author", description = "Save a new author")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful Saving - POST!"),
            @ApiResponse(responseCode = "400", description = "The author data are wrong."),
    })
    public ResponseEntity<AuthorDTO> save(@Valid @RequestBody AuthorDTO authorDTO) {
        Author author = mapper.map(authorDTO, Author.class);
        var saved = authorService.save(author);
        authorDTO.setId(saved.getId());

        // biography is stored on Author.bio field (no separate AuthorBiography entity)
        return ResponseEntity.status(HttpStatus.CREATED).body(authorDTO);
    }

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> getAll(){
        var list = authorService.getAll().stream().map(a -> mapper.map(a, AuthorDTO.class)).toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> getById(@PathVariable Integer id){
        var a = authorService.getId(id);
        if (a == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(mapper.map(a, AuthorDTO.class));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorDTO> update(@PathVariable Integer id, @RequestBody AuthorDTO dto){
        var author = mapper.map(dto, Author.class);
        author.setId(id);
        var updated = authorService.update(author);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(mapper.map(updated, AuthorDTO.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        authorService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
