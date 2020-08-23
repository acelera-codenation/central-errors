package br.com.central.errors.infrastructure.exception;

import lombok.Getter;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

@Getter
public class CustomNotFoundException extends ResourceNotFoundException {

    private final Class clazz;

    public CustomNotFoundException(Class clazz) {
        this.clazz = clazz;
    }
}
