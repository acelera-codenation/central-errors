package br.com.central.errors.infrastructure.message;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@ApiModel(value = "ResponseMessage")
public class ResponseMessage {

    @ApiModelProperty(example = "Update success")
    private String message;

}
