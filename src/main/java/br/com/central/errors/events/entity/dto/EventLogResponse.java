package br.com.central.errors.events.entity.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "Event Response with log")
public class EventLogResponse extends EventResponse {
    private String log;
}
