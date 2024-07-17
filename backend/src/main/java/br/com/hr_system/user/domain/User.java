package br.com.hr_system.user.domain;

import br.com.hr_system.user.enums.UserStatus;
import br.com.hr_system.notification.domain.Notification;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity(name = "TB_USER")
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Builder @ToString
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Column(unique = true)
    private String email;

    @Column(unique = true, length = 13)
    private String phone;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Department department;

    @ManyToOne
    @JoinColumn(name = "function_id", referencedColumnName = "id")
    private Function function;

    @Column
    private String password;

    @ManyToOne
    private Role role;

    @ManyToOne
    @JoinColumn(name = "marital_status_id", referencedColumnName = "id")
    private MaritalStatus maritalStatus;

    @Column(name = "hiring_dt", columnDefinition = "DATE")
    private Date hiringDate;

    @Column(name = "resignation_dt", columnDefinition = "DATE")
    private Date resignationDate;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User manager;

    @Column(name = "birth_dt", columnDefinition = "DATE")
    private Date birth;

    @Column
    @Enumerated(value = EnumType.STRING)
    private UserStatus status;

    @ManyToMany
    @JoinTable(name = "TB_NOTIFICATION_USER",
              joinColumns = @JoinColumn(name = "user_id"),
              inverseJoinColumns = @JoinColumn(name = "notification_id"))
    private List<Notification> notifications;

    @OneToMany(mappedBy = "from")
    private List<Notification> sendNotifications;

}
