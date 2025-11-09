package br.pucpr.projetobackend.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MovieReviewDTO {

    private Integer id;

    private String comment;

    @NotNull(message = "userId is required")
    private Integer userId;

    @NotNull(message = "movieId is required")
    private Integer movieId;
}