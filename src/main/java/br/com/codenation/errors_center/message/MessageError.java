package br.com.codenation.errors_center.message;

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
