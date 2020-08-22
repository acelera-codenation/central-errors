package br.com.central.errors.infrastructure.exception;

import br.com.central.errors.infrastructure.message.ResponseMessageError;
import br.com.central.errors.infrastructure.translate.CustomTranslator;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.mapping.PropertyReferenceException;
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
@Slf4j
class CustomResponseEntityExceptionHandle extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {

        ResponseMessageError response = new ResponseMessageError(status.value(),
                ex.getBindingResult().getFieldErrors().stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .collect(Collectors.toList()));

        return new ResponseEntity<>(response, headers, status);
    }
    @ExceptionHandler({CustomNotFoundException.class})
    protected ResponseEntity<Object> notFound(CustomNotFoundException e) {

        log.error(e.getMessage(), e);

        ResponseMessageError response = new ResponseMessageError(404,
                Collections.singletonList(CustomTranslator.toLocale("resource.not_found"
                        , e.getClazz().getSimpleName())));

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler({PropertyReferenceException.class})
    protected ResponseEntity<Object> handlerPropertyReference(PropertyReferenceException e) {

        log.error(e.getMessage(), e);
        ResponseMessageError response = new ResponseMessageError(400,
                Collections.singletonList(CustomTranslator.toLocale("invalid.reference.property"
                        , e.getPropertyName()
                        , e.getType().getType().getSimpleName()))
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler({ExpiredJwtException.class})
    protected ResponseEntity<Object> handlerTokenException(PropertyReferenceException e) {
        log.error(e.getMessage(), e);
        ResponseMessageError response = new ResponseMessageError(400,
                Collections.singletonList(CustomTranslator.toLocale("jwt.invalid_sign"))
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<ResponseMessageError> credentialsException(BadCredentialsException e) {
        log.error(e.getMessage(), e);
        ResponseMessageError response = new ResponseMessageError(401,
                Collections.singletonList(CustomTranslator.toLocale(e.getMessage()))
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<ResponseMessageError> handleConstraintViolation(ConstraintViolationException e) {

        log.error(e.getMessage(), e);

        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        List<String> msg = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());

        return ResponseEntity.badRequest().body(new ResponseMessageError(400, msg));
    }

}
