package br.pucpr.projetobackend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieAwardDTO {

    private Integer id;

    @NotNull(message = "Award name cannot be null.")
    private String name;

    private String category;

    @NotNull(message = "Award date cannot be null.")
    private LocalDate awardDate;

    @NotNull(message = "Movie ID cannot be null.")
    private Integer movieId;
}
