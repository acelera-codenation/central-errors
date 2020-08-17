package br.com.central.errors.security.controller;

import br.com.central.errors.security.entity.dto.AccessToken;
import br.com.central.errors.security.entity.dto.SignIn;
import br.com.central.errors.security.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "auth", tags = "security")
@Slf4j
@ApiImplicitParams({
        @ApiImplicitParam(
                name = "Accept-Language", value = "pt-br", dataType = "string", paramType = "header")})
public class AuthController {

    private UserService service;

    public AuthController(UserService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity<AccessToken> login(@Valid @RequestBody SignIn signIn) {
        return ResponseEntity.ok(service.login(signIn.getUsername(), signIn.getPassword()));
    }
}
