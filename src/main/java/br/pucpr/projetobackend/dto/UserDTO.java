package br.pucpr.projetobackend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor //cria construtor com os campos automatico p n chamar tds os setters manualmente
@NoArgsConstructor //construtor vazio p receber os dados do json
public class UserDTO {

    private Integer id;
    //MARK: VALIDACAO DE DADOS
    @NotNull(message = "Name cannot be null.")
    @NotBlank(message = "Name cannot be blank.")
    private String name;

    @NotNull(message = "Email cannot be null.")
    @NotBlank(message = "Email cannot be blank.")
    @Email(message = "Email must be valid.")
    private String email;
}
