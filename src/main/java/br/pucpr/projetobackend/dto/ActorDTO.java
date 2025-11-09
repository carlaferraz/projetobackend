package br.pucpr.projetobackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActorDTO {

    private Integer id;

    @NotNull(message = "Name cannot be null.")
    @NotBlank(message = "Name cannot be blank.")
    private String name;

    private LocalDate birthDate;

    private String nationality;

    private String biography;

    private String photoUrl;

    private List<Integer> movieIds;
}

