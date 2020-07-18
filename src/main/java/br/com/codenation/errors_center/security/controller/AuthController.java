package br.com.codenation.errors_center.security.controller;

import br.com.codenation.errors_center.infrastructure.translate.Translator;
import br.com.codenation.errors_center.security.dto.JwtResponseDTO;
import br.com.codenation.errors_center.security.dto.MessageDTO;
import br.com.codenation.errors_center.security.dto.SignInDTO;
import br.com.codenation.errors_center.security.dto.SignUpDTO;
import br.com.codenation.errors_center.security.entity.User;
import br.com.codenation.errors_center.security.entity.UserDetailsCustom;
import br.com.codenation.errors_center.security.repository.UserRepository;
import br.com.codenation.errors_center.security.service.JwtService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * The type Auth controller.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
@Api(value = "auth", produces = "application/json", consumes = "application/json", description = "Authorization")
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
    public ResponseEntity<JwtResponseDTO> login(@Valid @RequestBody SignInDTO signIn) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signIn.getUsername(), signIn.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtService.generateJwtToken(authentication);
        UserDetailsCustom userDetailsCustom = (UserDetailsCustom) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponseDTO(jwt,
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
    public ResponseEntity<MessageDTO> register(@Valid @RequestBody SignUpDTO signUp) {
        Boolean existsByUsername = userRepository.existsByUsername(signUp.getUsername());
        if (existsByUsername) {
            return ResponseEntity
                    .badRequest()
                    .body(translate("sign.user.exists"));
        }

        Boolean existsByEmail = userRepository.existsByEmail(signUp.getEmail());
        if (existsByEmail) {
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


    /**
     * Handle exception response entity.
     *
     * @param exception the exception
     * @return the response entity
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleException(MethodArgumentNotValidException exception) {

        String errorMsg = exception.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .findFirst()
                .orElse(exception.getMessage());

        return ResponseEntity.badRequest().body(errorMsg);
    }

    private MessageDTO translate(String msgKey) {
        return new MessageDTO(Translator.toLocale(msgKey));
    }
}
