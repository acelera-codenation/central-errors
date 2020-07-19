package br.com.codenation.errors_center.message;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * The type Message dto.
 */
@Data
@AllArgsConstructor
@ApiModel(value = "Message")
public class MessageError {
    private String message;
}
