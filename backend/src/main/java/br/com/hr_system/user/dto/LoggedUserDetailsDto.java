package br.com.hr_system.user.dto;

import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoggedUserDetailsDto extends UserBasicDetailsDto{
    private String role;
}

