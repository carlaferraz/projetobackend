package br.pucpr.projetobackend.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {

    private Integer id;

    @NotNull(message = "Movie ID cannot be null.")
    private Integer movieId;

    @NotNull(message = "User ID cannot be null.")
    private Integer userId;

    @NotNull(message = "Title cannot be null.")
    @NotBlank(message = "Title cannot be blank.")
    private String title;

    @NotNull(message = "Comment cannot be null.")
    @NotBlank(message = "Comment cannot be blank.")
    private String comment;
}
