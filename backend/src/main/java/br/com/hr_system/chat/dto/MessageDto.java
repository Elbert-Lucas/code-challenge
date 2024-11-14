package br.com.hr_system.chat.dto;

import br.com.hr_system.user.domain.User;
import br.com.hr_system.user.domain.view.SimpleUserDetails;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class MessageDto {

    @NotNull
    @JsonView(responseDto.class)
    private Integer fromUser;

    @NotNull
    @JsonView(requestDto.class)
    private Integer toUser;

    @NotNull
    private String message;

    private String sentDth;

    interface requestDto{};
    interface responseDto{};
}
