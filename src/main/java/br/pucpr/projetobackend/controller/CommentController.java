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

    private final List<CommentDTO> comments = new ArrayList<>();

    @GetMapping
    @Operation(summary = "Get all comments", description = "Retrieve all movie comments.")
    @ApiResponse(responseCode = "200", description = "Comments retrieved successfully.")
    public List<CommentDTO> findAll() {
        return comments;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get comment by ID", description = "Retrieve a comment by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment found successfully."),
            @ApiResponse(responseCode = "404", description = "Comment not found.")
    })
    public CommentDTO findById(@PathVariable("id") Integer id) {
        for (CommentDTO tempComment : comments) {
            if (tempComment.getId().equals(id)) {
                return tempComment;
            }
        }
        return null;
    }

    @GetMapping("/movie/{movieId}")
    @Operation(summary = "Get all comments by movie ID", description = "Retrieve all comments for a specific movie.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comments found successfully."),
            @ApiResponse(responseCode = "404", description = "No comments found for this movie.")
    })
    public List<CommentDTO> findByMovieId(@PathVariable("movieId") Integer movieId) {
        List<CommentDTO> result = new ArrayList<>();
        for (CommentDTO tempComment : comments) {
            if (tempComment.getMovieId().equals(movieId)) {
                result.add(tempComment);
            }
        }
        return result;
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get all comments by user ID", description = "Retrieve all comments made by a specific user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comments found successfully."),
            @ApiResponse(responseCode = "404", description = "No comments found for this user.")
    })
    public List<CommentDTO> findByUserId(@PathVariable("userId") Integer userId) {
        List<CommentDTO> result = new ArrayList<>();
        for (CommentDTO tempComment : comments) {
            if (tempComment.getUserId().equals(userId)) {
                result.add(tempComment);
            }
        }
        return result;
    }

    @PostMapping("/{movieId}")
    @Operation(summary = "Save a comment for a movie", description = "Create and save a new comment related to a movie.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comment created successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid comment data.")
    })
    public ResponseEntity<CommentDTO> save(@PathVariable Integer movieId, @Valid @RequestBody CommentDTO commentDTO) {
        commentDTO.setMovieId(movieId);
        commentDTO.setId(comments.size() + 1); // simple ID generation
        comments.add(commentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a comment by ID", description = "Update a specific comment by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment updated successfully."),
            @ApiResponse(responseCode = "404", description = "Comment not found.")
    })
    public ResponseEntity<CommentDTO> update(@PathVariable("id") Integer id, @RequestBody CommentDTO commentDTO) {
        for (CommentDTO tempComment : comments) {
            if (
                    tempComment.getId().equals(id) &&
                            tempComment.getUserId().equals(commentDTO.getUserId())
            ) {
                tempComment.setTitle(commentDTO.getTitle());
                tempComment.setComment(commentDTO.getComment());
                tempComment.setMovieId(commentDTO.getMovieId());
                return ResponseEntity.ok(tempComment);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a comment by ID", description = "Remove a comment by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Comment deleted successfully."),
            @ApiResponse(responseCode = "404", description = "Comment not found.")
    })
    public void delete(@PathVariable("id") Integer id) {
        comments.removeIf(comment -> id.equals(comment.getId()));
    }
}
