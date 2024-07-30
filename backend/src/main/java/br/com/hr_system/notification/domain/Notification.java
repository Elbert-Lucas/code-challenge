package br.com.hr_system.notification.domain;

import br.com.hr_system.user.domain.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity(name = "TB_NOTIFICATION")
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Builder
public class Notification implements Serializable {

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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "TB_NOTIFICATION_USER",
            joinColumns = @JoinColumn(name = "notification_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> to;

    @Column(name = "created_dth", updatable = false)
    @CreatedDate
    private LocalDateTime createdDate;

    @JsonBackReference
    public User getFrom() {
        return from;
    }

    @JsonIgnore
    public List<User> getToUsers() {
        return to;
    }
}
