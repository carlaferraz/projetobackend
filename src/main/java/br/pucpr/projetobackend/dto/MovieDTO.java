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
public class MovieDTO {

    private Integer id;

    @NotNull(message = "Title cannot be null.")
    @NotBlank(message = "Title cannot be blank.")
    private String title;

    // optional: link to an existing author
    private Integer authorId;

}


