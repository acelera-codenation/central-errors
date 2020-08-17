package br.com.central.errors.security.controller;

import br.com.central.errors.infrastructure.message.ResponseMessage;
import br.com.central.errors.infrastructure.message.ResponseMessageError;
import br.com.central.errors.infrastructure.translate.CustomTranslator;
import br.com.central.errors.security.entity.User;
import br.com.central.errors.security.entity.dto.ResetPassword;
import br.com.central.errors.security.entity.dto.SignUp;
import br.com.central.errors.security.service.UserService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "user", tags = "security")
@Slf4j
@ApiImplicitParams({
        @ApiImplicitParam(
                name = "Accept-Language", value = "pt-br", dataType = "string", paramType = "header")})
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/register")
    @ApiResponses({
            @ApiResponse(code = 202, message = "Success", response = ResponseMessage.class),
            @ApiResponse(code = 400, message = "Bad request", response = ResponseMessageError.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ResponseMessageError.class)
    })
    public ResponseEntity<ResponseMessage> register(@Valid @RequestBody SignUp signUp) {
        if (service.existsByUsername(signUp.getUsername())) throw new BadCredentialsException("sign.user.exists");

        if (service.existsByEmail(signUp.getEmail())) throw new BadCredentialsException("sign.email.exists");

        User user = new User(signUp.getUsername(), signUp.getEmail(), signUp.getPassword());
        service.save(user);
        return ResponseEntity.ok(new ResponseMessage(CustomTranslator.toLocale("sign.register.success")));
    }

    @PostMapping("/resetpassword")
    @ApiResponses({
            @ApiResponse(code = 202, message = "Success", response = ResponseMessage.class),
            @ApiResponse(code = 400, message = "Bad request", response = ResponseMessageError.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = ResponseMessageError.class),
            @ApiResponse(code = 404, message = "Not found", response = ResponseMessageError.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ResponseMessageError.class)
    })
    public ResponseEntity<ResponseMessage> resetPassword(@Validated @RequestBody ResetPassword account) {
        service.changeUserPassword(account);
        return ResponseEntity.ok(new ResponseMessage(CustomTranslator.toLocale("sign.reset_password")));
    }
}
