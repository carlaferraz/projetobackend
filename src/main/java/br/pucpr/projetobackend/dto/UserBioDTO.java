package br.pucpr.projetobackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBioDTO {

    private Integer id;

    @NotNull(message = "User cannot be null.")
    @NotBlank(message = "User cannot be blank.")
    private Integer userId;

    @NotNull(message = "BIO_REQUIRED")
    @NotBlank(message = "BIO_REQUIRED")
    private String biography;
}
