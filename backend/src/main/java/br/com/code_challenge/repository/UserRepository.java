package br.com.code_challenge.repository;

import br.com.code_challenge.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT User FROM TB_USER User WHERE id IN :ids")
    public List<User> findMultipleUsersByIds(List<Long> ids);
}
