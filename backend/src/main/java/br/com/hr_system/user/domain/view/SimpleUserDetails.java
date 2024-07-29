package br.com.hr_system.user.domain.view;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;

@Entity(name = "VW_USER_SIMPLE")
@Immutable
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class SimpleUserDetails {
    @Id
    private Integer id;
    @Column
    private String name;
//    private String image;
}
