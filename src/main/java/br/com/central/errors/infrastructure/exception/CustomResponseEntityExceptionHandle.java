package br.com.central.errors.infrastructure.exception;

import br.com.central.errors.infrastructure.message.ResponseMessageError;
import br.com.central.errors.infrastructure.translate.CustomTranslator;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
class CustomResponseEntityExceptionHandle extends ResponseEntityExceptionHandler {


    @ExceptionHandler({CustomNotFoundException.class})
    protected ResponseEntity<Object> notFound(CustomNotFoundException e) {

        ResponseMessageError response = new ResponseMessageError(
                HttpStatus.NOT_FOUND.value(),
                Collections.singletonList(CustomTranslator.toLocale("resource.not_found"
                        , e.getClazz().getSimpleName())));

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

//    @ExceptionHandler({MethodArgumentNotValidException.class})
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
//                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
//        Map<String, Object> body = new LinkedHashMap<>();
//        body.put("timestamp", LocalDateTime.now());
//        body.put("status", status.value());
//        body.put("errors", ex.getBindingResult().getFieldErrors().stream()
//                .map(DefaultMessageSourceResolvable::getDefaultMessage)
//                .collect(Collectors.toList()));
//
//        return new ResponseEntity<>(body, headers, status);
//    }

    @ExceptionHandler({PropertyReferenceException.class})
    protected ResponseEntity<Object> handlerPropertyReference(PropertyReferenceException e) {

        ResponseMessageError response = new ResponseMessageError(
                HttpStatus.BAD_REQUEST.value(),
                Collections.singletonList(CustomTranslator.toLocale("invalid.reference.property"
                        , e.getPropertyName()
                        , e.getType().getType().getSimpleName()))
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler({ExpiredJwtException.class})
    protected ResponseEntity<Object> handlerTokenException(PropertyReferenceException e) {

        ResponseMessageError response = new ResponseMessageError(
                HttpStatus.BAD_REQUEST.value(),
                Collections.singletonList(CustomTranslator.toLocale("jwt.invalid_sign"))
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<ResponseMessageError> credentialsException(BadCredentialsException e) {

        ResponseMessageError response = new ResponseMessageError(
                HttpStatus.UNAUTHORIZED.value(),
                Collections.singletonList(CustomTranslator.toLocale(e.getMessage()))
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    ResponseEntity<ResponseMessageError> handleConstraintViolation(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();

        List<String> msg = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());

        ResponseMessageError response = new ResponseMessageError(
                HttpStatus.BAD_REQUEST.value(), msg);

        return ResponseEntity.badRequest().body(response);
    }

}
