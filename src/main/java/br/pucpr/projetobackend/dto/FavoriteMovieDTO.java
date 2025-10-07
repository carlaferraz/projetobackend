package br.pucpr.projetobackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteMovieDTO {

    private Integer id;

    @NotNull(message = "Movie ID cannot be null.")
    private Integer movieId;

    @NotNull(message = "User ID cannot be null.")
    private Integer userId;

    @NotBlank(message = "Movie title cannot be blank.")
    private String movieTitle;

    private String notes;
}
