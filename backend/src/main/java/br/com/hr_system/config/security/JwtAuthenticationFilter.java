package br.com.hr_system.config.security;

import br.com.hr_system.config.security.exception.InvalidTokenException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationService authenticationService;

    @Autowired
    public JwtAuthenticationFilter(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
    private final List<String> PERMITTED_ENDPOINTS = List.of("/login","/refresh-token","/connect", "/user/register");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(PERMITTED_ENDPOINTS.stream().anyMatch(request.getRequestURI()::contains)){
            filterChain.doFilter(request, response);
            return;
        }
        boolean isRegister = false;
        String jwt;

        if(request.getRequestURI().contains("/user/change-password") && request.getHeader("email-auth") != null){
            jwt = request.getHeader("email-auth").substring(7);
            isRegister = true;
        }else jwt = request.getHeader("Authorization").substring(7);

        try {
            UsernamePasswordAuthenticationToken authentication = authenticationService.authenticate(jwt, isRegister);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write(new InvalidTokenException().toString());
        }

    }

}
