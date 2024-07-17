package br.com.hr_system.user.dto;

import br.com.hr_system.user.domain.*;
import br.com.hr_system.user.enums.UserStatus;
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

    @JsonProperty("department")
    @NotNull
    private Department department;

    @JsonProperty("function")
    @NotNull
    private Function function;

    @JsonProperty("marital-status")
    @NotNull
    private MaritalStatus maritalStatus;

    @NotNull(message = "{invalid.birth}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date hiringDate;

    @JsonProperty("manager")
    private User manager;

}

