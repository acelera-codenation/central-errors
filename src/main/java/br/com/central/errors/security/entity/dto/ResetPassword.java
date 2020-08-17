package br.com.central.errors.security.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(value = "Request Reset Password", description = "Change Password")
public class ResetPassword {
    @NotBlank(message = "{sign.username.not_blank}")
    @Size(min = 3, max = 20, message = "{sign.username.size}")
    @ApiModelProperty(required = true, example = "zelda")
    private String username;

    @NotBlank(message = "{sign.password.not_blank}")
    @Size(min = 6, max = 40, message = "{sign.password.size}")
    @ApiModelProperty(required = true, example = "C0mput3r")
    private String password;

    @NotBlank(message = "{sign.password.not_blank}")
    @Size(min = 6, max = 40, message = "{sign.password.size}")
    @ApiModelProperty(required = true, example = "newC0mput3r")
    private String newPassword;

    @NotBlank(message = "{sign.password.not_blank}")
    @Size(min = 6, max = 40, message = "{sign.password.size}")
    @ApiModelProperty(required = true, example = "newC0mput3r")
    private String confirmPassword;
}
