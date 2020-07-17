package br.com.codenation.errors_center.security.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * The type Message dto.
 */
@Data
@AllArgsConstructor
@ApiModel(value = "Message")
public class MessageDTO {
    private String message;
}
