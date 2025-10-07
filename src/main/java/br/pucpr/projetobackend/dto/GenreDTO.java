package br.pucpr.projetobackend.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenreDTO {

    private Integer id;

    @NotNull(message = "Genre name cannot be null.")
    @NotBlank(message = "Genre name cannot be blank.")
    private String name;

}


