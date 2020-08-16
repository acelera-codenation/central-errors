package br.com.central.errors.events.entity.dto;

import br.com.central.errors.events.entity.enums.Level;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@ApiModel(value = "EventDTO")
public class EventDTO {

    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Level level;

    @NotBlank
    @Size(max = 255)
    private String description;

    @NotBlank
    @Size(max = 255)
    private String origin;

    @NotNull
    private LocalDateTime date;

    @Positive
    private Integer quantity;
}
