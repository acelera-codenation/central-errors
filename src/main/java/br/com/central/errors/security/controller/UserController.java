package br.com.central.errors.security.controller;

import br.com.central.errors.infrastructure.message.CustomResponseMessage;
import br.com.central.errors.infrastructure.translate.CustomTranslator;
import br.com.central.errors.security.entity.User;
import br.com.central.errors.security.entity.dto.ResetPassword;
import br.com.central.errors.security.entity.dto.SignUp;
import br.com.central.errors.security.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user")
@Api(value = "user", produces = "application/json", consumes = "application/json")
public class UserController {

    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    /**
     * Register user response entity.
     *
     * @param signUp the sign up request
     * @return the response entity
     */
    @PostMapping("/register")
    public ResponseEntity<CustomResponseMessage> register(@Valid @RequestBody SignUp signUp) {
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

    @PostMapping("/resetpassword")
    public ResponseEntity<CustomResponseMessage> resetPassword(@Valid @RequestBody ResetPassword account) {
        service.changeUserPassword(account);
        return ResponseEntity.ok(translate("sign.reset_password"));
    }

    private CustomResponseMessage translate(String msgKey) {
        return new CustomResponseMessage(CustomTranslator.toLocale(msgKey));
    }
}
