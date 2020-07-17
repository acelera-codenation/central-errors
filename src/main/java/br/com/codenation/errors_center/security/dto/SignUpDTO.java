package br.com.codenation.errors_center.security.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * The type Sign up dto.
 */
@Data
@ApiModel(value = "SignUp")
public class SignUpDTO {
    @NotBlank
    @Size(min = 3, max = 20, message = "O campo username deve conter entre 6 e 40 caracteres.")
    @ApiModelProperty(value = "O username é um identificador único.")
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    @ApiModelProperty(value = "O email é um identificador único e deve ser válido.")
    private String email;

    @NotBlank
    @Size(min = 6, max = 40, message = "O campo password deve conter entre 6 e 40 caracteres.")
    @ApiModelProperty(value = "A senha deve conter entre 6 e 40 caracteres.")
    private String password;
}
