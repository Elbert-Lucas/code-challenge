package br.com.code_challenge.domain;

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

    @OneToMany(mappedBy = "function")
    private List<User> users;

}
