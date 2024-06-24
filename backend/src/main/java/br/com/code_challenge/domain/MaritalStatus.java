package br.com.code_challenge.domain;

import br.com.code_challenge.enums.MaritalStatusEnum;
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
