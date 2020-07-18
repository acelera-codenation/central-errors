package br.com.codenation.errors_center.security.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;


/**
 * The type Login dto.
 */
@Data
@ApiModel(value = "Login", description = "Login")
public class SignInDTO {

    @NotBlank(message = "{sign.username.not_blank}")
    @ApiModelProperty(required = true)
    private String username;

    @NotBlank(message = "{sign.username.not_blank}")
    @ApiModelProperty(required = true)
    private String password;

}
