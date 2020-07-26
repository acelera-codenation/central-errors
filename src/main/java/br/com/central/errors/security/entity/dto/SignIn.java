package br.com.central.errors.security.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


/**
 * The type Login dto.
 */
@Getter
@Setter
@ApiModel(value = "Login", description = "Login")
public class SignIn {

    @NotBlank(message = "{sign.username.not_blank}")
    @ApiModelProperty(required = true)
    private String username;

    @NotBlank(message = "{sign.username.not_blank}")
    @ApiModelProperty(required = true)
    private String password;

}