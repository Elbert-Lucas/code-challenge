package br.com.hr_system.chat.domain;

import br.com.hr_system.user.domain.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "TB_CHAT")
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToMany
    @JoinTable(name = "TB_CHAT_USERS",
               joinColumns = @JoinColumn(name="chat_id"),
               inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;

    @OneToMany(mappedBy = "chat")
    @JsonBackReference
    private List<Message> messages;

    @Column(name = "created_dth", updatable = false)
    @CreatedDate
    private LocalDateTime createdDate;
}
