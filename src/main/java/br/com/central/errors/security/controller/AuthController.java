package br.com.central.errors.security.controller;

import br.com.central.errors.security.entity.dto.AccessToken;
import br.com.central.errors.security.entity.dto.SignIn;
import br.com.central.errors.security.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * The type Auth controller.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
@Api(value = "auth", produces = "application/json", consumes = "application/json")
public class AuthController {

    private UserService service;

    public AuthController(UserService service) {
        this.service = service;
    }

    /**
     * Authenticate user response entity.
     *
     * @param signIn the login dto
     * @return the response entity
     */
    @PostMapping("/login")
    public ResponseEntity<AccessToken> login(@Valid @RequestBody SignIn signIn) {
        return ResponseEntity.ok(service.login(signIn.getUsername(), signIn.getPassword()));
    }

}
