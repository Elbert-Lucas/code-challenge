package br.com.hr_system.user.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity(name = "TB_DEPARTMENT")
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Builder
public class Department implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Column
    private String description;
}
