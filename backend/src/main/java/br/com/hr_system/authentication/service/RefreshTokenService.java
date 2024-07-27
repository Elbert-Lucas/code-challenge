package br.com.hr_system.authentication.service;

import br.com.hr_system.authentication.dto.CredentialsDto;
import br.com.hr_system.authentication.dto.RefreshTokenDTO;
import br.com.hr_system.config.security.JwtUtil;
import br.com.hr_system.user.domain.view.LoggedUserDetails;
import br.com.hr_system.user.repository.UserRepository;
import br.com.hr_system.user.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenService {
    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    @Autowired
    public RefreshTokenService(UserRepository userRepository, UserDetailsService userDetailsService, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    public CredentialsDto refreshToken(RefreshTokenDTO refreshToken) {
        refreshToken.setRefreshToken(refreshToken.getRefreshToken().substring(7));
        LoggedUserDetails user = userDetailsService.findUserByToken(refreshToken.getRefreshToken());
        return new CredentialsDto(jwtUtil.refreshToken(refreshToken.getRefreshToken(), user));
    }
}
