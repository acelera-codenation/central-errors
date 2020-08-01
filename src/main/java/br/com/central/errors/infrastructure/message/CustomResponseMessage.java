package br.com.central.errors.infrastructure.message;

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
public class CustomResponseMessage {
    private String message;
}
