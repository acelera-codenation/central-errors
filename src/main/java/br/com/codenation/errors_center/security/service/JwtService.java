package br.com.codenation.errors_center.security.service;

import br.com.codenation.errors_center.security.entity.UserDetailsCustom;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;


/**
 * The type Jwt service.
 */
@Component
public class JwtService {

    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

    @Value("${api.app.jwtSecret:SecretKey}")
    private String jwtSecret;

    @Value("${api.app.jwtExpirationMs:86400000}") //24hs
    private int jwtExpirationTime;

    /**
     * Generate jwt token string.
     *
     * @param authentication the authentication
     * @return the string
     */
    public String generateJwtToken(Authentication authentication) {

        UserDetailsCustom userPrincipal = (UserDetailsCustom) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationTime))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    /**
     * Gets user name from jwt token.
     *
     * @param token the token
     * @return the user name from jwt token
     */
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    private void log(String msgKey, Exception e) {
        logger.error(msgKey, e.getMessage());
    }

    /**
     * Validate jwt token boolean.
     *
     * @param authToken the auth token
     * @return the boolean
     */
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            log("jwt.invalid_sign", e);
        } catch (MalformedJwtException e) {
            log("jwt.invalid_token", e);
        } catch (ExpiredJwtException e) {
            log("jwt.expired_token", e);
        } catch (UnsupportedJwtException e) {
            log("jwt.unsupported_token", e);
        } catch (IllegalArgumentException e) {
            log("jwt.token_not_informed", e);
        }

        return false;
    }

}
