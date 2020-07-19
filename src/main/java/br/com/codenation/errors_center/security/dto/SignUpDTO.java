package br.com.codenation.errors_center.security.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * The type Sign up dto.
 */
@Getter
@Setter
@ApiModel(value = "SignUp", description = "SignUp")
public class SignUpDTO {
    @NotBlank(message = "{sign.username.not_blank}")
    @Size(min = 3, max = 20, message = "{sign.username.size}")
    @ApiModelProperty(required = true)
    private String username;

    @NotBlank(message = "{sign.email.not_blank}")
    @Size(max = 50, message = "{sign.email.size}")
    @Email
    @ApiModelProperty(required = true)
    private String email;

    @NotBlank(message = "{sign.password.not_blank}")
    @Size(min = 6, max = 40, message = "{sign.password.size}")
    @ApiModelProperty(required = true)
    private String password;
}
