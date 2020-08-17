package br.com.central.errors.events.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ApiModel(value = "EventLogDTO")
@NoArgsConstructor
public class EventLogDTO extends EventDTO {

    @NotBlank(message = "NotBlank.eventLogDTO.log")
    @ApiModelProperty(example = "Retrograde clock change detected, soft-evicting connections from pool.", required = true)
    private String log;
}
