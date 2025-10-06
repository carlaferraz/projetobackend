package br.pucpr.projetobackend.controller;
import br.pucpr.projetobackend.dto.CommentDTO;

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
@RequestMapping("/api/v1/comments")
@Tag(name = "Comments", description = "Comments API - Movie Reviews")
public class CommentController {

    private List<CommentDTO> comments = new ArrayList<>();

    @PostMapping
    @Operation(summary = "Save a comment", description = "Create and save a new movie comment.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comment created successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid comment data.")
    })
    public ResponseEntity<CommentDTO> save(@Valid @RequestBody CommentDTO commentDTO) {
        // Simple ID generation based on list size
        commentDTO.setId(comments.size() + 1);
        comments.add(commentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentDTO);
    }

    @GetMapping
    @Operation(summary = "Get all comments", description = "Retrieve all registered comments.")
    @ApiResponse(responseCode = "200", description = "Comments retrieved successfully.")
    public List<CommentDTO> findAll() {
        return comments;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get comment by ID", description = "Retrieve a specific comment by its ID.")
    @ApiResponse(responseCode = "200", description = "Comment retrieved successfully.")
    public ResponseEntity<CommentDTO> findById(@PathVariable("id") Integer id) {
        for (CommentDTO c : comments) {
            if (c.getId().equals(id)) {
                return ResponseEntity.ok(c);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update comment by ID", description = "Update a specific comment by its ID.")
    @ApiResponse(responseCode = "200", description = "Comment updated successfully.")
    public ResponseEntity<CommentDTO> update(@PathVariable("id") Integer id, @RequestBody CommentDTO commentDTO) {
        for (CommentDTO c : comments) {
            if (c.getId().equals(id)) {
                c.setTitle(commentDTO.getTitle());
                c.setComment(commentDTO.getComment());
                c.setRating(commentDTO.getRating());
                return ResponseEntity.ok(c);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete comment by ID", description = "Remove a comment by its ID.")
    @ApiResponse(responseCode = "204", description = "Comment deleted successfully.")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        boolean removed = comments.removeIf(c -> c.getId().equals(id));
        if (removed) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
