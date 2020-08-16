package br.com.central.errors.events.entity.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "EventLogDTO")
public class EventLogDTO extends EventDTO {
    private String log;
}
