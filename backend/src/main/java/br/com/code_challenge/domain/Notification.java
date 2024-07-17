package br.com.code_challenge.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "TB_NOTIFICATION")
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Builder
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="user_from_id", nullable=false)
    private User from;

    @Column
    private String title;

    @Column
    private String message;

    @Column
    private Boolean toAll;

    @ManyToMany(mappedBy = "notifications")
    private List<User> users;
}
