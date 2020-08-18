package br.com.central.errors.security.controller;

import br.com.central.errors.infrastructure.config.HeaderAcceptLanguage;
import br.com.central.errors.infrastructure.message.ResponseMessage;
import br.com.central.errors.infrastructure.message.ResponseMessageError;
import br.com.central.errors.security.entity.dto.AccessToken;
import br.com.central.errors.security.entity.dto.SignIn;
import br.com.central.errors.security.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "auth", tags = "auth")
@Slf4j
@HeaderAcceptLanguage
public class AuthController {

    private final UserService service;

    public AuthController(UserService service) {
        this.service = service;
    }

    @PostMapping("/login")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = ResponseMessage.class),
            @ApiResponse(code = 400, message = "Bad request", response = ResponseMessageError.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = ResponseMessageError.class),
            @ApiResponse(code = 404, message = "Not found", response = ResponseMessageError.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ResponseMessageError.class)
    })
    public ResponseEntity<AccessToken> login(@Valid @RequestBody SignIn signIn) {
        return ResponseEntity.ok(service.login(signIn.getUsername(), signIn.getPassword()));
    }
}
