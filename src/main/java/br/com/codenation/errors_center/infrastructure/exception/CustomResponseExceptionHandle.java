package br.com.codenation.errors_center.infrastructure.exception;

import br.com.codenation.errors_center.infrastructure.translate.CustomTranslator;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
public class CustomResponseExceptionHandle {

    @ExceptionHandler({ConstraintViolationException.class})
    ResponseEntity<Set<String>> handleConstraintViolation(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();

        Set<String> messages = new HashSet<>(constraintViolations.size());
        messages.addAll(constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList()));

        return ResponseEntity
                .badRequest()
                .body(messages);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
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
