package br.com.hr_system.user.service;

import br.com.hr_system.config.security.JwtUtil;
import br.com.hr_system.config.security.exception.InvalidTokenException;
import br.com.hr_system.user.dto.LoggedUserDetailsDto;
import br.com.hr_system.user.dto.UserBasicDetailsDto;
import br.com.hr_system.user.repository.UserRepository;
import br.com.hr_system.user.repository.UserVanillaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserVanillaRepository userVanillaRepository;

    @Autowired
    JwtUtil jwtUtil;

    public LoggedUserDetailsDto findUserByToken(String token) {
        try {
            return userVanillaRepository.findLoggedUserDetailsById(Long.valueOf(jwtUtil.getClaimItem(token, "sub")))
                    .orElseThrow(RuntimeException::new);
        } catch (Exception e) {
            throw new InvalidTokenException();
        }
    }

    public LoggedUserDetailsDto getLoggedUserDetails() {
        return (LoggedUserDetailsDto) ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                                    .getRequest()
                                    .getAttribute("user");
    }

    public UserBasicDetailsDto getUserDetails(Long userId) {
        return userVanillaRepository.findUserBasicDetails(userId)
                .orElseThrow(RuntimeException::new);
    }
}
