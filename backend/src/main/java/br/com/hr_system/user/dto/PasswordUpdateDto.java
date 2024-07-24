package br.com.hr_system.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class PasswordUpdateDto {

    @JsonProperty("old-password")
    private String oldPassword;

    @JsonProperty("confirm-password")
    @NotNull
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{9,}$",
            message = "{invalid.password}")
    private String confirmPassword;

    @JsonProperty("new-password")
    @NotNull
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{9,}$",
            message = "{invalid.password}")
    private String newPassword;
}
