package br.com.hr_system.user.domain.view;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;

import java.io.Serializable;
import java.util.Date;

@Entity(name = "VW_USER_DETAILS")
@Immutable
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class UserBasicDetails implements Serializable {

    @Id
    private Integer id;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String phone;

    @Column
    private String status;

    @Column
    private String department;

    @Column
    private String function;

    @Column
    private String manager;

    @Column(name = "birth_dt", columnDefinition = "DATE")
    private Date birth;

}
