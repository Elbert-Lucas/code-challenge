package br.com.code_challenge.user.repository;

import br.com.code_challenge.user.domain.Role;
import br.com.code_challenge.user.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(UserRole userRole);
}
