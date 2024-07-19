package br.com.hr_system.user.repository;

import br.com.hr_system.user.domain.view.LoggedUserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoggedUserRepository extends JpaRepository<LoggedUserDetails, Long> {
}
