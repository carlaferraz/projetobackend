package br.pucpr.projetobackend.controller;

import br.pucpr.projetobackend.dto.MovieDTO;
import br.pucpr.projetobackend.dto.RatingDTO;
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
@RequestMapping("/api/v1/rating")
@Tag(name = "Movies Rating", description = "Movies Rating API")
public class RatingController {

    private final List<RatingDTO> ratings = new ArrayList<>();

    @GetMapping
    @Operation(summary = "Get ALL the movies rating list.", description = "Get ALL the movies rating list.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful GET all movies."),
    })
    public List<RatingDTO> findAll() {
        return ratings;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get rating by ID.", description = "Get rating by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful GET movie rating."),
            @ApiResponse(responseCode = "400", description = "The movie rating data are wrong."),
    })
    private RatingDTO findById(@PathVariable("id") Integer id) {
        for (RatingDTO tempRatingDTO : ratings) {
            if (tempRatingDTO.getId().equals(id)) {
                return tempRatingDTO;
            }
        }
        return null;
    }

    @GetMapping("/movie/{movieId}")
    @Operation(summary = "Get all ratings by movie ID.", description = "Get all ratings by movie ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful GET movie rating."),
            @ApiResponse(responseCode = "400", description = "The movie rating data are wrong."),
    })
    public RatingDTO findByMovieId(@PathVariable("movieId") Integer movieId) {
        for (RatingDTO tempRatingDTO : ratings) {
            if (tempRatingDTO.getMovieId().equals(movieId)) {
                return tempRatingDTO;
            }
        }
        return null;
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get all ratings by user ID.", description = "Get all ratings by movie ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful GET movie rating."),
            @ApiResponse(responseCode = "400", description = "The movie rating data are wrong."),
    })
    public List<RatingDTO> findByUserId(@PathVariable("userId") Integer userId) {
        List<RatingDTO> result = new ArrayList<>();

        for (RatingDTO tempRatingDTO : ratings) {
            if (tempRatingDTO.getUserId().equals(userId)) {
                result.add(tempRatingDTO);
            }
        }
        return result;
    }

    @PostMapping("/{movieId}")
    @Operation(summary = "Save a movie rating.", description = "Save a movie rating.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful movie rating saving - POST!"),
            @ApiResponse(responseCode = "400", description = "The movie rating data are wrong."),
    })
    public ResponseEntity<RatingDTO> save(@PathVariable Integer movieId, @Valid @RequestBody RatingDTO ratingDTO) {
        ratingDTO.setMovieId(movieId);
        ratings.add(ratingDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ratingDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a rating by ID", description = "Update a rating by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful Updating - PUT!"),
    })
    public RatingDTO update(@PathVariable("id") Integer id, @RequestBody RatingDTO ratingDTO) {
        for (RatingDTO tempRatingDTO : ratings) {
            if (
                    tempRatingDTO.getId().equals(id) &&
                    tempRatingDTO.getUserId().equals(ratingDTO.getUserId())
            ) {
                tempRatingDTO.setId(id);
                tempRatingDTO.setUserId(ratingDTO.getUserId());
                tempRatingDTO.setMovieId(ratingDTO.getMovieId());
                tempRatingDTO.setRating(ratingDTO.getRating());
                return tempRatingDTO;
            }
        }
        return null;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a movie rating by ID", description = "Delete a movie rating by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful Deleting - DELETE!"),
    })
    public void delete(@PathVariable("id") Integer id) {
        ratings.removeIf(ratingDTO -> id.equals(ratingDTO.getId()));
    }

}
