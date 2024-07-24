package br.com.hr_system.user.service;

import br.com.hr_system.config.security.JwtUtil;
import br.com.hr_system.config.security.exception.InvalidTokenException;
import br.com.hr_system.user.domain.User;
import br.com.hr_system.user.domain.view.LoggedUserDetails;
import br.com.hr_system.user.domain.view.UserBasicDetails;
import br.com.hr_system.user.repository.LoggedUserRepository;
import br.com.hr_system.user.repository.UserDetailsRepository;
import br.com.hr_system.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

@Service
public class UserDetailsService {

    private final UserRepository userRepository;
    private final UserDetailsRepository userDetailsRepository;
    private final LoggedUserRepository loggedUserRepository;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserDetailsService(UserRepository userRepository, LoggedUserRepository loggedUserRepository, UserDetailsRepository userDetailsRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.loggedUserRepository = loggedUserRepository;
        this.userDetailsRepository = userDetailsRepository;
        this.jwtUtil = jwtUtil;
    }

    public LoggedUserDetails findUserByToken(String token) {
        return loggedUserRepository.findById(Long.valueOf(jwtUtil.getClaimItem(token, "sub")))
            .orElseThrow(InvalidTokenException::new);
    }
    public User findLoggedUser(){
        Integer id =((LoggedUserDetails) ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest()
                .getAttribute("user")).getId();
        return userRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }
    public Optional<User> findUserById(Integer id){
        return userRepository.findById(id);
    }

}
