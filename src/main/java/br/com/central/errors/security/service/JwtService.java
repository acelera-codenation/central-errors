package br.com.central.errors.security.service;

import br.com.central.errors.security.entity.UserDetailsCustom;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
@Slf4j
public class JwtService {

    @Value("${app.jwt.Secret:SecretKey}")
    private String jwtSecret;

    @Value("${app.jwt.ExpirationTime:86400000}") //24hs
    private int jwtExpirationTime;

    public String generateJwtToken(Authentication authentication) {

        UserDetailsCustom userPrincipal = (UserDetailsCustom) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationTime))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(authToken);
            return true;
        } catch (JwtException e) {
            log.error("jwt.invalid_sign", e);
            return false;
        }
    }

}
