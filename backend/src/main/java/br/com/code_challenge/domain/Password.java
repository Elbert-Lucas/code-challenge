package br.com.code_challenge.domain;

import br.com.code_challenge.converter.PasswordEncryptConverter;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "TB_PASSWORD")
@Builder
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class Password {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(mappedBy = "password")
    private User user;

    @Column
    @Convert(converter = PasswordEncryptConverter.class)
    private String password;

    @Column
    private boolean isTemporary = true;

    @Column(name = "last_change_dth")
    private LocalDateTime changedAt;
}
