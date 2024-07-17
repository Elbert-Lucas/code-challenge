package br.com.code_challenge.dto.request;

import br.com.code_challenge.domain.*;
import br.com.code_challenge.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter @Setter
public class RegisterUserDto {

    @NotEmpty
    @Size(min = 3, message = "{minimumSize.name}")
    private String name;

    @NotNull(message = "{invalid.birth}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birth;

    @NotEmpty
    @Email(message = "{invalid.email}")
    private String email;

    @NotEmpty
    @Size(min = 13, message = "{minimumSize.phone}")
    private String phone;

    @NotNull
    private Address address;

    @JsonProperty("function")
    @NotNull
    private Function function;

    @JsonProperty("department")
    @NotNull
    private Department department;

    @JsonProperty("marital-status")
    @NotNull
    private MaritalStatus maritalStatus;

    @NotNull(message = "{invalid.birth}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date hiringDate;

    @JsonProperty("manager")
    private User manager;

    public User toEntity() {
        User user = new ModelMapper().map(this, User.class);
        user.setStatus(UserStatus.ACTIVE);
        return user;
    }

}

