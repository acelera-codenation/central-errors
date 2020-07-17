package br.com.codenation.errors_center.security.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * The type Login dto.
 */
@Data
@ApiModel(value = "Login")
public class SignInDTO {

    @NotBlank(message = "O username não foi informado.")
    private String username;

    @NotBlank(message = "O password não foi informado.")
    private String password;

}
