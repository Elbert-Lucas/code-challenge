package br.com.hr_system.config.security;

import br.com.hr_system.user.domain.view.LoggedUserDetails;
import br.com.hr_system.user.service.UserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
@Slf4j
public class AuthenticationService {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Autowired
    public AuthenticationService(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    public UsernamePasswordAuthenticationToken authenticate(String jwt, boolean isRegister){
        log.debug("Autenticando usuario ");
        LoggedUserDetails user = userDetailsService.findUserByToken(jwt);

        if(!isRegister) jwtUtil.validateToken(jwt, user);
        else jwtUtil.validateRegisterToken(jwt, user);
        log.debug("Usuario autenticado");
        saveUserOnRequestContext(user);
        return new UsernamePasswordAuthenticationToken(
                user,
            null,
            null
        );
    }
    private void saveUserOnRequestContext(LoggedUserDetails loggedUser){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        request.setAttribute("user", loggedUser);
    }
}
