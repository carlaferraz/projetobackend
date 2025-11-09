package br.pucpr.projetobackend.controller;

import br.pucpr.projetobackend.dto.UserBioDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //responde requisicoes p json
@RequestMapping("/api/v1/biographies") //cria endereco das rotas
@Tag(name = "Biograhpies", description = "Biography for Author")
@AllArgsConstructor
public class UserBioController {
    private final br.pucpr.projetobackend.service.AuthorService authorService;
    

    // MARK: CREATE
    @PostMapping("/{authorId}")
    @Operation(summary = "Save a bio for an existing author", description = "Save a bio for an existing author")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful Saving - POST!"),
            @ApiResponse(responseCode = "400", description = "The data are wrong."),
            @ApiResponse(responseCode = "404", description = "Author not found."),
    })
    public ResponseEntity<UserBioDTO> save(@PathVariable Integer authorId, @Valid @RequestBody UserBioDTO bioDTO) {
        var author = authorService.getId(authorId);
        if (author == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        author.setBio(bioDTO.getBiography());
        authorService.save(author);
        bioDTO.setId(author.getId());
        bioDTO.setAuthorId(author.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(bioDTO);
    }


    // MARK: READ
    @GetMapping
    @Operation(summary = "Get ALL bios", description = "Get ALL bios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful GET all bios."),
    })
    public List<UserBioDTO> findAll() {
        return authorService.getAll()
                .stream()
                .map(a -> {
                    var dto = new UserBioDTO();
                    dto.setId(a.getId());
                    dto.setAuthorId(a.getId());
                    dto.setBiography(a.getBio());
                    return dto;
                })
                .toList();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get bio by ID", description = "Get bio by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful GET Bio."),
            @ApiResponse(responseCode = "404", description = "Bio not found."),
    })
    public UserBioDTO findById(@PathVariable("id") Integer id) {
        var author = authorService.getId(id);
        if (author == null) return null;
        var dto = new UserBioDTO();
        dto.setId(author.getId());
        dto.setAuthorId(author.getId());
        dto.setBiography(author.getBio());
        return dto;
    }

    @GetMapping("/author/{authorId}")
    @Operation(summary = "Get bio by author ID.", description = "Get bio by author ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful GET."),
            @ApiResponse(responseCode = "400", description = "The data are wrong."),
    })
    public List<UserBioDTO> findByAuthorId(@PathVariable("authorId") Integer authorId) {
        var author = authorService.getId(authorId);
        if (author == null) return List.of();
        var dto = new UserBioDTO();
        dto.setId(author.getId());
        dto.setAuthorId(author.getId());
        dto.setBiography(author.getBio());
        return List.of(dto);
    }

    //MARK: UPDATE
    @PutMapping("/{id}")
    @Operation(summary = "Update a BIO by ID", description = "Update a BIO by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful Updating - PUT!"),
    })
    public UserBioDTO update(@PathVariable("id") Integer id, @RequestBody UserBioDTO bioDTO) {
        var author = authorService.getId(id);
        if (author == null) return null;
        author.setBio(bioDTO.getBiography());
        authorService.save(author);
        var dto = new UserBioDTO();
        dto.setId(author.getId());
        dto.setAuthorId(author.getId());
        dto.setBiography(author.getBio());
        return dto;
    }

    //MARK: DELETE
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a movie by ID", description = "Delete a movie by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful Deleting - DELETE!"),
    })
    public void delete(@PathVariable("id") Integer id) {
        var author = authorService.getId(id);
        if (author != null) {
            author.setBio(null);
            authorService.save(author);
        }
    }
}

