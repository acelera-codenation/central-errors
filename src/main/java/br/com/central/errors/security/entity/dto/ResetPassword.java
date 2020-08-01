package br.com.central.errors.security.entity.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(value = "Request Reset Password", description = "Change Password")
public class ResetPassword {
    private String username;
    private String password;
    private String newPassword;
    private String confirmPassword;
}
