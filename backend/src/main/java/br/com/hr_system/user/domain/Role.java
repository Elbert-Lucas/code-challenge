package br.com.hr_system.user.domain;

import br.com.hr_system.user.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "TB_ROLE")
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    @Column
    private String description;
}
