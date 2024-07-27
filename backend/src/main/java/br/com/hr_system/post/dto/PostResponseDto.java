package br.com.hr_system.post.dto;

import br.com.hr_system.user.dto.SimpleUserDto;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Builder @ToString
public class PostResponseDto {
    private String text;
    private SimpleUserDto user;
    private String image;
    private LocalDateTime createdDate;
}
