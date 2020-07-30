package br.com.central.errors.security.controller;

import br.com.central.errors.infrastructure.exception.MessageError;
import br.com.central.errors.infrastructure.translate.CustomTranslator;
import br.com.central.errors.security.entity.User;
import br.com.central.errors.security.entity.UserDetailsCustom;
import br.com.central.errors.security.entity.dto.JwtResponse;
import br.com.central.errors.security.entity.dto.SignIn;
import br.com.central.errors.security.entity.dto.SignUp;
import br.com.central.errors.security.repository.UserRepository;
import br.com.central.errors.security.service.JwtService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtService jwtService;

    /**
     * Authenticate user response entity.
     *
     * @param signIn the login dto
     * @return the response entity
     */
    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody SignIn signIn) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signIn.getUsername(), signIn.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtService.generateJwtToken(authentication);
        UserDetailsCustom userDetailsCustom = (UserDetailsCustom) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetailsCustom.getId(),
                userDetailsCustom.getUsername(),
                userDetailsCustom.getEmail()));

    }

    /**
     * Register user response entity.
     *
     * @param signUp the sign up request
     * @return the response entity
     */
    @PostMapping("/signup")
    public ResponseEntity<MessageError> register(@Valid @RequestBody SignUp signUp) {
        if (userRepository.existsByUsername(signUp.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(translate("sign.user.exists"));
        }

        if (userRepository.existsByEmail(signUp.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(translate("sign.email.exists"));
        }

        User user = new User(signUp.getUsername(),
                signUp.getEmail(),
                encoder.encode(signUp.getPassword()));

        userRepository.save(user);
        return ResponseEntity.ok(translate("sign.register.success"));
    }


    private MessageError translate(String msgKey) {
        return new MessageError(CustomTranslator.toLocale(msgKey));
    }
}
