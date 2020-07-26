package br.com.codenation.central.infrastructure.exception;

import br.com.codenation.central.infrastructure.translate.CustomTranslator;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomResponseExceptionHandle {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<String> handleException(MethodArgumentNotValidException e) {

        String errorMsg = e.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .findFirst()
                .orElse(e.getMessage());

        return ResponseEntity.badRequest().body(errorMsg);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<String> credentialsException(BadCredentialsException e) {

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(CustomTranslator.toLocale("user.auth" +
                ".unauthorized_user"));
    }

}
