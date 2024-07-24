package br.com.hr_system.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity(name = "TB_ADDRESS")
@Builder
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class Address implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(mappedBy = "address")
    @JsonIgnore
    private User user;

    @Column
    private String country;

    @Column
    private String state;

    @Column
    private String city;

    @Column
    private String address;
}
