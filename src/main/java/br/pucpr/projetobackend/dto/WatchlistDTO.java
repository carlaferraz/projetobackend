package br.pucpr.projetobackend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WatchlistDTO {

    private Integer id;

    @NotNull(message = "USER_ID_REQUIRED")
    private Integer userId;

    @NotNull(message = "MOVIE_ID_REQUIRED")
    private Integer movieId;

    private String status;
}
