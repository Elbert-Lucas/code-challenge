package br.com.hr_system.notification.domain;

import br.com.hr_system.user.domain.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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

    @ManyToMany(mappedBy = "notifications", fetch = FetchType.EAGER)
    private List<User> users;

    @JsonBackReference
    public User getFrom() {
        return from;
    }

    @JsonIgnore
    public List<User> getUsers() {
        return users;
    }
}
