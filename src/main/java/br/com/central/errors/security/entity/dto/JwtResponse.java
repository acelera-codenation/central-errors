package br.com.central.errors.security.entity.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

/**
 * The type Jwt response dto.
 */
@Getter
@Setter
@ApiModel(value = "Response Acess Bearer Token", description = "Response Acess Bearer Token")
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;

    /**
     * Instantiates a new Jwt response dto.
     *
     * @param jwt      the jwt
     * @param id       the id
     * @param username the username
     * @param email    the email
     */
    public JwtResponse(String jwt, Long id, String username, String email) {
        this.token = jwt;
        this.id = id;
        this.username = username;
        this.email = email;
    }
}
