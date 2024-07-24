package br.com.hr_system.session.service;

import br.com.hr_system.session.domain.Session;
import br.com.hr_system.session.repository.SessionRepository;
import br.com.hr_system.session.repository.SessionVanillaRepository;
import br.com.hr_system.user.domain.User;
import br.com.hr_system.user.exception.UserNotFoundException;
import br.com.hr_system.user.service.UserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SessionService {
    private final SessionRepository sessionRepository;
    private final SessionVanillaRepository sessionVanillaRepository;
    private final UserDetailsService userDetailsService;
    @Autowired
    public SessionService(SessionRepository sessionRepository, SessionVanillaRepository sessionVanillaRepository, UserDetailsService userDetailsService) {
        this.sessionRepository = sessionRepository;
        this.sessionVanillaRepository = sessionVanillaRepository;
        this.userDetailsService = userDetailsService;
    }

    public Session saveSession(String sessionId, String user){
        Session session = Session.builder()
                                 .sessionId(sessionId)
                                 .userId(user)
                                 .build();
        return sessionRepository.save(session);
    }
    public List<Session> findSessionByUserId(String userId){
        return sessionRepository.findByUserId(userId);
    }
    public List<String> findSessionByUserId(List<String> userId){
        return sessionVanillaRepository.findByUserIdIn(userId);
    }
    public User findUserBySessionId(String sessionId){
        Integer userId = sessionVanillaRepository.findUserIdBySessionId(sessionId);
        return userDetailsService.findUserById(userId).orElseThrow(UserNotFoundException::new);
    }
    public Integer findUserIdBySessionId(String sessionId){
        return sessionVanillaRepository.findUserIdBySessionId(sessionId);
    }
    public Optional<Session> findSessionById(String id){
        return sessionRepository.findById(id);
    }
    public void finishSession(String session){
        sessionRepository.delete(sessionRepository.findById(session).get());
    }
}
