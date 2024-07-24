package br.com.hr_system.session.service;

import br.com.hr_system.session.domain.Session;
import br.com.hr_system.session.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SessionService {
    private final SessionRepository sessionRepository;

    @Autowired
    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }
    public Session saveSession(String user){
        Session session = Session.builder()
                                 .sessionId(UUID.randomUUID().toString())
                                 .userId(user)
                                 .build();
        return sessionRepository.save(session);
    }
    public List<Session> findSessionByUserId(String userId){
        return sessionRepository.findByUserId(userId);
    }
    public Optional<Session> findSessionById(String id){
        return sessionRepository.findById(id);
    }
    public void finishSession(String session){
        sessionRepository.delete(sessionRepository.findById(session).get());
    }
}
