package br.com.hr_system.authentication.controller;

import br.com.hr_system.authentication.dto.CredentialsDto;
import br.com.hr_system.authentication.dto.LoginDTO;
import br.com.hr_system.authentication.dto.RefreshTokenDTO;
import br.com.hr_system.authentication.service.LoginService;
import br.com.hr_system.authentication.service.RefreshTokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    @Autowired
    LoginService loginService;
    @Autowired
    RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    public ResponseEntity<CredentialsDto> login(@RequestBody @Valid LoginDTO loginDTO){
        return new ResponseEntity<>(loginService.doLogin(loginDTO), HttpStatus.OK);
    }
    @PostMapping("/refresh-token")
    public ResponseEntity<CredentialsDto> refreshToken(@RequestBody RefreshTokenDTO refreshToken){
        return new ResponseEntity<>(refreshTokenService.refreshToken(refreshToken), HttpStatus.OK);
    }
}
