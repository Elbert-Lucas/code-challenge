package br.com.hr_system.user.repository;

import br.com.hr_system.user.domain.Role;
import br.com.hr_system.user.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(UserRole userRole);
}
