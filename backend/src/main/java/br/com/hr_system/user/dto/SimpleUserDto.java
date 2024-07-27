package br.com.hr_system.user.dto;

import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class SimpleUserDto {
    private Integer id;
    private String name;
//    private String image;
}
