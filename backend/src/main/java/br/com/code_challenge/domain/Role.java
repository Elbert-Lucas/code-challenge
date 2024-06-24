package br.com.code_challenge.domain;

import br.com.code_challenge.enums.UserRole;
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
    private UserRole role = UserRole.BASIC;

    @Column
    private String description;
}
