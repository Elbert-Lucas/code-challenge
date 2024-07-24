package br.com.hr_system.user.domain.view;

import jakarta.persistence.*;
import lombok.*;


@Entity(name = "VW_LOGGED_USER")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoggedUserDetails extends UserBasicDetails {
    @Column
    private String role;
}

