package br.com.central.errors.infrastructure.message;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@ApiModel(value = "ResponseMessageError")
public class ResponseMessageError {

    @ApiModelProperty(example = "2020-08-16 13:10:27")
    private LocalDateTime timestamp;
    @ApiModelProperty(example = "400")
    private Integer status;

    @ApiModelProperty(example = "[Resource not found]")
    private List<String> message;

    public ResponseMessageError(Integer status, List<String> message) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
    }
}
