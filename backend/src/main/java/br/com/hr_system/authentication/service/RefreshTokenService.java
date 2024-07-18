package br.com.hr_system.authentication.service;

import br.com.hr_system.authentication.dto.CredentialsDto;
import br.com.hr_system.authentication.dto.RefreshTokenDTO;
import br.com.hr_system.config.security.JwtUtil;
import br.com.hr_system.user.dto.LoggedUserDetailsDto;
import br.com.hr_system.user.repository.UserRepository;
import br.com.hr_system.user.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    public CredentialsDto refreshToken(RefreshTokenDTO refreshToken) {
        LoggedUserDetailsDto user = userDetailsService.findUserByToken(refreshToken.getRefreshToken());
        return new CredentialsDto(jwtUtil.refreshToken(refreshToken.getRefreshToken(), user));
    }
}
