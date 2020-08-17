package br.com.central.errors.security.entity.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(description = "Response Access Bearer Token")
public class AccessToken {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;

    public AccessToken(String jwt, Long id, String username, String email) {
        this.token = jwt;
        this.id = id;
        this.username = username;
        this.email = email;
    }
}
