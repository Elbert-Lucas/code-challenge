package br.com.hr_system.user.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity(name = "TB_FUNCTION")
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Builder
public class Function implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Column
    private String description;
}
