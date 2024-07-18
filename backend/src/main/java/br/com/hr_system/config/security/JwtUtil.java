package br.com.hr_system.config.security;

import br.com.hr_system.config.security.exception.InvalidTokenException;
import br.com.hr_system.user.dto.LoggedUserDetailsDto;
import br.com.hr_system.user.dto.UserBasicDetailsDto;
import br.com.hr_system.user.enums.UserStatus;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;
    private static final long USER_EXPIRATION_TIME = 1000L * 60 * 15; // 15 minutes
    private static final long REFRESH_EXPIRATION_TIME = 1000L * 60 * 60 * 24 * 15; // 15 days
    private static final long REGISTER_EXPIRATION_TIME = 1000L * 60 * 60 * 24 * 30; // 30 days

    public Map<String, String> createUserTokens(LoggedUserDetailsDto user){
        return createUserTokens(user, createRefreshJwt(user));
    }
    public String createRegisterPasswordToken(UserBasicDetailsDto user){
        return createJwt(String.valueOf(user.getId()),
                createRegisterClaims(user),
                USER_EXPIRATION_TIME);
    }

    private Map<String, String> createUserTokens(LoggedUserDetailsDto user, String refreshToken){
        Map<String, String> tokens = new HashMap<>();
        tokens.put("token", createJwtToken(user));
        tokens.put("refreshToken", refreshToken);
        return tokens;
    }
    private String createJwtToken(LoggedUserDetailsDto user){
        return createJwt(String.valueOf(user.getId()),
                         createJwtClaims(user),
                         REGISTER_EXPIRATION_TIME);
    }
    private String createRefreshJwt(LoggedUserDetailsDto user){
        return createJwt(String.valueOf(user.getId()),
                         createRefreshClaims(user),
                         REFRESH_EXPIRATION_TIME);
    }
    private String createJwt(String subject, Map<String, String> claims, Long expirationTime){
        return Jwts.builder()
                .signWith(getSigningKey())
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .compact();
    }
    private <T extends UserBasicDetailsDto> Map<String, String> createDefaultClaims(T user){
        Map<String, String> claims = new HashMap<>();
        claims.put("name", user.getName());
        claims.put("role", user.getRole());
        return claims;
    }

    private  Map<String, String> createJwtClaims(LoggedUserDetailsDto user){
        Map<String, String> claims = createDefaultClaims(user);
        claims.put("is_refresh", "false");
        return claims;
    }
    private Map<String, String> createRefreshClaims(LoggedUserDetailsDto user){
        Map<String, String> claims = createDefaultClaims(user);
        claims.put("is_refresh", "true");
        return claims;
    }
    private Map<String, String> createRegisterClaims(UserBasicDetailsDto user){
        Map<String, String> claims = createDefaultClaims(user);
        claims.put("is_register", "true");
        claims.put("email", user.getEmail());
        return claims;
    }
    public Map<String, String> refreshToken(String refreshJwt, LoggedUserDetailsDto user){
        validateRefreshToken(refreshJwt, user);
        return createUserTokens(user, refreshJwt);
    }
    private boolean defaultIsJwtValid(String jwt, LoggedUserDetailsDto user){
        return  user.getStatus().equals(UserStatus.ACTIVE.name())
                && isTokenExpired(jwt);
    }
    public void validateToken(String jwt, LoggedUserDetailsDto user){
        if(!(defaultIsJwtValid(jwt, user) && getClaimItem(jwt,"is_refresh").equals("false")))
            throw new InvalidTokenException();
    }
    private void validateRefreshToken(String jwt, LoggedUserDetailsDto user){
        if(!(defaultIsJwtValid(jwt, user) && getClaimItem(jwt,"is_refresh").equals("true")))
            throw new InvalidTokenException();
    }
    public void validateRegisterToken(String jwt, LoggedUserDetailsDto user){
        if(!(user.getStatus().equals(UserStatus.PASSWORD_PENDING.name()) && isTokenExpired(jwt))
           && getClaimItem(jwt,"is_register").equals("true"))
            throw new InvalidTokenException();
    }
    private Claims getClaims(String jwt){
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
    }
    public String getClaimItem(String jwt, String item) {
        return String.valueOf(getClaims(jwt).get(item));
    }
    private boolean isTokenExpired(String jwt) {
        return new Date(Long.valueOf(getClaimItem(jwt, "exp")))
                    .before(new Date());
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
