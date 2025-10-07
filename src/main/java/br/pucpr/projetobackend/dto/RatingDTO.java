package br.pucpr.projetobackend.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "User cannot be blank.")
    private Integer userId;

    @NotNull(message = "Movie ID cannot be null.")
    private Integer movieId;

    @NotNull(message = "Rating score cannot be null.")
    @Min(value = 0, message = "Rating must be at least 0.")
    @Max(value = 5, message = "Rating must be at most 5.")
    private Integer rating;
}

