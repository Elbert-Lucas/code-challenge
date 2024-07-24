package br.com.hr_system.notification.dto;

import br.com.hr_system.notification.domain.Notification;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import java.util.List;

@Getter @Setter
@ToString
public class NotificationDto {

    @NotEmpty
    private String title;

    @NotEmpty
    private String message;

    @JsonIgnore
    private Integer from;

    @JsonProperty("to-all")
    @NotNull
    private Boolean toAll;

    @JsonProperty("to-users")
    private List<Long> users;

}
