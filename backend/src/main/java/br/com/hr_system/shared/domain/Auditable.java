package br.com.hr_system.shared.domain;

import br.com.hr_system.user.domain.User;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter @Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Auditable {

//    @CreatedBy
//    protected User createdBy;
//
}
