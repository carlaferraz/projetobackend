package br.pucpr.projetobackend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {

    @NotNull(message = "NAME_REQUIRED")
    @NotBlank(message = "NAME_REQUIRED")
    private String nome;

    @Email(message = "EMAIL_INVALID")
    @NotBlank(message = "EMAIL_REQUIRED")
    private String email;

    @NotBlank(message = "PASSWORD_REQUIRED")
    private String senha;

    @NotBlank(message = "CONFIRM_PASSWORD_REQUIRED")
    private String confirmarSenha;
}

