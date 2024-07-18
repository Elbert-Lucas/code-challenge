package br.com.hr_system.user.service;

import br.com.hr_system.config.security.JwtUtil;
import br.com.hr_system.config.security.exception.InvalidTokenException;
import br.com.hr_system.user.domain.User;
import br.com.hr_system.user.dto.LoggedUserDetailsDto;
import br.com.hr_system.user.dto.UserBasicDetailsDto;
import br.com.hr_system.user.repository.UserRepository;
import br.com.hr_system.user.repository.UserVanillaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

@Service
public class UserDetailsService {

    private final UserRepository userRepository;
    private final UserVanillaRepository userVanillaRepository;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserDetailsService(UserRepository userRepository, UserVanillaRepository userVanillaRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.userVanillaRepository = userVanillaRepository;
        this.jwtUtil = jwtUtil;
    }

    public LoggedUserDetailsDto findUserByToken(String token) {
        return userVanillaRepository.findLoggedUserDetailsById(Long.valueOf(jwtUtil.getClaimItem(token, "sub")))
            .orElseThrow(InvalidTokenException::new);
    }
    User findLoggedUser(){
        Integer id =((LoggedUserDetailsDto) ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest()
                .getAttribute("user")).getId();
        return userRepository.findById(Long.valueOf(id)).orElseThrow(RuntimeException::new);
    }

    public UserBasicDetailsDto getUserDetails(Long userId) {
        return userVanillaRepository.findUserBasicDetails(userId)
                .orElseThrow(RuntimeException::new);
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

}
