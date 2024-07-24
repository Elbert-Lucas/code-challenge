package br.com.hr_system.user.domain;

import br.com.hr_system.user.enums.MaritalStatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity(name = "TB_MARITAL_STATUS")
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Builder
public class MaritalStatus implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @Enumerated(value = EnumType.STRING)
    private MaritalStatusEnum status;

    @Column
    private String description;
}
