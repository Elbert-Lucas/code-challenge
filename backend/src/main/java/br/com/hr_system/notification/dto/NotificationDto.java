package br.com.hr_system.notification.dto;

import br.com.hr_system.notification.domain.Notification;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.util.List;

@Getter @Setter
public class NotificationDto {

    @NotEmpty
    private String title;

    @NotEmpty
    private String message;

    @NotEmpty
    @JsonProperty("from")
    private Long fromId;

    @JsonProperty("to-all")
    @NotNull
    private Boolean toAll;

    @JsonProperty("to-users")
    private List<Long> users;

    public Notification toEntity() {
        Notification notification = new ModelMapper().map(this, Notification.class);
        return notification;
    }
}
