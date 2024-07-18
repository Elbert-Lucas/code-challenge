package br.com.hr_system.user.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserBasicDetailsDto {

    private Integer id;
    private String name;
    private String email;
    private String phone;
    private String department;
    private String function;
    private String manager;
    private Date birth;
    private String status;
}
