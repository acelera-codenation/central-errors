package br.com.central.errors.message;

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
