package br.com.central.errors.infrastructure.exception;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * The type Message dto.
 */
@Getter
@Setter
@AllArgsConstructor
@ApiModel(value = "Message")
public class MessageError {
    private String message;
}
