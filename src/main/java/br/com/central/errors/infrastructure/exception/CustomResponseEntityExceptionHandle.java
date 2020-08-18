package br.com.central.errors.infrastructure.exception;

import br.com.central.errors.infrastructure.message.ResponseMessageError;
import br.com.central.errors.infrastructure.translate.CustomTranslator;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestControllerAdvice
class CustomResponseEntityExceptionHandle extends ResponseEntityExceptionHandler {
//org.springframework.data.mapping.PropertyReferenceException
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status);
        body.put("errors", ex.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList()));

        return new ResponseEntity<>(body, headers, status);
    }

    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<ResponseMessageError> credentialsException(BadCredentialsException e) {

        ResponseMessageError response = new ResponseMessageError(
                HttpStatus.UNAUTHORIZED.value(),
                Collections.singletonList(CustomTranslator.toLocale("user.auth.unauthorized_user"))
        );

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(response);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    ResponseEntity<ResponseMessageError> handleConstraintViolation(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();

        List<String> msg = constraintViolations.stream()
                .map(constraintViolation -> constraintViolation.getMessage())
                .collect(Collectors.toList());


        ResponseMessageError response = new ResponseMessageError(
                HttpStatus.BAD_REQUEST.value(), msg);

        return ResponseEntity
                .badRequest()
                .body(response);
    }

}
