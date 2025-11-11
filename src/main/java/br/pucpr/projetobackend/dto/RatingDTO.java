package br.pucpr.projetobackend.dto;

import br.pucpr.projetobackend.model.Movie;
import br.pucpr.projetobackend.model.MovieRating;
import br.pucpr.projetobackend.model.User;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingDTO {

    private Integer id;

    @NotNull(message = "User cannot be null.")
    private Integer userId;

    @NotNull(message = "Movie ID cannot be null.")
    private Integer movieId;

    @NotNull(message = "Rating score cannot be null.")
    @Min(value = 0, message = "Rating must be at least 0.")
    @Max(value = 5, message = "Rating must be at most 5.")
    private Integer rating;

    public static RatingDTO fromEntity(MovieRating rating) {
        RatingDTO dto = new RatingDTO();
        dto.setId(rating.getId());
        dto.setUserId(rating.getUser().getId());
        dto.setMovieId(rating.getMovie().getId());
        dto.setRating(rating.getRating());
        return dto;
    }

    public MovieRating toEntity(User user, Movie movie) {
        MovieRating entity = new MovieRating();
        entity.setId(this.id);
        entity.setUser(user);
        entity.setMovie(movie);
        entity.setRating(this.rating);
        return entity;
    }
}

