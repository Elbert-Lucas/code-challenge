package br.com.hr_system.user.repository;

import br.com.hr_system.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT User FROM TB_USER User WHERE id IN :ids")
    List<User> findMultipleUsersByIds(List<Long> ids);

    Optional<User> findUserByEmail(String email);
}
