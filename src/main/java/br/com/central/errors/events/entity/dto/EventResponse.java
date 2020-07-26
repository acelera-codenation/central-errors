package br.com.central.errors.events.entity.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EventResponse {
    private Long id;
    private Integer level;
    private String description;
    private String origin;
    private LocalDateTime date;
    private Integer quantity;
}
