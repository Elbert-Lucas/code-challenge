package br.com.hr_system.notification.dto;

import br.com.hr_system.user.dto.SimpleUserDto;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class NotificationResponseDto {

    @JsonView({ResponseView.Sent.class, ResponseView.Receive.class})
    private String title;

    @JsonView({ResponseView.Sent.class, ResponseView.Receive.class})
    private String message;

    @JsonView({ResponseView.Sent.class, ResponseView.Receive.class})
    private SimpleUserDto from;

    @JsonView({ResponseView.Sent.class})
    private Boolean toAll;

    @JsonView({ResponseView.Sent.class})
    private List<SimpleUserDto> to;

    @JsonView({ResponseView.Sent.class, ResponseView.Receive.class})
    private LocalDateTime createdDate;

}
