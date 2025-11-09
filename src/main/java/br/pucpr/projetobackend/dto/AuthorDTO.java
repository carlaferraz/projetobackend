package br.pucpr.projetobackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDTO {
    private Integer id;

    @NotNull(message = "NAME_REQUIRED")
    @NotBlank(message = "NAME_REQUIRED")
    private String name;

    private String bio;
}
