package br.com.hr_system.session.repository;

import br.com.hr_system.session.domain.Session;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends CrudRepository<Session, String>{
    List<Session> findByUserId(String userId);

}
