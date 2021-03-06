package br.com.central.errors.security.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@ApiModel(value = "SignUp", description = "SignUp")
public class SignUp {
    @NotBlank(message = "{sign.username.not_blank}")
    @Size(min = 3, max = 20, message = "{sign.username.size}")
    @ApiModelProperty(required = true, example = "zelda")
    private String username;

    @NotBlank(message = "{sign.email.not_blank}")
    @Size(max = 50, message = "{sign.email.size}")
    @Email
    @ApiModelProperty(required = true, example = "zelda@codenation.com")
    private String email;

    @NotBlank(message = "{sign.password.not_blank}")
    @Size(min = 6, max = 40, message = "{sign.password.size}")
    @ApiModelProperty(required = true, example = "C0mput3r")
    private String password;
}
