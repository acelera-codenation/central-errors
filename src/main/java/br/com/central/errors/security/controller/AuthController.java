package br.com.central.errors.security.controller;

import br.com.central.errors.infrastructure.exception.MessageError;
import br.com.central.errors.infrastructure.translate.CustomTranslator;
import br.com.central.errors.security.entity.User;
import br.com.central.errors.security.entity.dto.JwtResponse;
import br.com.central.errors.security.entity.dto.SignIn;
import br.com.central.errors.security.entity.dto.SignUp;
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
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody SignIn signIn) {

        return ResponseEntity.ok(service.login(signIn.getUsername(), signIn.getPassword()));

    }

    /**
     * Register user response entity.
     *
     * @param signUp the sign up request
     * @return the response entity
     */
    @PostMapping("/register")
    public ResponseEntity<MessageError> register(@Valid @RequestBody SignUp signUp) {
        if (service.existsByUsername(signUp.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(translate("sign.user.exists"));
        }

        if (service.existsByEmail(signUp.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(translate("sign.email.exists"));
        }

        User user = new User(signUp.getUsername(), signUp.getEmail(), signUp.getPassword());

        service.save(user);
        return ResponseEntity.ok(translate("sign.register.success"));
    }


    private MessageError translate(String msgKey) {
        return new MessageError(CustomTranslator.toLocale(msgKey));
    }
}
