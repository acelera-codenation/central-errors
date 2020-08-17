package br.com.central.errors.security.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
@ApiModel(value = "Login", description = "Login")
public class SignIn {

    @NotBlank(message = "{sign.username.not_blank}")
    @ApiModelProperty(required = true, example = "zelda")
    private String username;

    @NotBlank(message = "{sign.username.not_blank}")
    @ApiModelProperty(required = true, example = "C0mput3r")
    private String password;

}
