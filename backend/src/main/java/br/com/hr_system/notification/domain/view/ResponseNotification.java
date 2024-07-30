package br.com.hr_system.notification.domain.view;

import br.com.hr_system.notification.dto.ResponseView;
import br.com.hr_system.user.domain.view.LoggedUserDetails;
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
    @JsonView({ResponseView.Sent.class, ResponseView.Receive.class})
    private Integer id;

    @Column
    @JsonView({ResponseView.Sent.class, ResponseView.Receive.class})
    private String title;

    @Column
    @JsonView({ResponseView.Sent.class, ResponseView.Receive.class})
    private String message;

    @Column(name = "created_dth", updatable = false)
    @CreatedDate
    @JsonView({ResponseView.Sent.class, ResponseView.Receive.class})
    private LocalDateTime createdDate;

    @ManyToOne
    @JoinColumn(name="from_user_id", nullable=false)
    @JsonView({ResponseView.Sent.class, ResponseView.Receive.class})
    private SimpleUserDetails from;

    @Column
    @JsonView({ResponseView.Sent.class})
    private Boolean toAll;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "TB_NOTIFICATION_USER",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "notification_id"))
    @JsonManagedReference
    @JsonView({ResponseView.Sent.class})
    private List<SimpleUserDetails> to;

}
