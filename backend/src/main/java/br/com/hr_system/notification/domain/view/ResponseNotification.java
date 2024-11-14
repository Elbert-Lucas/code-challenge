package br.com.hr_system.notification.domain.view;

import br.com.hr_system.user.domain.view.SimpleUserDetails;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "VW_RESPONSE_NOTIFICATION")
@Immutable
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) @ToString
public class ResponseNotification {

    @Id
    private Integer id;

    @Column
    private String title;

    @Column
    private String message;

    @Column(name = "created_dth", updatable = false)
    @CreatedDate
    private LocalDateTime createdDate;

    @ManyToOne
    @JoinColumn(name="from_user_id", nullable=false)
    private SimpleUserDetails from;

    @Column
    @JsonView({Sent.class})
    private Boolean toAll;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "TB_NOTIFICATION_USER",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "notification_id"))
    @JsonManagedReference
    @JsonView({Sent.class})
    private List<SimpleUserDetails> to;

    public interface Sent{};
    public interface Receive{};
}
