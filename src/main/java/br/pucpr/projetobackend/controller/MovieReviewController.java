package br.pucpr.projetobackend.controller;

import br.pucpr.projetobackend.dto.MovieReviewDTO;
import br.pucpr.projetobackend.dto.UserDTO;
import br.pucpr.projetobackend.exception.BusinessException;
import br.pucpr.projetobackend.model.Movie;
import br.pucpr.projetobackend.model.MovieReview;
import br.pucpr.projetobackend.model.User;
import br.pucpr.projetobackend.service.MovieReviewService;
import br.pucpr.projetobackend.service.MovieService;
import br.pucpr.projetobackend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
@Tag(name = "Movies Reviews", description = "Movies Reviews API")
public class MovieReviewController {

    private final MovieReviewService movieReviewService;
    private final UserService userService;
    private final MovieService movieService;
    private final ModelMapper mapper = new ModelMapper();

    public MovieReviewController(MovieReviewService movieReviewService,
                                 UserService userService,
                                 MovieService movieService) {
        this.movieReviewService = movieReviewService;
        this.userService = userService;
        this.movieService = movieService;
    }

    @PostMapping
    @Operation(summary = "Save a movie review", description = "Save a movie review")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful Saving - POST!"),
            @ApiResponse(responseCode = "400", description = "The movie review data are wrong."),
    })
    public ResponseEntity<MovieReviewDTO> save(@Valid @RequestBody MovieReviewDTO movieReviewDTO) {

        User user = userService.getId(movieReviewDTO.getUserId());
        Movie movie = movieService.getId(movieReviewDTO.getMovieId());

        MovieReview review = new MovieReview();
        review.setComment(movieReviewDTO.getComment());
        review.setUser(user);
        review.setMovie(movie);

        movieReviewService.save(review);

        movieReviewDTO.setId(review.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(movieReviewDTO);
    }

    @GetMapping
    @Operation(summary = "Get ALL the movies reviews list.", description = "Get ALL the movies reviews list.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful GET all movies reviews."),
    })
    public ResponseEntity<List<MovieReviewDTO>> getAll() {
        List<MovieReviewDTO> list = movieReviewService.getAll()
                .stream()
                .map(r -> mapper.map(r, MovieReviewDTO.class))
                .toList();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get movie review by ID.", description = "Get movie review by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful GET movie review."),
    })
    public ResponseEntity<MovieReviewDTO> findById(@PathVariable Integer id) {
        MovieReview review = movieReviewService.getId(id);
        return ResponseEntity.ok(mapper.map(review, MovieReviewDTO.class));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a movie review by ID", description = "Update a movie review by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful Updating - PUT!"),
    })
    public ResponseEntity<MovieReviewDTO> update(@PathVariable Integer id,
                                                 @RequestBody MovieReviewDTO dto) throws BusinessException {

        if (id == null || dto.getId() == null)
            throw new BusinessException("ID_REQUIRED","The ID is required");

        MovieReview review = mapper.map(dto, MovieReview.class);
        movieReviewService.update(review);

        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a movie review by ID", description = "Delete a movie review by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful Deleting - DELETE!"),
    })
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        movieReviewService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
