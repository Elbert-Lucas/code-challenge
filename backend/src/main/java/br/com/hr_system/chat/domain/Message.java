package br.com.hr_system.chat.domain;

import br.com.hr_system.user.domain.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity(name = "TB_MESSAGE")
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 500)
    private String message;

    @ManyToOne
    @JoinColumn(name = "from_user", nullable = false)
    private User from;

    @ManyToOne
    @JoinColumn(name = "chat", nullable = false)
    private Chat chat;

    @Column(name = "sent_dth", updatable = false)
    @CreatedDate
    private LocalDateTime sentTime;

}