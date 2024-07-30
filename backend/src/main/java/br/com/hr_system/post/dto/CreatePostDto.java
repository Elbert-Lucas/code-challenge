package br.com.hr_system.post.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class CreatePostDto {
    @NotEmpty
    private String text;
}
